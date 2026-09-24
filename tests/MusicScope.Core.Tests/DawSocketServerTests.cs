using System;
using System.Buffers.Binary;
using System.Net.Sockets;
using System.Threading.Tasks;
using MusicScope.Network;
using Xunit;

namespace MusicScope.Core.Tests;

public class DawSocketServerTests
{
    [Fact]
    public async Task DawSocketServer_Receives_SampleRate_And_AudioStream()
    {
        int testPort = 18989; // use non-standard port for test isolation
        await using var server = new DawSocketServer(testPort);
        server.Start();

        double receivedSampleRate = 0.0;
        int receivedAudioBytesCount = 0;
        var audioReceivedTcs = new TaskCompletionSource<bool>();

        server.AudioDataReceived += (s, e) =>
        {
            receivedSampleRate = e.SampleRate;
            receivedAudioBytesCount += e.AudioBytes.Length;
            audioReceivedTcs.TrySetResult(true);
        };

        using var client = new TcpClient();
        await client.ConnectAsync("127.0.0.1", testPort);
        await using var stream = client.GetStream();

        // 1. Send 'I' command (sample rate = 96000.0)
        byte[] infoPacket = new byte[5 + 8];
        infoPacket[0] = (byte)'I';
        BinaryPrimitives.WriteInt32LittleEndian(infoPacket.AsSpan(1, 4), 8);
        BinaryPrimitives.WriteInt64LittleEndian(infoPacket.AsSpan(5, 8), BitConverter.DoubleToInt64Bits(96000.0));
        await stream.WriteAsync(infoPacket);

        // 2. Send 'S' command (audio chunk: 16 bytes = 2 frames of stereo float)
        byte[] audioData = new byte[16];
        byte[] streamPacket = new byte[5 + audioData.Length];
        streamPacket[0] = (byte)'S';
        BinaryPrimitives.WriteInt32LittleEndian(streamPacket.AsSpan(1, 4), audioData.Length);
        audioData.CopyTo(streamPacket.AsSpan(5));
        await stream.WriteAsync(streamPacket);
        await stream.FlushAsync();

        // Wait for event
        var completed = await Task.WhenAny(audioReceivedTcs.Task, Task.Delay(2000));
        Assert.Same(audioReceivedTcs.Task, completed);

        Assert.Equal(96000.0, receivedSampleRate);
        Assert.Equal(16, receivedAudioBytesCount);

        // 3. Send 'E' command
        byte[] endPacket = new byte[5];
        endPacket[0] = (byte)'E';
        BinaryPrimitives.WriteInt32LittleEndian(endPacket.AsSpan(1, 4), 0);
        await stream.WriteAsync(endPacket);

        await server.StopAsync();
    }
}
