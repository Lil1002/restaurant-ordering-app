# Auth Server Environment Configuration - Windows Server 2025 PowerShell
# Run this PowerShell script before starting the Java application
# Usage: . .\env.ps1 (with dot-sourcing to set variables in current session)
#    or: .\env.ps1
#    then: java -jar target\daam-auth-0.0.1-SNAPSHOT.jar

# Application Configuration
$env:SERVER_PORT = "9000"

# Database Configuration
$env:DB_HOST = "localhost"
$env:DB_PORT = "3306"
$env:DB_NAME = "daamdb"
$env:DB_USER = "root"
$env:DB_PASSWORD = "secret123"

# Optional configuration (uncomment to use):
# $env:DEBUG_LEVEL = "INFO"
# $env:DB_DDL = "update"
# $env:DB_INIT = "always"
# $env:DB_ON_ERR = "true"
# $env:SEC_DEBUG = "DEBUG"

Write-Host "`nEnvironment variables set:" -ForegroundColor Green
Write-Host "  SERVER_PORT: $env:SERVER_PORT"
Write-Host "  DB_HOST: $env:DB_HOST"
Write-Host "  DB_PORT: $env:DB_PORT"
Write-Host "  DB_NAME: $env:DB_NAME"
Write-Host "`nReady to start Auth Server:" -ForegroundColor Green
Write-Host "  java -jar target\daam-auth-0.0.1-SNAPSHOT.jar" -ForegroundColor Cyan
Write-Host ""
