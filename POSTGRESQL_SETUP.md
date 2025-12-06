# PostgreSQL Setup Guide

## Step 1: Install PostgreSQL

1. Run the downloaded installer: `postgresql-18.1-1-windows-x64.exe`
2. During installation:
   - **Password:** Set to `postgres` (or remember your password)
   - **Port:** Keep default `5432`
   - **Locale:** Default
   - **Components:** Install all (PostgreSQL Server, pgAdmin, Command Line Tools)

## Step 2: Create Database

After installation completes:

### Option A: Using pgAdmin (GUI)
1. Open **pgAdmin 4** (installed with PostgreSQL)
2. Connect to PostgreSQL Server (use your password)
3. Right-click **Databases** → **Create** → **Database**
4. Name: `aishopping`
5. Click **Save**

### Option B: Using Command Line
```powershell
# Add PostgreSQL to PATH (if not done automatically)
$env:PATH += ";C:\Program Files\PostgreSQL\18\bin"

# Create database
createdb -U postgres aishopping

# Verify
psql -U postgres -c "\l"
```

## Step 3: Run Your Application

```powershell
# Set environment variables
$env:POSTGRES_PASSWORD='your-database-password'
$env:OPENAI_API_KEY='your-openai-api-key-here'

# Run the application
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Step 4: Access Your App

Open browser: http://localhost:3434

## Troubleshooting

### PostgreSQL service not running:
```powershell
# Start PostgreSQL service
net start postgresql-x64-18
```

### Can't connect to database:
- Check PostgreSQL is running: Services → postgresql-x64-18
- Verify password is correct
- Ensure database `aishopping` exists

### Port already in use:
```powershell
# Find what's using port 5432
netstat -ano | findstr :5432

# Stop conflicting service if needed
```

## After Installation - Quick Start

Just double-click this file after PostgreSQL is installed:
