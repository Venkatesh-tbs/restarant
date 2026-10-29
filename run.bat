@echo off
echo ==========================================
echo Compiling Restaurant Management System...
echo ==========================================

REM Navigate to the src folder where packages are rooted
cd /d "%~dp0src"

REM Compile Main.java (javac will automatically compile all imported packages)
javac Main.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed. Please check the error messages above.
    pause
    exit /b %errorlevel%
)

echo.
echo [SUCCESS] Compilation successful! Starting application...
echo.

REM Run Main
java Main

pause
