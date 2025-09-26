@echo off
cd /d "C:\Users\reddi\mango\assp\shopping"
set MYSQL_DB_PASSWORD=R1e2d3d4i5@143
set FLASK_APP=app.py
REM Set your OpenAI API key here or set it as a system environment variable
REM set OPENAI_API_KEY=your_api_key_here

echo Starting Spring Boot Application...
java -Dspring.profiles.active=default -cp "target/classes;target/dependency/*" com.example.demo.DemoApplication
pause