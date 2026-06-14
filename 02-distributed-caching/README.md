# 02-distributed-caching

Distributed caching implementation using **Spring Boot + Redis**.

## Tech Stack

* Java
* Spring Boot
* Spring Cache
* Redis
* H2 Database
* Docker

## Start Redis

```bash
sudo docker run -d --name redis-cache -p 6380:6379 redis
```

Check container:

```bash
sudo docker ps
```

Start existing container:

```bash
sudo docker start redis-cache
```

Stop Redis:

```bash
sudo docker stop redis-cache
```

Restart Redis:

```bash
sudo docker restart redis-cache
```

---

## Redis Configuration

```properties
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6380
```

---

## API Endpoints

### Add Weather

```http
POST /weather
```

Request Body:

```json
{
  "city":"A",
  "forecast":"Sunny"
}
```

### Get Weather

```http
GET /weather?city=A
```

* First request fetches from DB and stores the result in Redis.
* Subsequent requests are served directly from cache.

### Update Weather

```http
PUT /weather/A?weatherUpdate=Rainy
```

Updates DB and Redis cache.

### Delete Weather

```http
DELETE /weather/A
```

Removes data from DB and evicts cache.

---

# Verify Redis Cache

Open Redis CLI:

```bash
sudo docker exec -it redis-cache redis-cli
```

Ping Redis:

```redis
PING
```

Expected:

```text
PONG
```

### View all keys

```redis
KEYS *
```

Example output:

```text
1) "weather::A"
```

### Get value of a cache key

```redis
get weather::A
```

> Note: Spring Boot stores values in serialized form, so the output may look binary or unreadable.

### Check key type

```redis
type weather::A
```

Example output:

```text
string
```

### Check TTL (Time-To-Live)

```redis
ttl weather::A
```

Output:

* `-1` → No expiration
* Positive number → Remaining seconds
* `-2` → Key does not exist

### Monitor Redis operations in real-time

```redis
MONITOR
```

### Flush all cache (optional)

```redis
FLUSHALL
```

---

# Multi-Node Testing

Run multiple application instances:

### Node 1

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

### Node 2

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

Both nodes share the same Redis instance, enabling distributed caching.
