@echo off
echo Starting AI Shopping E-Commerce Application with Chatbot Backend
echo ================================================================

REM Set environment variables
set MYSQL_DB_PASSWORD=R1e2d3d4i5@143
echo Setting up environment variables...
set FLASK_APP=app.py
set FLASK_ENV=development
set FLASK_DEBUG=1
REM Set your OpenAI API key here or set it as a system environment variable
REM set OPENAI_API_KEY=your_api_key_here

echo.
echo [1/2] Starting Python Chatbot Backend...
echo ----------------------------------------
cd /d "%~dp0src\main\resources\templates\chatbot"
start "Python Chatbot Backend" cmd /k "python app.py"

echo.
echo [2/2] Starting Spring Boot Application...
echo -----------------------------------------
cd /d "%~dp0"
timeout /t 3 /nobreak > nul

REM Run the Spring Boot application
"C:\Program Files\Eclipse Adoptium\jdk-17.0.6.10-hotspot\bin\java.exe" -cp "target\classes;%USERPROFILE%\.m2\repository\org\springframework\boot\spring-boot-starter-web\3.4.2\spring-boot-starter-web-3.4.2.jar;%USERPROFILE%\.m2\repository\org\springframework\boot\spring-boot-starter-thymeleaf\3.4.2\spring-boot-starter-thymeleaf-3.4.2.jar;%USERPROFILE%\.m2\repository\org\springframework\boot\spring-boot-starter\3.4.2\spring-boot-starter-3.4.2.jar" com.example.demo.DemoApplication

echo.
echo Application stopped. Press any key to exit...
pause