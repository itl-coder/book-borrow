@echo off
chcp 65001 >nul
REM ========================================
REM 启动 Redis 服务器 - Redis 7.2.6 for Windows
REM ========================================

REM 设置工作目录
cd /d D:\SoftWare\Redis-7.2.6-Windows-x64-msys2\Redis-7.2.6-Windows-x64-msys2

REM 显示启动信息
echo ========================================
echo 正在启动 Redis 服务器，请稍候...
echo 使用配置文件:redis.conf
echo ========================================

REM 启动 Redis 服务
redis-server.exe redis.conf

REM 启动完成后关闭窗口
echo Redis 服务已关闭，按任意键退出...
pause >nul
exit
