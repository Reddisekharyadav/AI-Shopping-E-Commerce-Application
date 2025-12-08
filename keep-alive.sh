#!/bin/bash
# Keep-alive script to prevent Render free tier from sleeping
# Run this on a separate service or use a cron job service

URL="https://nextgenkart-app.onrender.com"
INTERVAL=600  # Ping every 10 minutes (600 seconds)

while true; do
    echo "$(date): Pinging $URL"
    curl -s -o /dev/null -w "Status: %{http_code}\n" "$URL"
    sleep $INTERVAL
done
