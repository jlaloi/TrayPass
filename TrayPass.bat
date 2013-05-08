set folder=%~dp0
start javaw -Xmx128M -Dlogback.configurationFile=file:/%folder%logback.xml -cp "%folder%TrayPass.jar;%folder%lib\*" traypass.Launcher
