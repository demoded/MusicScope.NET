using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Runtime.Intrinsics;

namespace MusicScope.Core.DSP;

public struct PolyphaseStats
{
    public double MaxL;
    public double MaxR;
    public double SumSqL;
    public double SumSqR;
}

/// <summary>
/// High-performance SIMD-accelerated port of XiVideo MusicScope's FIRFilterPoly.
/// Provides bit-exact True Peak and CREST calculation matching the original application.
/// </summary>
public sealed class MusicScopePolyphaseFilter
{
    private static readonly double[][] Coefficients =
    [
        // Filter 0: 90 taps (used for 44.1 kHz stage 1)
        [
            1.2102430109747054E-4, 4.4773464463986843E-4, 6.147372538423959E-4, 2.0806235252426693E-4, -4.967865822939038E-4,
            -4.8683641715010535E-4, 4.3448370523378315E-4, 8.886761505499103E-4, -1.7445967434274301E-4, -0.001310705738342131,
            -3.746882706263863E-4, 0.0016071300210796636, 0.0012272665216102754, -0.0015971790313525885, -0.0023156945951180135,
            0.0010982848317523262, 0.003474142315209548, 3.456788865806543E-5, -0.0044402785315951045, -0.001860069485439131,
            0.00487617799755048, 0.004306344251922917, -0.004410210468398261, -0.007138332685267387, 0.002695216432634287,
            0.009944940344595626, 5.254724912941727E-4, -0.012148740261914487, -0.005355404525969005, 0.013034411326453674,
            0.011698154491376442, -0.011781117224485332, -0.019237637057734947, 0.0074636892080764475, 0.027452347953846754,
            0.001059306177338399, -0.03566488690251283, -0.01561047405052567, 0.043119967030972726, 0.040463338352525004,
            -0.049081527685542393, -0.0913864209995784, 0.05293197100633365, 0.3132654297051058, 0.44573578908175543,
            0.3132654297051058, 0.05293197100633365, -0.09138642099957842, -0.049081527685542393, 0.04046333835252502,
            0.043119967030972726, -0.01561047405052567, -0.03566488690251282, 0.001059306177338399, 0.027452347953846754,
            0.007463689208076453, -0.019237637057734954, -0.011781117224485327, 0.011698154491376447, 0.013034411326453674,
            -0.0053554045259689995, -0.012148740261914487, 5.254724912941727E-4, 0.009944940344595626, 0.002695216432634287,
            -0.007138332685267387, -0.004410210468398261, 0.004306344251922914, 0.0048761779975504856, -0.0018600694854391333,
            -0.004440278531595102, 3.456788865806543E-5, 0.003474142315209548, 0.0010982848317523239, -0.002315694595118013,
            -0.0015971790313525885, 0.0012272665216102705, 0.0016071300210796587, -3.746882706263863E-4, -0.001310705738342131,
            -1.74459674342748E-4, 8.886761505499079E-4, 4.3448370523378315E-4, -4.8683641715011034E-4, -4.967865822939012E-4,
            2.0806235252427191E-4, 6.147372538424021E-4, 4.4773464463986843E-4, 1.2102430109746929E-4, 0.0
        ],
        // Filter 1: 54 taps (used for 44.1 kHz stage 2 and 88.2 kHz)
        [
            -6.275252828529093E-5, -4.1639630717071055E-4, -8.55141336889107E-4, -5.76441128658373E-4, 7.935520711192477E-4,
            0.0017716599195868104, 2.362457344043564E-4, -0.0028333741447320562, -0.0028154997024349647, 0.002273811821903716,
            0.006284737061950161, 0.0013895517953467567, -0.008507875544760677, -0.00848484037479237, 0.0063136686563387045,
            0.01707896824746866, 0.003190292283947032, -0.022570659320389843, -0.020925123577377948, 0.01817001385945677,
            0.044650736330463854, 0.004904554862204061, -0.06908139901881272, -0.0643406291067331, 0.08751846507360325,
            0.3031676158438164, 0.40561526702057915, 0.3031676158438164, 0.08751846507360325, -0.0643406291067331,
            -0.06908139901881272, 0.004904554862204061, 0.044650736330463854, 0.01817001385945677, -0.020925123577377948,
            -0.022570659320389843, 0.003190292283947032, 0.017078968247468657, 0.006313668656338709, -0.00848484037479237,
            -0.008507875544760674, 0.0013895517953467567, 0.006284737061950164, 0.00227381182190372, -0.0028154997024349647,
            -0.002833374144732052, 2.362457344043564E-4, 0.0017716599195868104, 7.935520711192435E-4, -5.764411286583814E-4,
            -8.55141336889107E-4, -4.1639630717071055E-4, -6.275252828528674E-5, 0.0
        ],
        // Filter 2: 80 taps (used for 48 kHz stage 1)
        [
            6.775238264029908E-5, 6.9120616831989616E-6, -3.7755518761413073E-4, -7.933940368527308E-4, -5.049933026986365E-4,
            4.892656899130968E-4, 8.707279944712783E-4, -2.622926097394152E-4, -0.0014193210771868844, -3.975869677588417E-4,
            0.0018032538230234206, 0.0014964853104819289, -0.0017353345773701936, -0.0029193552844220248, 9.157363791623283E-4,
            0.004364050198252058, 8.669559628573531E-4, -0.005354200791441549, -0.0036362798822331356, 0.005297271984196986,
            0.007140660106550909, -0.003589106318364235, -0.010802095265468036, -2.4778483681485105E-4, 0.013721491812763666,
            0.006428552512304546, -0.014729165426495144, -0.01479783394126319, 0.01245452026320786, 0.02479118035467874,
            -0.005306551940372494, -0.0354695142173262, -0.008879602481803452, 0.04564484072538752, 0.034527697015962,
            -0.05406427481900422, -0.08731778785685661, 0.05962337296088667, 0.311819181164125, 0.4384338638428065,
            0.311819181164125, 0.05962337296088667, -0.0873177878568566, -0.05406427481900422, 0.034527697015962,
            0.04564484072538752, -0.008879602481803452, -0.03546951421732619, -0.005306551940372494, 0.02479118035467874,
            0.01245452026320786, -0.01479783394126319, -0.014729165426495144, 0.006428552512304551, 0.01372149181276367,
            -2.4778483681485105E-4, -0.010802095265468034, -0.003589106318364235, 0.007140660106550911, 0.005297271984196989,
            -0.003636279882233133, -0.005354200791441549, 8.669559628573531E-4, 0.004364050198252058, 9.157363791623283E-4,
            -0.0029193552844220217, -0.0017353345773701936, 0.0014964853104819289, 0.0018032538230234212, -3.9758696775883613E-4,
            -0.0014193210771868844, -2.6229260973942086E-4, 8.707279944712783E-4, 4.892656899130996E-4, -5.049933026986365E-4,
            -7.933940368527308E-4, -3.775551876141286E-4, 6.9120616831989616E-6, 6.77523826403047E-5, 0.0
        ],
        // Filter 3: 52 taps (used for 48 kHz stage 2 and 96 kHz)
        [
            -1.3580988356781654E-4, -2.68444772817281E-4, 9.246350138675724E-5, 0.0010486417539920768, 0.0014011115490871367,
            -2.160658699843765E-4, -0.002540408219547814, -0.001824652138117239, 0.002771123081833796, 0.005399601838859129,
            3.859983850134724E-5, -0.008431318176613497, -0.00672234889775849, 0.007561062990517049, 0.015879251628668913,
            8.277700417562266E-4, -0.02287826357377677, -0.01843097102765121, 0.02027497023897156, 0.04334284395866305,
            0.0016995275716546454, -0.06985622272788873, -0.061461764176492704, 0.0902531743064741, 0.30201446683335736,
            0.40208122107153516, 0.30201446683335736, 0.0902531743064741, -0.061461764176492704, -0.06985622272788873,
            0.0016995275716546498, 0.04334284395866305, 0.02027497023897156, -0.01843097102765121, -0.022878263573776772,
            8.27770041756231E-4, 0.015879251628668913, 0.007561062990517049, -0.00672234889775849, -0.008431318176613497,
            3.859983850135595E-5, 0.005399601838859129, 0.002771123081833796, -0.001824652138117239, -0.0025404082195478127,
            -2.160658699843765E-4, 0.0014011115490871367, 0.0010486417539920855, 9.246350138675717E-5, -2.68444772817281E-4,
            -1.3580988356781654E-4, 0.0
        ],
        // Filter 4: 33 taps
        [
            7.002339730987853E-4, -1.6335029259019785E-4, -0.0018927079082414454, -0.0035137915716116313, -0.00246255786371549,
            0.0029124393930489993, 0.010505655538534579, 0.013835755229168977, 0.005799660223992739, -0.01445879146906549,
            -0.03649964162936848, -0.04077109897936529, -0.009163315466058196, 0.06157248722225863, 0.15285197172626008,
            0.23024785216583069, 0.26059549355218564, 0.23024785216583069, 0.15285197172626008, 0.06157248722225863,
            -0.009163315466058196, -0.04077109897936529, -0.03649964162936848, -0.01445879146906549, 0.005799660223992739,
            0.013835755229168977, 0.010505655538534579, 0.0029124393930489993, -0.00246255786371549, -0.0035137915716116313,
            -0.0018927079082414454, -1.6335029259019785E-4, 7.002339730987853E-4
        ]
    ];

