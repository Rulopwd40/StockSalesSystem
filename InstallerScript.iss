; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Libreria Centro App"
#define MyAppVersion "1.0"

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{1F354F7C-5906-44F2-AD44-36A9B9CB7D0D}}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
DefaultDirName={autopf}\libreriacentroapp
DefaultGroupName={#MyAppName}
; Remove the following line to run in administrative install mode (install for all users).
PrivilegesRequired=lowest
PrivilegesRequiredOverridesAllowed=dialog
OutputBaseFilename=AppSetup
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"


[Dirs]
Name: "{app}\csv"
Name: "{app}\backup"
Name: "{app}\database_sqlite"
Name: "{app}\python"

[Files]
Source: "D:\Coding\demo\target\demo-1.0.1.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Coding\demo\dist\generar_grafica.exe"; DestDir: "{app}\python"; Flags: ignoreversion
Source: "D:\Coding\demo\centro.ico"; DestDir: "{app}"; Flags: ignoreversion

; NOTE: Don't use "Flags: ignoreversion" on any shared system files



[Icons]
; Crear un acceso directo en el escritorio
Name: "{userdesktop}\Libreria Centro App"; Filename: "{app}\demo-1.0.1.jar"; WorkingDir: "{app}"; IconFilename: "{app}\centro.ico"; IconIndex: 0; Comment: "Acceso directo a Libreria Centro App"; Tasks: desktopicon

[Tasks]
; Opción para permitir al usuario seleccionar la creación del acceso directo en el escritorio
Name: desktopicon; Description: "Crear acceso directo en el escritorio"; GroupDescription: "Accesos directos";

[Run]
; Ejecutar el archivo JAR principal después de la instalación
Filename: "javaw.exe"; Parameters: "-jar {app}\demo-1.0.1.jar"; StatusMsg: "Iniciando {#MyAppName}..."; Flags: nowait postinstall skipifsilent