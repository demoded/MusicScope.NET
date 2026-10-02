#!/bin/sh
# Register this extracted distribution in the current user's application menu.
set -eu

app_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
data_dir=${XDG_DATA_HOME:-"$HOME/.local/share"}
mkdir -p "$data_dir/applications"

# Desktop Entry string escaping is applied before Exec's quoted argument escaping.
# Escape backslashes, quotes, backticks, dollars, and literal percent field codes.
executable=$(printf '%s' "$app_dir/MusicScope.NET" | sed 's/\\/\\\\\\\\/g; s/"/\\\\"/g; s/`/\\\\`/g; s/\$/\\\\$/g; s/%/%%/g')
while IFS= read -r line; do
    case "$line" in
        Exec=*) printf 'Exec="%s"\n' "$executable" ;;
        *) printf '%s\n' "$line" ;;
    esac
done < "$app_dir/MusicScope.NET.desktop" > "$data_dir/applications/MusicScope.NET.desktop"

for size in 16 32 48 64 128 256; do
    icon_dir="$data_dir/icons/hicolor/${size}x${size}/apps"
    mkdir -p "$icon_dir"
    cp "$app_dir/Assets/MusicScope-$size.png" "$icon_dir/MusicScope.NET.png"
done
chmod +x "$app_dir/MusicScope.NET"
if command -v update-desktop-database >/dev/null 2>&1; then
    update-desktop-database "$data_dir/applications"
fi
if command -v gtk-update-icon-cache >/dev/null 2>&1; then
    gtk-update-icon-cache --force --ignore-theme-index "$data_dir/icons/hicolor"
fi
printf 'Registered MusicScope.NET from %s\n' "$app_dir"
