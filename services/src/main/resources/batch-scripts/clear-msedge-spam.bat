@echo off

REM --- Step 1: Clear Microsoft Edge Cache ---
rundll32.exe InetCpl.cpl,ClearMyTracksByProcess 255

REM --- Step 2: Close Microsoft Edge Tabs ---
taskkill /F /IM msedge.exe

echo Microsoft Edge cache cleared and all tabs closed.
pause
