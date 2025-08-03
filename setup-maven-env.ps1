# Script to set Maven environment variables (Run as Administrator)
Write-Host "Setting up Maven environment variables..." -ForegroundColor Green

# Set MAVEN_HOME
$mavenHome = "E:\Programs\DevTools\Maven\apache-maven-3.9.11-bin\apache-maven-3.9.11"
[Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHome, "Machine")
Write-Host "MAVEN_HOME set to: $mavenHome" -ForegroundColor Yellow

# Add Maven to PATH
$currentPath = [Environment]::GetEnvironmentVariable("Path", "Machine")
$mavenBin = "$mavenHome\bin"

if ($currentPath -notlike "*$mavenBin*") {
    $newPath = "$currentPath;$mavenBin"
    [Environment]::SetEnvironmentVariable("Path", $newPath, "Machine")
    Write-Host "Maven bin added to PATH: $mavenBin" -ForegroundColor Yellow
} else {
    Write-Host "Maven bin already in PATH" -ForegroundColor Green
}

Write-Host "Environment variables set successfully!" -ForegroundColor Green
Write-Host "Please restart VS Code and PowerShell for changes to take effect." -ForegroundColor Cyan

# Test the setup
Write-Host "`nTesting Maven installation..." -ForegroundColor Green
try {
    $mavenVersion = & "$mavenBin\mvn.cmd" --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Maven is working correctly!" -ForegroundColor Green
    }
} catch {
    Write-Host "Maven test failed, but environment variables are set." -ForegroundColor Yellow
}

pause
