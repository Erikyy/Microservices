# Microservices

## "Simple bank system"

![](services.png)


Latest version does not have keycloak authentication
instead to save a lot of time I just made a secret field that is in plain text
!! NOT SECURE !!, but transaction database is sharded and replicated

## Requirements

 - Minimum 32 Gigs Ram, 16 Gigs with intellij will crash your pc
 - Docker
 - Docker compose
 - Jdk 17

### Tech stack

- Spring boot
- Apache sharding sphere
- Kafka