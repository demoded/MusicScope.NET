python -c "
import wave, struct, math

# Let's run K-weighting filter and LoudnessModule PLR calculation on 04_MusicalSim
# K-weighting filter coefficients at 44100
# Stage 1 (high-shelf):
# b = [1.53512485958697, -2.69169618940638, 1.19839281085285]
# a = [1.0, -1.69065929318241, 0.73248077421585]
# Stage 2 (high-pass):
# b = [1.0, -2.0, 1.0]
# a = [1.0, -1.99004745483398, 0.99007225036621]

class Biquad:
    def __init__(self, b, a):
        self.b0, self.b1, self.b2 = b
        self.a1, self.a2 = a[1], a[2]
        self.x1 = self.x2 = self.y1 = self.y2 = 0.0
    def process(self, x):
        y = self.b0 * x + self.b1 * self.x1 + self.b2 * self.x2 - self.a1 * self.y1 - self.a2 * self.y2
        self.x2 = self.x1
        self.x1 = x
        self.y2 = self.y1
        self.y1 = y
        return y

class KFilter:
    def __init__(self):
        # 44100 filter constants matching MusicScope iirFilter
        self.hs = Biquad([1.53512485958697, -2.69169618940638, 1.19839281085285], [1.0, -1.69065929318241, 0.73248077421585])
        self.hp = Biquad([1.0, -2.0, 1.0], [1.0, -1.99004745483398, 0.99007225036621])
    def process(self, x):
        return self.hp.process(self.hs.process(x))

k_l = KFilter()
k_r = KFilter()

with wave.open('TestAudioSamples/04_MusicalSim_Crest_11.1dB_44k_16bit.wav', 'rb') as w:
    n_frames = w.getnframes()
    raw = w.readframes(n_frames)
    samples = struct.unpack('<' + str(n_frames * 2) + 'h', raw)

left = [s / 32768.0 for s in samples[0::2]]
right = [s / 32768.0 for s in samples[1::2]]

n_blocks = len(left) // 2048
ring_peak = [0.0]*8
ring_resp = [0.0]*8
ring_idx_p = 0
ring_idx_r = 0
warmup = 0
chunk_info = 0.0
demux_rest = 1.0
plr_avg = 0.0

for b in range(n_blocks):
    b_l = [k_l.process(x) for x in left[b*2048:(b+1)*2048]]
    b_r = [k_r.process(x) for x in right[b*2048:(b+1)*2048]]
    
    pk_l = max(abs(x) for x in b_l)
    pk_r = max(abs(x) for x in b_r)
    d2 = max(pk_l, pk_r)
    
    ms_l = sum(x*x for x in b_l)
    ms_r = sum(x*x for x in b_r)
    d9 = (ms_l + ms_r) / 2048.0
    
    ring_peak[ring_idx_p] = d2
    ring_idx_p = (ring_idx_p + 1) & 7
    d10 = sum(ring_peak) / 8.0
    d10 = 20.0 * math.log10(d10) - 0.691 if d10 > 0 else -60.0
    if d10 < -60.0: d10 = -60.0
    
    ring_resp[ring_idx_r] = d9
    ring_idx_r = (ring_idx_r + 1) & 7
    d11 = sum(ring_resp) / 8.0
    d12 = 10.0 * math.log10(d11) - 0.691 if d11 > 0 else -90.0
    if d12 < -60.0: d12 = -60.0
    
    alac_utils = max(0.0, d10 - d12)
    if warmup < 8:
        warmup += 1
        alac_utils = 0.0
    else:
        chunk_info += 10.0 ** (alac_utils / 20.0)
        demux_rest += 1.0
        plr_avg = 20.0 * math.log10(chunk_info / demux_rest) if chunk_info > 0 else 0.0

print('PLR Avg (DemuxUtils):', plr_avg, 'rounded:', round(plr_avg, 1))
"