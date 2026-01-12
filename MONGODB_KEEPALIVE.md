# MongoDB Keep-Alive Configuration

## Problem
MongoDB Atlas free tier (M0) clusters automatically pause after ~60 minutes of inactivity. This causes the website to become unresponsive until the cluster resumes.

## Solutions Implemented

### 1. **Internal Keep-Alive Task** ✅
A scheduled task automatically pings the database every 5 minutes to prevent auto-pause.

**Location:** `src/main/java/com/example/demo/config/DatabaseKeepAliveTask.java`

**Features:**
- Pings database every 5 minutes
- Health check every 2 minutes
- Automatic reconnection on failure
- Minimal resource usage

### 2. **Optimized Connection Pooling** ✅
MongoDB connection pool maintains active connections.

**Configuration:** `application.properties`
- Min pool size: 10 connections
- Max pool size: 100 connections
- Idle timeout: 60 seconds
- Connection keep-alive enabled

### 3. **Health Check Endpoints** ✅
REST endpoints for external monitoring services.

**Endpoints:**
- `GET /api/health` - Full health check with database status
- `GET /api/health/ping` - Quick ping response

**Example Response:**
```json
{
  "status": "UP",
  "database": "CONNECTED",
  "timestamp": "2026-01-12T23:45:00",
  "message": "Application is healthy"
}
```

## External Keep-Alive Services (Optional)

If you deploy to a platform like Render, use these free services to ping your application:

### 1. **UptimeRobot** (Recommended)
- Website: https://uptimerobot.com
- Free plan: 50 monitors, 5-minute intervals
- Setup:
  1. Create account
  2. Add monitor with URL: `https://your-app.onrender.com/api/health/ping`
  3. Set interval: 5 minutes

### 2. **Cron-Job.org**
- Website: https://cron-job.org
- Free plan: Unlimited jobs
- Setup:
  1. Create account
  2. Create new cron job
  3. URL: `https://your-app.onrender.com/api/health/ping`
  4. Schedule: `*/5 * * * *` (every 5 minutes)

### 3. **BetterUptime**
- Website: https://betteruptime.com
- Free plan: 10 monitors
- Setup similar to UptimeRobot

## MongoDB Atlas Free Tier Limitations

**Current limitations (M0 Free Tier):**
- Auto-pauses after inactivity
- 512MB storage
- Shared CPU/RAM
- Limited connections

**Upgrade options to prevent auto-pause:**

### M2 Shared Cluster ($9/month)
- No auto-pause
- 2GB storage
- Dedicated CPU/RAM
- More connections

### M10+ Dedicated Cluster ($57+/month)
- No auto-pause
- Production-ready
- Full features
- 24/7 support

## Testing Keep-Alive

1. **Check if scheduled tasks are running:**
   ```
   Look for these log messages:
   - "Database keep-alive ping successful"
   - "Database health check successful"
   ```

2. **Test health endpoint:**
   ```bash
   curl http://localhost:3434/api/health
   ```

3. **Test ping endpoint:**
   ```bash
   curl http://localhost:3434/api/health/ping
   ```

## Troubleshooting

### If database still pauses:
1. Verify scheduling is enabled in `DemoApplication.java`
2. Check logs for keep-alive task execution
3. Ensure MongoDB Atlas cluster is not paused manually
4. Consider upgrading to M2 or higher tier

### If seeing connection errors:
1. Check MongoDB Atlas network access settings
2. Verify connection string in `.env` or environment variables
3. Check connection pool settings in `application.properties`
4. Review MongoDB Atlas cluster status

## Production Recommendations

For production deployments:

1. **Upgrade to M2+ tier** - Most reliable solution
2. **Use external monitoring** - UptimeRobot + internal keep-alive
3. **Enable logging** - Monitor keep-alive task execution
4. **Set up alerts** - Get notified if database goes down
5. **Use Redis cache** - Reduce database queries
6. **Implement retry logic** - Handle temporary connection failures

## Configuration

All keep-alive settings can be adjusted in:
- `DatabaseKeepAliveTask.java` - Change ping intervals
- `application.properties` - Adjust connection pool settings
- External monitors - Modify ping frequency

**Current settings:**
- Internal ping: Every 5 minutes
- Health check: Every 2 minutes
- Connection pool: 10-100 connections
- Idle timeout: 60 seconds