    private static readonly int[] TapCounts = [90, 54, 80, 52, 33];
    private const int PhaseCount = 2;

    private readonly int _tapsPerPhase;
    private readonly int _m;
    private readonly double[] _phase0Coeffs;
    private readonly double[] _phase1Coeffs;

    // Mirrored contiguous history buffer: [stream][2 * _m]
    private readonly double[][] _historyL;
    private readonly double[][] _historyR;
    private readonly int[] _writePos;

    public MusicScopePolyphaseFilter(int filterIndex, int maxStreams = 2)
    {
        _tapsPerPhase = TapCounts[filterIndex] / PhaseCount;
        _m = _tapsPerPhase + 1;

        _phase0Coeffs = new double[_tapsPerPhase];
        _phase1Coeffs = new double[_tapsPerPhase];

        for (int k = 0; k < _tapsPerPhase; k++)
        {
            _phase0Coeffs[k] = Coefficients[filterIndex][k * PhaseCount + 0];
            _phase1Coeffs[k] = Coefficients[filterIndex][k * PhaseCount + 1];
        }

        _historyL = new double[maxStreams][];
        _historyR = new double[maxStreams][];
        _writePos = new int[maxStreams];

        for (int s = 0; s < maxStreams; s++)
        {
            _historyL[s] = new double[2 * _m];
            _historyR[s] = new double[2 * _m];
        }
    }

