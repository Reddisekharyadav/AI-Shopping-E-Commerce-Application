# Render Deployment Guide

## Prerequisites
- GitHub repository with your code
- Render account (https://render.com)
- OpenAI API key

## Deployment Steps

### 1. Push Code to GitHub
```bash
git add render.yaml build.sh
git commit -m "Add Render deployment configuration"
git push origin main
```

### 2. Create New Web Service on Render

1. Go to https://dashboard.render.com
2. Click **"New +"** → **"Blueprint"**
3. Connect your GitHub repository: `Reddisekharyadav/AI-Shopping-E-Commerce-Application`
4. Render will detect `render.yaml` automatically
5. Click **"Apply"** to create both database and web service

### 3. Set Environment Variables

After deployment starts, go to your web service settings and add:

- **OPENAI_API_KEY**: Your OpenAI API key (from .env file)

The `POSTGRES_PASSWORD` and `DATABASE_URL` will be automatically set from the database.

### 4. Monitor Deployment

- Check the **Logs** tab for build progress
- Wait for "Build succeeded" message
- Application will be available at: `https://nextgenkart-app.onrender.com`

## Important Notes

- **Free tier limitations**: 
  - App sleeps after 15 minutes of inactivity
  - First request after sleep takes 30-60 seconds to wake up
  - Database has 90-day expiration on free tier

- **Database Connection**: 
  - Render provides `DATABASE_URL` automatically
  - Format: `postgres://user:password@host:port/dbname`
  - Spring Boot will parse this automatically

- **Port Configuration**:
  - Render sets `PORT` environment variable dynamically
  - Application uses `${PORT:8080}` to read it

## Troubleshooting

### Build Fails
- Check Java version is 17 in render.yaml
- Verify mvnw has execute permissions
- Check build logs for Maven errors

### Application Won't Start
- Verify OPENAI_API_KEY is set
- Check DATABASE_URL is connected
- Review application logs for errors

### Database Connection Issues
- Ensure database service is running
- Verify DATABASE_URL format is correct
- Check firewall/IP allowlist settings

## Manual Deployment Alternative

If Blueprint deployment doesn't work:

1. **Create PostgreSQL Database**:
   - New → PostgreSQL
   - Name: `nextgenkart-db`
   - Save database credentials

2. **Create Web Service**:
   - New → Web Service
   - Connect GitHub repo
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/demo-0.0.1-SNAPSHOT.jar`
   - Add environment variables manually

## Post-Deployment

- Test the application at your Render URL
- Verify database tables are created automatically
- Check chatbot functionality with OpenAI API
- Monitor logs for any runtime errors
