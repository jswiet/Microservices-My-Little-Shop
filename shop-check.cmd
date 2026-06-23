@echo off
fc docs\openapi.json target\openapi-temp\openapi.json >nul
if %ERRORLEVEL% EQU 0 (
    echo OpenAPI documentation is up to date.
) else (
    echo ERROR: OpenAPI documentation is outdated! Run http://localhost:8081/v3/api-docs
    exit /b 1
)