    /// <summary>
    /// Processes count stereo samples and outputs 2 * count oversampled stereo samples.
    /// Bit-exact port of FIRFilterPoly.DSP in XiVideo MusicScope, accelerated with fused dual-phase SIMD.
    /// </summary>
    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public void Process(int stream, int count, ReadOnlySpan<double> inL, ReadOnlySpan<double> inR, Span<double> outL, Span<double> outR)
    {
        int n4 = _tapsPerPhase;
        int m = _m;
        double[] histL = _historyL[stream];
        double[] histR = _historyR[stream];
        int pos = _writePos[stream];

        ref double pHistBaseL = ref MemoryMarshal.GetArrayDataReference(histL);
        ref double pHistBaseR = ref MemoryMarshal.GetArrayDataReference(histR);
        ref double pC0 = ref MemoryMarshal.GetArrayDataReference(_phase0Coeffs);
        ref double pC1 = ref MemoryMarshal.GetArrayDataReference(_phase1Coeffs);

        ref double pInL = ref MemoryMarshal.GetReference(inL);
        ref double pInR = ref MemoryMarshal.GetReference(inR);
        ref double pOutL = ref MemoryMarshal.GetReference(outL);
        ref double pOutR = ref MemoryMarshal.GetReference(outR);

        int outIdx = 0;
        int simdEnd = n4 - (n4 & 3);

        for (int i = 0; i < count; i++)
        {
            double sL = Unsafe.Add(ref pInL, i);
            double sR = Unsafe.Add(ref pInR, i);

            // Store in mirrored buffer
            Unsafe.Add(ref pHistBaseL, pos) = sL;
            Unsafe.Add(ref pHistBaseL, pos + m) = sL;
            Unsafe.Add(ref pHistBaseR, pos) = sR;
            Unsafe.Add(ref pHistBaseR, pos + m) = sR;

            pos++;
            if (pos >= m) pos = 0;

            ref double pHL = ref Unsafe.Add(ref pHistBaseL, pos);
            ref double pHR = ref Unsafe.Add(ref pHistBaseR, pos);

            double sumL1, sumR1, sumL0, sumR0;

            if (Vector256.IsHardwareAccelerated)
            {
                Vector256<double> accL1 = Vector256<double>.Zero;
                Vector256<double> accR1 = Vector256<double>.Zero;
                Vector256<double> accL0 = Vector256<double>.Zero;
                Vector256<double> accR0 = Vector256<double>.Zero;

                int k = 0;
                for (; k < simdEnd; k += 4)
                {
                    var vhL = Vector256.LoadUnsafe(ref pHL, (nuint)k);
                    var vhR = Vector256.LoadUnsafe(ref pHR, (nuint)k);
                    var vc1 = Vector256.LoadUnsafe(ref pC1, (nuint)k);
                    var vc0 = Vector256.LoadUnsafe(ref pC0, (nuint)k);

                    accL1 = Vector256.FusedMultiplyAdd(vc1, vhL, accL1);
                    accR1 = Vector256.FusedMultiplyAdd(vc1, vhR, accR1);
                    accL0 = Vector256.FusedMultiplyAdd(vc0, vhL, accL0);
                    accR0 = Vector256.FusedMultiplyAdd(vc0, vhR, accR0);
                }

                sumL1 = Vector256.Sum(accL1);
                sumR1 = Vector256.Sum(accR1);
                sumL0 = Vector256.Sum(accL0);
                sumR0 = Vector256.Sum(accR0);

                for (; k < n4; k++)
                {
                    double hL = Unsafe.Add(ref pHL, k);
                    double hR = Unsafe.Add(ref pHR, k);
                    double c1 = Unsafe.Add(ref pC1, k);
                    double c0 = Unsafe.Add(ref pC0, k);

                    sumL1 += c1 * hL;
                    sumR1 += c1 * hR;
                    sumL0 += c0 * hL;
                    sumR0 += c0 * hR;
                }
            }
            else
            {
                sumL1 = 0.0; sumR1 = 0.0; sumL0 = 0.0; sumR0 = 0.0;
                for (int k = 0; k < n4; k++)
                {
                    double hL = Unsafe.Add(ref pHL, k);
                    double hR = Unsafe.Add(ref pHR, k);
                    sumL1 += Unsafe.Add(ref pC1, k) * hL;
                    sumR1 += Unsafe.Add(ref pC1, k) * hR;
                    sumL0 += Unsafe.Add(ref pC0, k) * hL;
                    sumR0 += Unsafe.Add(ref pC0, k) * hR;
                }
            }

            Unsafe.Add(ref pOutL, outIdx) = sumL1 * 2.0;
            Unsafe.Add(ref pOutR, outIdx) = sumR1 * 2.0;
            Unsafe.Add(ref pOutL, outIdx + 1) = sumL0 * 2.0;
            Unsafe.Add(ref pOutR, outIdx + 1) = sumR0 * 2.0;
            outIdx += 2;
        }

        _writePos[stream] = pos;
    }

