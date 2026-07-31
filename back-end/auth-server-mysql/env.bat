@REM Auth Server Environment Configuration - Windows Server 2025
@REM Run this batch file before starting the Java application
@REM Usage: env.bat [then] java -jar target\daam-auth-0.0.1-SNAPSHOT.jar

set SERVER_PORT=9000
set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=daamdb
set DB_USER=root
set DB_PASSWORD=secret123

@REM Optional environment variables (uncomment to use):
@REM set DEBUG_LEVEL=INFO
@REM set DB_DDL=update
@REM set DB_INIT=always
@REM set DB_ON_ERR=true
@REM set SEC_DEBUG=DEBUG

@echo.
@echo Environment variables set:
@echo SERVER_PORT=%SERVER_PORT%
@echo DB_HOST=%DB_HOST%
@echo DB_PORT=%DB_PORT%
@echo DB_NAME=%DB_NAME%
@echo.
@echo Ready to start Auth Server:
@echo   java -jar target\daam-auth-0.0.1-SNAPSHOT.jar
@echo.
