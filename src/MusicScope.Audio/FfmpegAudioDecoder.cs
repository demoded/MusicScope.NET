using System;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;

namespace MusicScope.Audio;

/// <summary>
/// Universal cross-platform audio decoder using native FFmpeg.
/// Streams decoded 32-bit floating-point audio data for WAV, AIFF, FLAC, ALAC, MP3, AAC, DSD, OGG, etc.
/// </summary>
public sealed class FfmpegAudioDecoder : IAudioDecoder
{
    private readonly string _ffmpegPath;

    public FfmpegAudioDecoder(string? customFfmpegPath = null)
    {
        _ffmpegPath = customFfmpegPath ?? FindFfmpegExecutable();
    }

    public static string FindFfmpegExecutable()
    {
        // 1. Check current directory or app directory
        string localExe = OperatingSystem.IsWindows() ? "ffmpeg.exe" : "ffmpeg";
        string localPath = Path.Combine(AppContext.BaseDirectory, localExe);
        if (File.Exists(localPath))
            return localPath;

        // 2. Check PATH environment variable
        string? pathEnv = Environment.GetEnvironmentVariable("PATH");
        if (!string.IsNullOrEmpty(pathEnv))
        {
            string separator = OperatingSystem.IsWindows() ? ";" : ":";
            foreach (string dir in pathEnv.Split(separator, StringSplitOptions.RemoveEmptyEntries))
            {
                try
                {
                    string candidate = Path.Combine(dir, localExe);
                    if (File.Exists(candidate))
                        return candidate;
                }
                catch { }
            }
        }

        // 3. Fallback to command name
        return localExe;
    }

    public async Task<AudioFileInfo> ProbeAsync(string filePath, CancellationToken ct = default)
    {
        if (!File.Exists(filePath))
            throw new FileNotFoundException("Audio file not found.", filePath);

        var psi = new ProcessStartInfo
        {
            FileName = _ffmpegPath,
            Arguments = $"-i \"{filePath}\"",
            RedirectStandardError = true,
            UseShellExecute = false,
            CreateNoWindow = true
        };

        using var process = new Process { StartInfo = psi };
        process.Start();

        string output = await process.StandardError.ReadToEndAsync(ct);
        await process.WaitForExitAsync(ct);

        double sampleRate = 44100.0;
        int channels = 2;
        int bitDepth = 16;
        TimeSpan duration = TimeSpan.Zero;
        string format = Path.GetExtension(filePath).TrimStart('.').ToUpperInvariant();

        // Parse Duration: 00:03:45.67
        var durMatch = Regex.Match(output, @"Duration:\s*(\d+):(\d+):(\d+\.\d+)");
        if (durMatch.Success)
        {
            int h = int.Parse(durMatch.Groups[1].Value);
            int m = int.Parse(durMatch.Groups[2].Value);
            double s = double.Parse(durMatch.Groups[3].Value, CultureInfo.InvariantCulture);
            duration = TimeSpan.FromSeconds(h * 3600 + m * 60 + s);
        }

        // Parse Audio: ..., 44100 Hz, stereo, s16, ...
        var audioMatch = Regex.Match(output, @"Audio:\s*[^,]+,\s*(\d+)\s*Hz,\s*([^,]+),\s*([^,]+)");
        if (audioMatch.Success)
        {
            sampleRate = double.Parse(audioMatch.Groups[1].Value, CultureInfo.InvariantCulture);
            string chStr = audioMatch.Groups[2].Value.Trim().ToLowerInvariant();
            channels = chStr switch
            {
                "mono" => 1,
                "stereo" => 2,
                "5.1" or "5.1(side)" => 6,
                "7.1" => 8,
                _ => 2
            };

            string sampleFmt = audioMatch.Groups[3].Value.Trim();
            if (sampleFmt.Contains("s32") || sampleFmt.Contains("flt") || sampleFmt.Contains("dbl"))
                bitDepth = 24;
            else if (sampleFmt.Contains("s16"))
                bitDepth = 16;
        }

        long fileSizeBytes = 0;
        try { fileSizeBytes = new FileInfo(filePath).Length; } catch { }

        return new AudioFileInfo
        {
            FilePath = filePath,
            FormatName = format,
            SampleRate = sampleRate,
            ChannelCount = channels,
            BitDepth = bitDepth,
            Duration = duration,
            FileSizeBytes = fileSizeBytes
        };
    }

    public async Task DecodeAsync(
        string filePath,
        AudioChunkDecodedHandler onChunkDecoded,
        CancellationToken ct = default)
    {
        AudioFileInfo info = await ProbeAsync(filePath, ct);

        var psi = new ProcessStartInfo
        {
            FileName = _ffmpegPath,
            // Stream interleaved 32-bit floating point PCM directly to pipe:1 (standard output)
            Arguments = $"-v error -i \"{filePath}\" -f f32le -ac {info.ChannelCount} pipe:1",
            RedirectStandardOutput = true,
            UseShellExecute = false,
            CreateNoWindow = true
        };

        using var process = new Process { StartInfo = psi };
        process.Start();

        const int floatBufferSize = 8192; // 8192 float samples per chunk
        int byteBufferSize = floatBufferSize * sizeof(float);
        byte[] rawBuffer = new byte[byteBufferSize];

        Stream stdout = process.StandardOutput.BaseStream;
        long totalSamplesRead = 0;
        long expectedTotalSamples = info.TotalFrames * info.ChannelCount;

        try
        {
            int bytesRead;
            while ((bytesRead = await stdout.ReadAsync(rawBuffer.AsMemory(0, byteBufferSize), ct)) > 0)
            {
                int floatCount = bytesRead / sizeof(float);
                if (floatCount == 0)
                    continue;

                float[] floatArray = new float[floatCount];
                Buffer.BlockCopy(rawBuffer, 0, floatArray, 0, bytesRead);

                totalSamplesRead += floatCount;
                double progress = expectedTotalSamples > 0 
                    ? Math.Min(1.0, (double)totalSamplesRead / expectedTotalSamples)
                    : 0.0;

                await onChunkDecoded(floatArray, info.ChannelCount, info.SampleRate, progress);
            }

            await process.WaitForExitAsync(ct);
        }
        catch (OperationCanceledException)
        {
            try { process.Kill(entireProcessTree: true); } catch { }
            throw;
        }
    }
}
