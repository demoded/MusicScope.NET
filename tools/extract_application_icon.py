"""Extract the original PE icon group without using platform-specific APIs.

Requires Pillow (pip install Pillow). Run from any directory; paths default to
the checked-out original distribution and MusicScope.Desktop/Assets.
"""

import argparse
from io import BytesIO
from pathlib import Path
import struct

from PIL import Image


def icon_from_executable(path: Path) -> bytes:
    data = path.read_bytes()
    u16 = lambda offset: struct.unpack_from("<H", data, offset)[0]
    u32 = lambda offset: struct.unpack_from("<I", data, offset)[0]
    if data[:2] != b"MZ":
        raise ValueError("Expected a Windows PE executable")
    pe = u32(0x3C)
    if data[pe:pe + 4] != b"PE\0\0":
        raise ValueError("Invalid PE signature")
    optional = pe + 24
    directory = optional + (112 if u16(optional) == 0x20B else 96)
    sections = optional + u16(pe + 20)

    def file_offset(rva: int) -> int:
        for i in range(u16(pe + 6)):
            section = sections + i * 40
            address = u32(section + 12)
            length = max(u32(section + 8), u32(section + 16))
            if address <= rva < address + length:
                return u32(section + 20) + rva - address
        raise ValueError(f"Resource RVA {rva:#x} is outside the PE sections")

    root = file_offset(u32(directory + 16))

    def resources(relative: int, keys: tuple = ()):
        node = root + relative
        for i in range(u16(node + 12) + u16(node + 14)):
            entry = node + 16 + i * 8
            name, target = u32(entry), u32(entry + 4)
            key = keys + (name,)
            if target & 0x80000000:
                yield from resources(target & 0x7FFFFFFF, key)
            else:
                record = root + target
                start = file_offset(u32(record))
                yield key, data[start:start + u32(record + 4)]

    entries = dict(resources(0))
    # The original application's first RT_GROUP_ICON contains its six branded sizes.
    group_key = next(key for key in entries if key[0] == 14)
    group = entries[group_key]
    count = struct.unpack_from("<H", group, 4)[0]
    directory_entries, payloads = [], []
    offset = 6 + 16 * count
    for i in range(count):
        entry = group[6 + 14 * i:6 + 14 * (i + 1)]
        icon_id = struct.unpack_from("<H", entry, 12)[0]
        payload = entries[(3, icon_id, group_key[2])]
        if len(payload) != struct.unpack_from("<I", entry, 8)[0]:
            raise ValueError("Icon resource size does not match its group entry")
        directory_entries.append(entry[:12] + struct.pack("<I", offset))
        payloads.append(payload)
        offset += len(payload)
    return group[:6] + b"".join(directory_entries + payloads)


def extract(source: Path, destination: Path):
    original = icon_from_executable(source)
    icon = Image.open(BytesIO(original))
    sizes = sorted(icon.ico.sizes())
    required = {(n, n) for n in (16, 32, 48, 64, 256)}
    if not required.issubset(sizes):
        raise ValueError(f"Original icon is missing required sizes: {required - set(sizes)}")
    destination.mkdir(parents=True, exist_ok=True)
    (destination / "MusicScope.ico").write_bytes(original)
    images = {}
    for size in sizes:
        image = icon.ico.getimage(size).convert("RGBA")
        images[size[0]] = image
        image.save(destination / f"MusicScope-{size[0]}.png")
    master = images[max(images)]
    master.save(destination / "MusicScope.png")

    # ICNS uses PNG chunks. Preserve the native sizes; only Retina 512/1024 are resized.
    types = {16: b"icp4", 32: b"icp5", 64: b"icp6", 128: b"ic07",
             256: b"ic08", 512: b"ic09", 1024: b"ic10"}
    chunks = []
    for size, kind in types.items():
        image = images.get(size)
        if image is None:
            image = master.resize((size, size), Image.Resampling.LANCZOS)
        stream = BytesIO()
        image.save(stream, format="PNG")
        payload = stream.getvalue()
        chunks.append(kind + struct.pack(">I", 8 + len(payload)) + payload)
    body = b"".join(chunks)
    (destination / "MusicScope.icns").write_bytes(b"icns" + struct.pack(">I", 8 + len(body)) + body)
    print(f"Extracted original resolutions: {sizes}")
    print(f"Wrote ICO, PNG and ICNS assets to {destination}")


if __name__ == "__main__":
    root = Path(__file__).resolve().parent.parent
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source", type=Path, default=root / "OriginalJavaApp/MusicScope.exe")
    parser.add_argument("--destination", type=Path, default=root / "src/MusicScope.Desktop/Assets")
    args = parser.parse_args()
    extract(args.source, args.destination)
