# resizr

## Details on Redis
Redis database contains one key for each running slave instance. The key is the id of the instance, e.g. *instance_8dasf98*. Each of the keys hold a FIFO queue for a specific slave instance.

Structure of a list:
```
instance_8dasf98:
    1) "{"resize_id": "1", "job_id": "1", "x": "123", "y": "525", "imageName": "test"}"
    2) "{"resize_id": "2", "job_id": "1", "x": "141", "y": "1425", "imageName": "test"}"
    3) "{"resize_id": "3", "job_id": "2", "x": "623", "y": "155", "imageName": "test"}"
    ...
    n) "{"resize_id": "n", "job_id": "m", "x": "149", "y": "1885", "imageName": "test"}"
```

Pushing to this list (Redis queue) can be done with the following command (using `redis-cli`):

`$ redis-cli -h 35.204.73.203 RPUSH instance_8dasf98 "{"resize_id": "n+1", "job_id": "m+1", "x": "222", "y": "444", "imageName": "test"}"`, which would append a line to the list.

- The command used to push to the tail of Redis queue is RPUSH which can be used by using "RedisTemplate". Reference: https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/
- The IP-address is the Redis IP address. Remember to create a firewall rule in order to open Redis to external machines.

Popping from the queue can be done with the following CLI command:
`$ redis-cli -h 35.204.73.203 LPOP instance_8dasf98`

- LPOP pops the head of the queue. As RPUSH, LPOP also can be used in Java by using RedisTemplates.

# Environment setup
Add the following properties into your environment variables:
DB_URL
DB_USERNAME
DB_PASSWORD