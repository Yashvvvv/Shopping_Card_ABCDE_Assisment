@echo off
echo Checking Prerequisites for E-Commerce Shopping Cart Application...
echo.

echo Checking Java...
java -version >nul 2>&1
if %errorlevel% == 0 (
    java -version
    echo ✓ Java is installed
) else (
    echo ✗ Java is not installed or not in PATH
    echo Please install Java 17+ from https://adoptium.net/
)
echo.

echo Checking Maven...
mvn -version >nul 2>&1
if %errorlevel% == 0 (
    mvn -version | findstr "Apache Maven"
    echo ✓ Maven is installed
) else (
    echo ✗ Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/download.cgi
)
echo.

echo Checking Node.js...
node -version >nul 2>&1
if %errorlevel% == 0 (
    node -version
    echo ✓ Node.js is installed
) else (
    echo ✗ Node.js is not installed or not in PATH
    echo Please install Node.js from https://nodejs.org/
)
echo.

echo Checking npm...
npm -version >nul 2>&1
if %errorlevel% == 0 (
    npm -version
    echo ✓ npm is installed
) else (
    echo ✗ npm is not installed or not in PATH
    echo npm should come with Node.js installation
)
echo.

echo Prerequisite check complete!
echo.
echo If all tools are installed, you can run start-app.bat to start the application.
echo.
pause
