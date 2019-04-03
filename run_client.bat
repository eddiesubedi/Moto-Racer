@echo off
for /f "skip=1 delims={}, " %%A in ('wmic nicconfig get ipaddress') do for /f "tokens=1" %%B in ("%%~A") do set "IP=%%~B"
echo %IP%

java Client.ClientLauncher %IP% %1