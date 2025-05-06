@echo off
setlocal enabledelayedexpansion

echo === Entering bookborrow-ui directory...
cd bookborrow-ui || (
    echo [ERROR] Failed to enter bookborrow-ui directory.
    pause
    exit /b
)

:: Detect available package manager
set PM=

where pnpm >nul 2>nul && set PM=pnpm
if "%PM%"=="" where yarn >nul 2>nul && set PM=yarn
if "%PM%"=="" where cnpm >nul 2>nul && set PM=cnpm
if "%PM%"=="" where npm >nul 2>nul && set PM=npm

if "%PM%"=="" (
    echo [ERROR] No package manager found. Please install pnpm, yarn, cnpm, or npm.
    pause
    exit /b
)

echo Detected package manager: %PM%

:: Check node_modules
if exist node_modules (
    echo node_modules already exists. Skipping install.
) else (
    echo node_modules not found. Installing dependencies...
    call %PM% install || (
        echo [ERROR] Failed to install dependencies.
        pause
        exit /b
    )
)

:: Start dev server
echo Starting development server...
if "%PM%"=="yarn" (
    call yarn serve
) else (
    call %PM% run dev
)
