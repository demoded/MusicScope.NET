var File = java.io.File;
var AudioSystem = javax.sound.sampled.AudioSystem;
var FIR = java.lang.Class.forName('sdfgjkljljoftrytrszgijpokjprs.pNDgZfJwuCsjGRIyQUKtqdU');

var f0 = FIR.getConstructor(java.lang.Integer.TYPE).newInstance(0);
var f1 = FIR.getConstructor(java.lang.Integer.TYPE).newInstance(1);

// We need DSP method: DSP(int n, int n2, double[] dArray, double[] dArray2, double[] dArray3, double[] dArray4)
var dArrayClass = java.lang.Class.forName('[D');
var dspMethod = FIR.getMethod('rHAjVyBgPhqkQKsOvJMPMYn', java.lang.Integer.TYPE, java.lang.Integer.TYPE, dArrayClass, dArrayClass, dArrayClass, dArrayClass);

var file = new File('TestAudioSamples/04_MusicalSim_Crest_11.1dB_44k_16bit.wav');
var ais = AudioSystem.getAudioInputStream(file);
var format = ais.getFormat();
print('Audio format: ' + format);

var frameSize = format.getFrameSize(); // 4 bytes (16-bit stereo)
var bufSize = 2048 * frameSize;
var byteBuf = java.lang.reflect.Array.newInstance(java.lang.Byte.TYPE, bufSize);

var stage0L = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 2048);
var stage0R = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 2048);
var stage1L = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 4096);
var stage1R = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 4096);
var stage2L = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 8192);
var stage2R = java.lang.reflect.Array.newInstance(java.lang.Double.TYPE, 8192);

var ring = [0, 0, 0, 0, 0, 0, 0, 0];
var ring_idx = 0;
var warmup = 0;
var chunk_info = 0.0;
var channel_decoder = 1;
var demux_res = 0.0;
var total_blocks = 0;

var bytesRead;
while ((bytesRead = ais.read(byteBuf)) > 0) {
    var frames = bytesRead / frameSize;
    if (frames < 2048) {
        print('Trailing frames: ' + frames);
        break;
    }
    
    // unpack 16-bit little endian
    for (var i = 0; i < frames; i++) {
        var idx = i * 4;
        var b0 = byteBuf[idx];
        var b1 = byteBuf[idx + 1];
        var sL = (b1 << 8) | (b0 & 0xFF);
        stage0L[i] = sL / 32768.0;
        
        var b2 = byteBuf[idx + 2];
        var b3 = byteBuf[idx + 3];
        var sR = (b3 << 8) | (b2 & 0xFF);
        stage0R[i] = sR / 32768.0;
    }
    
    // Stage 1 filter: 2048 -> 4096
    dspMethod.invoke(f0, 0, 2048, stage0L, stage0R, stage1L, stage1R);
    // Stage 2 filter: 4096 -> 8192
    dspMethod.invoke(f1, 0, 4096, stage1L, stage1R, stage2L, stage2R);
    
    var maxL = 0.0;
    var maxR = 0.0;
    var sumSqL = 0.0;
    var sumSqR = 0.0;
    
    for (var n = 0; n < 8192; n++) {
        var d9 = stage2L[n];
        var d10 = stage2R[n];
        var absL = Math.abs(d9);
        var absR = Math.abs(d10);
        if (maxL < absL) maxL = absL;
        if (maxR < absR) maxR = absR;
        sumSqL += d9 * d9;
        sumSqR += d10 * d10;
    }
    
    var d4 = sumSqL / 8192.0;
    var d3 = sumSqR / 8192.0;
    
    var d17 = maxL;
    var d18 = d4;
    if (d17 < maxR) {
        d17 = maxR;
        d18 = d3;
    }
    
    var rms = Math.sqrt(d18);
    var crest = rms > 0.0 ? d17 / rms : 0.0;
    
    ring[ring_idx] = crest;
    ring_idx = (ring_idx + 1) & 7;
    
    var d19 = 0.0;
    for (var k = 0; k < 8; k++) {
        d19 += ring[k];
    }
    d19 /= 8.0;
    
    if (d19 > 0.001) {
        if (warmup < 8) {
            warmup++;
        } else {
            chunk_info += d19;
            demux_res = 20.0 * (Math.log(chunk_info / channel_decoder) / Math.LN10);
            channel_decoder++;
        }
    }
    total_blocks++;
}

print('Total full blocks processed: ' + total_blocks);
print('Original Java DemuxResT: ' + demux_res);
print('Rounded (1 dec): ' + (Math.round(demux_res * 10.0) / 10.0));
