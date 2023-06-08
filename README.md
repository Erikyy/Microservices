# Microservices

## "Simple bank system"

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
- Kafka (1 node)
- Postgresql
- JPA

### Current database sharding

Currently database is configured to have 1 master and 2 replicas and
each of them are sharded using tables, it should be possible to change this
each database instead.

### Setup

Run ```docker-compose up -d``` to start databases and kafka

In each run configuration, add these env variables
though values need to be depending on what ports are assigned
```dotenv
TRANSACTION_DB_HOST=127.0.0.1
TRANSACTION_DB_PORT_MASTER=59000
TRANSACTION_DB_PORT_SLAVE1=59001-59005
TRANSACTION_DB_PORT_SLAVE2=59001-59005
TRANSACTION_DB_USER=db1_user
TRANSACTION_DB_PASSWORD=db1_password
TRANSACTION_DB_REPLICATION_USER=db_1_repl
TRANSACTION_DB_REPLICATION_PASSWORD=db_1_repl_password
TRANSACTION_DB=db_1

USER_DB_HOST=127.0.0.1
USER_DB_PORT=59100
USER_DB_USER=db2_user
USER_DB_PASSWORD=db2_password
USER_DB=db_1

MANAGEMENT_DB_HOST=127.0.0.1
MANAGEMENT_DB_PORT=5433
MANAGEMENT_DB_USER=db3_user
MANAGEMENT_DB_PASSWORD=db3_password
MANAGEMENT_DB=db_1
```
Then before running services, run migration-runner to migrate transaction-svc database.
Other automatic migrations are handled by JPA. 

After that start services in any desired order, it does not really matter.
