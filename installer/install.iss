[Files]
Source: "files\wg.exe"; DestDir: "{app}"; DestName: "wg.exe"
Source: "files\wg4.ico"; DestDir: "{app}"; DestName: "wg4.ico"
Source: "files\wireguard-all-0.0.1.jar"; DestDir: "{app}"; DestName: "wireguard-all-0.0.1.jar"
Source: "files\tap-windows-9.21.2.exe"; DestDir: "{tmp}"; DestName: "tap-windows-9.21.2.exe"; Flags: confirmoverwrite deleteafterinstall

[Icons]
Name: "{userdesktop}\WireGuardGui"; Filename: "{app}\wireguard-all-0.0.1.jar"; WorkingDir: "{app}"; IconFilename: "{app}\wg4.ico"; IconIndex: 0; Tasks: AddDesktopIcon
Name: "{userstartup}\Wireguard"; Filename: "{app}\wireguard-all-0.0.1.jar"; WorkingDir: "{app}"; IconFilename: "{app}\wg4.ico"; IconIndex: 0; Tasks: Autostart

[Tasks]
Name: "AddDesktopIcon"; Description: "Add Desktop Icon"
Name: "InstallTAP"; Description: "Install TAP interface"
Name: "Autostart"; Description: "Autostart"

[Setup]
AppName=WireGuardGui
AppVersion=0.0.1
AppCopyright=2017
AppId={{021666A2-3AE9-4F0B-B989-CF48028F27ED}
LicenseFile=LICENSE.rtf
ShowLanguageDialog=no
AppPublisher=marcin212
AppPublisherURL=bymarcin.com
AppSupportURL=bymarcin.com
AppUpdatesURL=bymarcin.com
AppContact=bymarcin.com
UninstallDisplayIcon={app}\wg4.ico
VersionInfoVersion=0.0.1
VersionInfoTextVersion=0.0.1
VersionInfoCopyright=2017
VersionInfoProductName=WireGuardGui
VersionInfoProductVersion=0.0.1
VersionInfoProductTextVersion=0.0.1
SetupIconFile=files\wg4.ico
DefaultDirName={pf}\WireGuardGui

[Run]
Filename: "{tmp}\tap-windows-9.21.2.exe"; Tasks: InstallTAP
