using System;
using System.Buffers.Binary;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;

namespace MusicScope.Network;

/// <summary>
/// Event arguments when DAW audio data is received.
/// </summary>
public sealed class DawAudioDataEventArgs : EventArgs
{
    public double SampleRate { get; }
    public ReadOnlyMemory<byte> AudioBytes { get; }

    public DawAudioDataEventArgs(double sampleRate, ReadOnlyMemory<byte> audioBytes)
    {
        SampleRate = sampleRate;
        AudioBytes = audioBytes;
    }
}

/// <summary>
/// Asynchronous TCP socket server listening on port 8989 for VST/AU DAW plugin streaming.
/// Fully compatible with XiVero MusicScope's SocketReader protocol.
/// </summary>
public sealed class DawSocketServer : IAsyncDisposable
{
    public const int DefaultPort = 8989;

    private readonly int _port;
    private TcpListener? _listener;
    private CancellationTokenSource? _cts;
    private Task? _listenerTask;
    private double _currentSampleRate = 44100.0;
    private bool _isConnected;

    public int Port => _port;
    public bool IsConnected => _isConnected;
    public double CurrentSampleRate => _currentSampleRate;

    public event EventHandler<bool>? ConnectionStateChanged;
    public event EventHandler<DawAudioDataEventArgs>? AudioDataReceived;
    public event EventHandler? StreamEnded;

    public DawSocketServer(int port = DefaultPort)
    {
        _port = port;
    }

    /// <summary>
    /// Starts listening for incoming DAW plugin connections on port 8989.
    /// </summary>
    public void Start()
    {
        if (_listener != null)
            return;

        _cts = new CancellationTokenSource();
        _listener = new TcpListener(IPAddress.Any, _port);
        _listener.Start();

        _listenerTask = Task.Run(() => ListenLoopAsync(_cts.Token));
    }

    /// <summary>
    /// Stops the socket server and disconnects any active clients.
    /// </summary>
    public async Task StopAsync()
    {
        if (_cts != null)
        {
            await _cts.CancelAsync();
        }

        try
        {
            _listener?.Stop();
        }
        catch { }

        if (_listenerTask != null)
        {
            try
            {
                await _listenerTask;
            }
            catch (OperationCanceledException) { }
        }

        _listener = null;
        _cts?.Dispose();
        _cts = null;

        SetConnected(false);
    }

    private async Task ListenLoopAsync(CancellationToken ct)
    {
        while (!ct.IsCancellationRequested)
        {
            try
            {
                TcpClient client = await _listener!.AcceptTcpClientAsync(ct);
                SetConnected(true);

                // Handle single connected client
                await HandleClientAsync(client, ct);
            }
            catch (OperationCanceledException)
            {
                break;
            }
            catch (Exception)
            {
                if (ct.IsCancellationRequested)
                    break;
                await Task.Delay(500, ct);
            }
            finally
            {
                SetConnected(false);
            }
        }
    }

    private async Task HandleClientAsync(TcpClient client, CancellationToken ct)
    {
        using (client)
        await using (NetworkStream stream = client.GetStream())
        {
            byte[] headerBuffer = new byte[5];

            while (!ct.IsCancellationRequested && client.Connected)
            {
                // Read 5-byte header: 1-byte command + 4-byte little-endian length
                int bytesRead = await ReadExactAsync(stream, headerBuffer, 5, ct);
                if (bytesRead < 5)
                    break;

                char command = (char)headerBuffer[0];
                int length = BinaryPrimitives.ReadInt32LittleEndian(headerBuffer.AsSpan(1, 4));

                if (command == 'E') // End of stream
                {
                    StreamEnded?.Invoke(this, EventArgs.Empty);
                    break;
                }

                if (length <= 0)
                    continue;

                byte[] payload = new byte[length];
                int payloadRead = await ReadExactAsync(stream, payload, length, ct);
                if (payloadRead < length)
                    break;

                switch (command)
                {
                    case 'I': // Info: sample rate (8-byte double little-endian)
                        if (length >= 8)
                        {
                            _currentSampleRate = BitConverter.Int64BitsToDouble(BinaryPrimitives.ReadInt64LittleEndian(payload));
                        }
                        break;

                    case 'S': // Stream: audio byte chunks
                        AudioDataReceived?.Invoke(this, new DawAudioDataEventArgs(_currentSampleRate, payload));
                        break;
                }
            }
        }
    }

    private static async Task<int> ReadExactAsync(Stream stream, byte[] buffer, int count, CancellationToken ct)
    {
        int totalRead = 0;
        while (totalRead < count)
        {
            int read = await stream.ReadAsync(buffer.AsMemory(totalRead, count - totalRead), ct);
            if (read == 0)
                break; // EOF
            totalRead += read;
        }
        return totalRead;
    }

    private void SetConnected(bool connected)
    {
        if (_isConnected != connected)
        {
            _isConnected = connected;
            ConnectionStateChanged?.Invoke(this, connected);
        }
    }

    public async ValueTask DisposeAsync()
    {
        await StopAsync();
    }
}
