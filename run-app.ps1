$env:MYSQL_DB_PASSWORD='R1e2d3d4i5@143'
# Set your OpenAI API key here or set it as a system environment variable
# $env:OPENAI_API_KEY='your_api_key_here'

Write-Host "Starting Spring Boot Application..." -ForegroundColor Green
Set-Location "C:\Users\reddi\mango\assp\shopping"

# Try to run with jar file first (if exists)
if (Test-Path "target/*.jar") {
    Write-Host "Found jar file, running with java -jar..." -ForegroundColor Yellow
    & java -jar (Get-ChildItem "target/*.jar" | Select-Object -First 1).FullName
} else {
    # Run with classpath
    Write-Host "Running with classpath..." -ForegroundColor Yellow
    & java -cp "target/classes" com.example.demo.DemoApplication
}

Write-Host "Press any key to continue..."
$host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")