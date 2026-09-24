import os
import re
import json
import shutil

BASE_DIR = 'D:/git/MusicScope.NET'
TOOLS_DIR = os.path.join(BASE_DIR, 'tools')
RAW_EXE_DIR = os.path.join(TOOLS_DIR, 'src-decompiled', 'raw-exe')
RAW_MSCV_DIR = os.path.join(TOOLS_DIR, 'src-decompiled', 'raw-mscv')
OUT_DIR = os.path.join(TOOLS_DIR, 'src-decompiled', 'organized')
MAPPING_FILE = os.path.join(TOOLS_DIR, 'class_mappings.json')

with open(MAPPING_FILE, 'r') as f:
    mappings = json.load(f)

# Group mappings by original source file
# Some source files have multiple classes (outer + inner or package private)
# We will create a clean name for each class:
# Outer class -> SourceFile without .java
# Inner class Outer$1 -> OriginalSource$1
short_class_map = {} # obfuscated_simple_name -> target_simple_name

for full_class, src_file in mappings.items():
    simple_obf = full_class.split('/')[-1]
    src_base = src_file.replace('.java', '')
    
    if '$' in simple_obf:
        outer_obf, inner_suffix = simple_obf.split('$', 1)
        outer_full = full_class.split('$')[0]
        outer_src = mappings.get(outer_full, src_base).replace('.java', '')
        clean_name = f"{outer_src}${inner_suffix}"
    else:
        clean_name = src_base
        
    short_class_map[simple_obf] = clean_name

print(f"Generated {len(short_class_map)} class replacements")

# Organize into domain folders:
# Categorize based on source name
CATEGORIES = {
    'dsp': ['FFT', 'DSP', 'FIR', 'iir', 'Mathematics', 'BitMath', 'Filter', 'Convolver', 'LeadingZeros', 'Entropy'],
    'loudness': ['Loudness', 'Levels', 'CREST', 'Peak', 'StereoMeter', 'LevelMeter', 'Dynamic'],
    'codecs': ['AudioCodec', 'Decoder', 'Chunk', 'DSD', 'DSF', 'DST', 'FLAC', 'Alac', 'Wav', 'Aiff', 'Mp3', 'Aac', 'Vorbis', 'Cue', 'Riff'],
    'hardware': ['THD', 'Jitter', 'Turntable', 'UHR', 'AudioInput', 'LineIn', 'AudioOutput', 'Mixer'],
    'network': ['Socket', 'Network', 'Port', 'Server'],
    'batch_reporting': ['Batch', 'Report', 'Composer', 'Playlist', 'Album', 'Track'],
    'ui_controls': ['Control', 'Display', 'Frame', 'Dialog', 'Box', 'View', 'Panel', 'Style', 'Icon']
}

def categorize(name):
    for cat, keywords in CATEGORIES.items():
        for kw in keywords:
            if kw.lower() in name.lower():
                return cat
    return 'common'

os.makedirs(OUT_DIR, exist_ok=True)
for cat in list(CATEGORIES.keys()) + ['common']:
    os.makedirs(os.path.join(OUT_DIR, cat), exist_ok=True)

# Build a sorted list of replacements (longest first to avoid substring collision)
sorted_replacements = sorted(short_class_map.items(), key=lambda x: len(x[0]), reverse=True)
# Compile a regex pattern to replace all obfuscated class names
pattern = re.compile(r'\b(' + '|'.join(re.escape(k) for k, v in sorted_replacements if len(k) > 4) + r')\b')

def replace_callback(match):
    token = match.group(1)
    return short_class_map.get(token, token)

processed_count = 0

for root_dir in [RAW_EXE_DIR, RAW_MSCV_DIR]:
    for dirpath, _, filenames in os.walk(root_dir):
        for fname in filenames:
            if not fname.endswith('.java'):
                continue
            
            src_path = os.path.join(dirpath, fname)
            simple_name = fname.replace('.java', '')
            
            # Target name
            target_class_name = short_class_map.get(simple_name, simple_name)
            category = categorize(target_class_name)
            
            dest_filename = f"{target_class_name}.java"
            dest_path = os.path.join(OUT_DIR, category, dest_filename)
            
            # Read and replace obfuscated references
            with open(src_path, 'r', encoding='utf-8', errors='ignore') as in_f:
                content = in_f.read()
                
            # Replace class definition name if needed
            content = pattern.sub(replace_callback, content)
            
            with open(dest_path, 'w', encoding='utf-8') as out_f:
                out_f.write(content)
                
            processed_count += 1

print(f"Processed and organized {processed_count} decompiled Java files into {OUT_DIR}")
