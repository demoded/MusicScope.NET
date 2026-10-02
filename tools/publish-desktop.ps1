#requires -Version 7.0
param(
    [Parameter(Mandatory)]
    [ValidateSet('win-x64', 'win-arm64', 'osx-x64', 'osx-arm64', 'linux-x64', 'linux-arm64')]
    [string] $RuntimeIdentifier,
    [string] $OutputDirectory,
    [ValidatePattern('^\d+\.\d+\.\d+$')]
    [string] $Version = '1.0.0'
)

$ErrorActionPreference = 'Stop'
$repositoryRoot = Split-Path -Parent $PSScriptRoot
$desktopProject = Join-Path $repositoryRoot 'src/MusicScope.Desktop'
if ([string]::IsNullOrWhiteSpace($OutputDirectory)) {
    $OutputDirectory = Join-Path $repositoryRoot "dist/$RuntimeIdentifier"
}
$outputRoot = [IO.Path]::GetFullPath($OutputDirectory)
$publishPath = $outputRoot
if ($RuntimeIdentifier.StartsWith('osx-')) {
    $bundleContents = Join-Path $outputRoot 'MusicScope.NET.app/Contents'
    $publishPath = Join-Path $bundleContents 'MacOS'
}

# Publish directly into the app bundle on macOS so distribution folders contain one copy.
& dotnet publish (Join-Path $desktopProject 'MusicScope.Desktop.csproj') `
    -r $RuntimeIdentifier -c Release --self-contained true `
    -p:PublishSingleFile=true -p:IncludeNativeLibrariesForSelfExtract=true `
    -p:DebugType=embedded -p:CopyOutputSymbolsToPublishDirectory=false `
    "-p:Version=$Version" -o $publishPath
if ($LASTEXITCODE -ne 0) { throw "Publishing $RuntimeIdentifier failed." }

# Rename the published apphost while preserving its embedded assembly/resource names.
$executableExtension = if ($RuntimeIdentifier.StartsWith('win-')) { '.exe' } else { '' }
$executable = Join-Path $publishPath "MusicScope.NET$executableExtension"
Move-Item -LiteralPath (Join-Path $publishPath "MusicScope.Desktop$executableExtension") `
    -Destination $executable -Force

# Native packages can ship their own PDBs even when managed symbols are embedded.
Get-ChildItem -LiteralPath $publishPath -Filter *.pdb -File -Recurse | Remove-Item -Force

if ($RuntimeIdentifier.StartsWith('osx-')) {
    $resources = Join-Path $bundleContents 'Resources'
    New-Item -ItemType Directory -Path $resources -Force | Out-Null
    Copy-Item -LiteralPath (Join-Path $desktopProject 'Assets/MusicScope.icns') -Destination $resources
    [xml] $manifest = Get-Content -LiteralPath (Join-Path $desktopProject 'Packaging/macOS/Info.plist') -Raw
    foreach ($key in @('CFBundleVersion', 'CFBundleShortVersionString')) {
        $manifest.SelectSingleNode("/plist/dict/key[text()='$key']/following-sibling::*[1]").InnerText = $Version
    }
    $manifest.Save((Join-Path $bundleContents 'Info.plist'))
}

if (-not $IsWindows) {
    if (Test-Path -LiteralPath $executable) {
        [IO.File]::SetUnixFileMode($executable, [IO.UnixFileMode]::UserRead -bor [IO.UnixFileMode]::UserWrite `
            -bor [IO.UnixFileMode]::UserExecute -bor [IO.UnixFileMode]::GroupRead `
            -bor [IO.UnixFileMode]::GroupExecute -bor [IO.UnixFileMode]::OtherRead -bor [IO.UnixFileMode]::OtherExecute)
    }
}
Write-Output "Published $RuntimeIdentifier to $outputRoot"
