using System;

namespace MusicScope.Audio;

public record AudioFileInfo
{
    public string FilePath { get; init; } = string.Empty;
    public string FormatName { get; init; } = string.Empty;
    public double SampleRate { get; init; } = 44100.0;
    public int ChannelCount { get; init; } = 2;
    public int BitDepth { get; init; } = 16;
    public TimeSpan Duration { get; init; } = TimeSpan.Zero;
    public long FileSizeBytes { get; init; }
    public long TotalFrames => (long)(Duration.TotalSeconds * SampleRate);
}
