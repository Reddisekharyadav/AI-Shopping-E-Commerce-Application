# pgAdmin Setup Guide - View Your Local Database Tables

## Install pgAdmin (if not already installed)

1. Download pgAdmin 4: https://www.pgadmin.org/download/pgadmin-4-windows/
2. Run the installer and follow the setup wizard
3. Launch pgAdmin 4 from Start Menu

## Connect to Your Local PostgreSQL Database

### Step 1: Open pgAdmin
- Launch pgAdmin 4
- You may be asked to set a master password (this is for pgAdmin, not your database)

### Step 2: Add Server Connection

1. **Right-click "Servers"** in the left sidebar → **"Register" → "Server"**

2. **General Tab**:
   - Name: `Local PostgreSQL` (or any name you prefer)

3. **Connection Tab**:
   - Host name/address: `localhost`
   - Port: `5432`
   - Maintenance database: `postgres`
   - Username: `postgres`
   - Password: Your PostgreSQL password (from .env file)
   - ☑️ Check "Save password"

4. Click **"Save"**

### Step 3: Navigate to Your Database

Once connected:
1. Expand **"Servers"** → **"Local PostgreSQL"**
2. Expand **"Databases"** → Find **"aishopping"**
3. Expand **"Schemas"** → **"public"**
4. Expand **"Tables"**

### Step 4: View Tables

You should see your application tables:
- `cart_item`
- `checkout`
- `feedback`
- `orders`
- `products`
- `users`
- (and any other tables created by your application)

### Step 5: View Table Data

To see the data in a table:
1. **Right-click on a table** (e.g., `users`)
2. Select **"View/Edit Data" → "All Rows"**
3. A query editor will open showing all records

### Step 6: Run Custom Queries

To write your own SQL queries:
1. **Right-click on "aishopping" database**
2. Select **"Query Tool"**
3. Type your SQL query, for example:
   ```sql
   SELECT * FROM users;
   SELECT * FROM products;
   SELECT * FROM orders WHERE user_id = 1;
   ```
4. Click the **Play button** (▶️) or press **F5** to execute

## Common Queries

### View all users:
```sql
SELECT * FROM users;
```

### Count total products:
```sql
SELECT COUNT(*) FROM products;
```

### View recent orders:
```sql
SELECT * FROM orders ORDER BY created_at DESC LIMIT 10;
```

### View cart items with user details:
```sql
SELECT u.username, c.product_name, c.quantity, c.price 
FROM cart_item c 
JOIN users u ON c.user_id = u.id;
```

### Check database size:
```sql
SELECT pg_size_pretty(pg_database_size('aishopping')) as database_size;
```

## Troubleshooting

### Can't Connect to PostgreSQL

**Error**: "Could not connect to server"

**Solutions**:
1. Verify PostgreSQL service is running:
   - Open **Services** (Win + R → `services.msc`)
   - Find **"postgresql-x64-xx"** service
   - Ensure it's **Running**. If not, right-click → **Start**

2. Check port 5432 is not blocked:
   ```powershell
   netstat -an | findstr 5432
   ```

3. Verify password is correct (from your `.env` file)

### Database "aishopping" doesn't exist

**Solution**: Run your Spring Boot application first
```powershell
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
Spring Boot will create the database and tables automatically on first run.

### Tables are empty

This is normal for a new application. Tables will populate when you:
- Create user accounts (Signup page)
- Add products to cart
- Place orders
- Submit feedback

### Permission Denied

**Error**: "Permission denied for schema public"

**Solution**: Grant permissions in Query Tool:
```sql
GRANT ALL PRIVILEGES ON DATABASE aishopping TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
```

## Backup Your Database

### Using pgAdmin GUI:
1. Right-click on **"aishopping"** database
2. Select **"Backup..."**
3. Choose filename and format (Custom recommended)
4. Click **"Backup"**

### Using Command Line:
```powershell
# Backup
pg_dump -U postgres -d aishopping -F c -f backup.dump

# Restore
pg_restore -U postgres -d aishopping backup.dump
```

## Tips for Development

1. **Refresh View**: If tables don't appear, right-click "Tables" → **"Refresh"**

2. **Auto-Refresh Data**: After running application, press **F5** in data view to reload

3. **Export Data**: Right-click table → **"Export..."** → Choose CSV/JSON format

4. **Table Structure**: Right-click table → **"Properties"** to see column definitions

5. **Run Application Logs**: Keep an eye on Spring Boot console for SQL queries (`spring.jpa.show-sql=true`)

## Next Steps

- Once you verify local database works, you can deploy to Render
- Render will create a cloud PostgreSQL database automatically
- You can connect pgAdmin to Render's database using the external connection string
