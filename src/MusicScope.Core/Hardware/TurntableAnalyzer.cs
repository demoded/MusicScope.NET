using System;
using System.Collections.Generic;

namespace MusicScope.Core.Hardware;

public enum TurntableTargetSpeed
{
    Rpm33_3,
    Rpm45,
    Rpm78
}

public record TurntableResult
{
    /// <summary>
    /// Target nominal RPM (33.333, 45.0, or 78.0).
    /// </summary>
    public double TargetRpm { get; init; } = 33.333;

    /// <summary>
    /// Measured current RPM.
    /// </summary>
    public double CurrentRpm { get; init; } = 33.333;

    /// <summary>
    /// Measured tone frequency in Hz.
    /// </summary>
    public double MeasuredFrequencyHz { get; init; } = 1000.0;

    /// <summary>
    /// Speed deviation from nominal in percent (+/- %).
    /// </summary>
    public double DeviationPercent { get; init; } = 0.0;

    /// <summary>
    /// Wow & Flutter measurement (DIN / IEC 2-sigma peak-to-peak) in percent (%).
    /// </summary>
    public double WowAndFlutterPercent { get; init; } = 0.0;

    /// <summary>
    /// Rolling speed history points for UI display.
    /// </summary>
    public IReadOnlyList<double> HistoryRpm { get; init; } = [];
}

/// <summary>
/// Turntable RPM and Wow & Flutter analyzer using standard 1000 Hz or 3150 Hz reference vinyl test records.
/// Derived from MusicScope's TurntableRPM module.
/// </summary>
public sealed class TurntableAnalyzer
{
    private readonly double _sampleRate;
    private readonly double _referenceFrequencyHz;
    private readonly double _targetRpm;
    private readonly List<double> _frequencyHistory = [];
    private readonly int _maxHistoryLength;

    // Zero-crossing detector state
    private double _lastSample;
    private long _lastCrossingSampleIndex;
    private long _currentSampleIndex;
    private readonly List<double> _halfCyclePeriods = [];

    public TurntableAnalyzer(
        double sampleRate = 48000,
        double referenceFrequencyHz = 1000.0,
        TurntableTargetSpeed targetSpeed = TurntableTargetSpeed.Rpm33_3,
        int maxHistorySeconds = 120)
    {
        _sampleRate = sampleRate;
        _referenceFrequencyHz = referenceFrequencyHz;
        _targetRpm = targetSpeed switch
        {
            TurntableTargetSpeed.Rpm45 => 45.0,
            TurntableTargetSpeed.Rpm78 => 78.0,
            _ => 33.333333333
        };
        _maxHistoryLength = maxHistorySeconds * 10; // 10 updates per second
    }

    /// <summary>
    /// Processes incoming mono/left-channel audio samples and updates turntable frequency estimations.
    /// </summary>
    public void ProcessSamples(ReadOnlySpan<double> samples)
    {
        for (int i = 0; i < samples.Length; i++)
        {
            double currentSample = samples[i];

            // Positive-going zero-crossing with linear interpolation
            if (_lastSample <= 0.0 && currentSample > 0.0)
            {
                double fraction = -_lastSample / (currentSample - _lastSample + 1e-12);
                double exactCrossing = _currentSampleIndex + fraction;

                if (_lastCrossingSampleIndex > 0)
                {
                    double periodSamples = exactCrossing - _lastCrossingSampleIndex;
                    double expectedPeriod = _sampleRate / _referenceFrequencyHz;

                    // Filter out spurious zero-crossings outside +/- 20% of nominal frequency
                    if (periodSamples >= expectedPeriod * 0.75 && periodSamples <= expectedPeriod * 1.35)
                    {
                        double instantFreq = _sampleRate / periodSamples;
                        _halfCyclePeriods.Add(instantFreq);

                        // Every 100 ms of data, compute average frequency and append to history
                        if (_halfCyclePeriods.Count >= (int)(_referenceFrequencyHz * 0.10))
                        {
                            double avgFreq = 0.0;
                            for (int k = 0; k < _halfCyclePeriods.Count; k++)
                                avgFreq += _halfCyclePeriods[k];
                            avgFreq /= _halfCyclePeriods.Count;
                            _halfCyclePeriods.Clear();

                            _frequencyHistory.Add(avgFreq);
                            if (_frequencyHistory.Count > _maxHistoryLength)
                            {
                                _frequencyHistory.RemoveAt(0);
                            }
                        }
                    }
                }
                _lastCrossingSampleIndex = (long)exactCrossing;
            }

            _lastSample = currentSample;
            _currentSampleIndex++;
        }
    }

    /// <summary>
    /// Computes the current RPM, deviation %, and Wow/Flutter %.
    /// </summary>
    public TurntableResult CalculateResult()
    {
        if (_frequencyHistory.Count == 0)
        {
            return new TurntableResult
            {
                TargetRpm = _targetRpm,
                CurrentRpm = _targetRpm,
                MeasuredFrequencyHz = _referenceFrequencyHz
            };
        }

        double latestFreq = _frequencyHistory[^1];
        double currentRpm = _targetRpm * (latestFreq / _referenceFrequencyHz);
        double deviationPercent = ((latestFreq - _referenceFrequencyHz) / _referenceFrequencyHz) * 100.0;

        // Calculate Wow & Flutter (2-sigma deviation of frequency over recent history)
        int sampleCountForWf = Math.Min(_frequencyHistory.Count, 50); // last 5 seconds
        int startIdx = _frequencyHistory.Count - sampleCountForWf;

        double meanFreq = 0.0;
        for (int i = startIdx; i < _frequencyHistory.Count; i++)
            meanFreq += _frequencyHistory[i];
        meanFreq /= sampleCountForWf;

        double sumSqDev = 0.0;
        for (int i = startIdx; i < _frequencyHistory.Count; i++)
        {
            double diff = _frequencyHistory[i] - meanFreq;
            sumSqDev += diff * diff;
        }
        double stdDev = Math.Sqrt(sumSqDev / sampleCountForWf);
        // 2-sigma peak-to-peak wow & flutter percentage
        double wowAndFlutter = (2.0 * stdDev / meanFreq) * 100.0;

        List<double> rpmHistory = new(_frequencyHistory.Count);
        foreach (double f in _frequencyHistory)
        {
            rpmHistory.Add(Math.Round(_targetRpm * (f / _referenceFrequencyHz), 3));
        }

        return new TurntableResult
        {
            TargetRpm = Math.Round(_targetRpm, 3),
            CurrentRpm = Math.Round(currentRpm, 3),
            MeasuredFrequencyHz = Math.Round(latestFreq, 2),
            DeviationPercent = Math.Round(deviationPercent, 2),
            WowAndFlutterPercent = Math.Round(wowAndFlutter, 3),
            HistoryRpm = rpmHistory
        };
    }

    public void Reset()
    {
        _frequencyHistory.Clear();
        _halfCyclePeriods.Clear();
        _lastSample = 0.0;
        _lastCrossingSampleIndex = 0;
        _currentSampleIndex = 0;
    }
}