    /// <summary>
    /// Processes count stereo samples and directly accumulates true peak and energy statistics
    /// without writing out an intermediate buffer. Bit-exact to XiVideo MusicScope.
    /// </summary>
    [MethodImpl(MethodImplOptions.AggressiveOptimization)]
    public PolyphaseStats ProcessAndAccumulate(int stream, int count, ReadOnlySpan<double> inL, ReadOnlySpan<double> inR)
    {
        int n4 = _tapsPerPhase;
        int m = _m;
        double[] histL = _historyL[stream];
        double[] histR = _historyR[stream];
        int pos = _writePos[stream];

        ref double pHistBaseL = ref MemoryMarshal.GetArrayDataReference(histL);
        ref double pHistBaseR = ref MemoryMarshal.GetArrayDataReference(histR);
        ref double pC0 = ref MemoryMarshal.GetArrayDataReference(_phase0Coeffs);
        ref double pC1 = ref MemoryMarshal.GetArrayDataReference(_phase1Coeffs);

        ref double pInL = ref MemoryMarshal.GetReference(inL);
        ref double pInR = ref MemoryMarshal.GetReference(inR);

        double maxL = 0.0;
        double maxR = 0.0;
        double sumSqL = 0.0;
        double sumSqR = 0.0;

        int simdEnd = n4 - (n4 & 3);

        for (int i = 0; i < count; i++)
        {
            double sL = Unsafe.Add(ref pInL, i);
            double sR = Unsafe.Add(ref pInR, i);

            // Store in mirrored buffer
            Unsafe.Add(ref pHistBaseL, pos) = sL;
            Unsafe.Add(ref pHistBaseL, pos + m) = sL;
            Unsafe.Add(ref pHistBaseR, pos) = sR;
            Unsafe.Add(ref pHistBaseR, pos + m) = sR;

            pos++;
            if (pos >= m) pos = 0;

            ref double pHL = ref Unsafe.Add(ref pHistBaseL, pos);
            ref double pHR = ref Unsafe.Add(ref pHistBaseR, pos);

            double sumL1, sumR1, sumL0, sumR0;

            if (Vector256.IsHardwareAccelerated)
            {
                Vector256<double> accL1 = Vector256<double>.Zero;
                Vector256<double> accR1 = Vector256<double>.Zero;
                Vector256<double> accL0 = Vector256<double>.Zero;
                Vector256<double> accR0 = Vector256<double>.Zero;

                int k = 0;
                for (; k < simdEnd; k += 4)
                {
                    var vhL = Vector256.LoadUnsafe(ref pHL, (nuint)k);
                    var vhR = Vector256.LoadUnsafe(ref pHR, (nuint)k);
                    var vc1 = Vector256.LoadUnsafe(ref pC1, (nuint)k);
                    var vc0 = Vector256.LoadUnsafe(ref pC0, (nuint)k);

                    accL1 = Vector256.FusedMultiplyAdd(vc1, vhL, accL1);
                    accR1 = Vector256.FusedMultiplyAdd(vc1, vhR, accR1);
                    accL0 = Vector256.FusedMultiplyAdd(vc0, vhL, accL0);
                    accR0 = Vector256.FusedMultiplyAdd(vc0, vhR, accR0);
                }

                sumL1 = Vector256.Sum(accL1);
                sumR1 = Vector256.Sum(accR1);
                sumL0 = Vector256.Sum(accL0);
                sumR0 = Vector256.Sum(accR0);

                for (; k < n4; k++)
                {
                    double hL = Unsafe.Add(ref pHL, k);
                    double hR = Unsafe.Add(ref pHR, k);
                    double c1 = Unsafe.Add(ref pC1, k);
                    double c0 = Unsafe.Add(ref pC0, k);

                    sumL1 += c1 * hL;
                    sumR1 += c1 * hR;
                    sumL0 += c0 * hL;
                    sumR0 += c0 * hR;
                }
            }
            else
            {
                sumL1 = 0.0; sumR1 = 0.0; sumL0 = 0.0; sumR0 = 0.0;
                for (int k = 0; k < n4; k++)
                {
                    double hL = Unsafe.Add(ref pHL, k);
                    double hR = Unsafe.Add(ref pHR, k);
                    sumL1 += Unsafe.Add(ref pC1, k) * hL;
                    sumR1 += Unsafe.Add(ref pC1, k) * hR;
                    sumL0 += Unsafe.Add(ref pC0, k) * hL;
                    sumR0 += Unsafe.Add(ref pC0, k) * hR;
                }
            }

            double outL1 = sumL1 * 2.0;
            double outR1 = sumR1 * 2.0;
            double outL0 = sumL0 * 2.0;
            double outR0 = sumR0 * 2.0;

            double absL1 = outL1 < 0.0 ? -outL1 : outL1;
            double absR1 = outR1 < 0.0 ? -outR1 : outR1;
            double absL0 = outL0 < 0.0 ? -outL0 : outL0;
            double absR0 = outR0 < 0.0 ? -outR0 : outR0;

            if (absL1 > maxL) maxL = absL1;
            if (absL0 > maxL) maxL = absL0;
            if (absR1 > maxR) maxR = absR1;
            if (absR0 > maxR) maxR = absR0;

            sumSqL += outL1 * outL1 + outL0 * outL0;
            sumSqR += outR1 * outR1 + outR0 * outR0;
        }

        _writePos[stream] = pos;

        return new PolyphaseStats
        {
            MaxL = maxL,
            MaxR = maxR,
            SumSqL = sumSqL,
            SumSqR = sumSqR
        };
    }

    public void Reset()
    {
        for (int s = 0; s < _historyL.Length; s++)
        {
            Array.Clear(_historyL[s]);
            Array.Clear(_historyR[s]);
            _writePos[s] = 0;
        }
    }
}
