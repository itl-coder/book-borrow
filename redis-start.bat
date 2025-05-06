@echo off
chcp 65001 >nul
REM ========================================
REM Start Redis Server - Redis 7.2.6 for Windows
REM ========================================

REM Set working directory
cd /d D:\SoftWare\Redis-7.2.6-Windows-x64-msys2\Redis-7.2.6-Windows-x64-msys2

REM Display startup information
echo =========================================================================================
echo Install Path: D:\SoftWare\Redis-7.2.6-Windows-x64-msys2\Redis-7.2.6-Windows-x64-msys2
echo Starting Redis server, please wait......
echo Using configuration file: redis.conf
echo =========================================================================================

REM Start Redis service
redis-server.exe redis.conf

REM Close window after service stops
echo Redis service has been stopped. Press any key to exit......
pause >nul
exit