# Microservices

## "Simple bank system"

Latest version does not have keycloak authentication
instead to save a lot of time I just made a secret field that is in plain text
!! NOT SECURE !!, but transaction database is sharded and replicated

!! Due to having little time for this project it is incomplete
but should be able to do simple transactions which use both services.

## Requirements

 - Minimum 32 Gigs Ram, 16 Gigs with intellij will crash your pc
 - Docker
 - Docker compose
 - Jdk 17

### Tech stack

- Spring boot
- Apache sharding sphere jdbc
- Kafka (1 node)
- Postgresql
- JPA

### Main goal

Main goal of this project is database sharding though for a bit extra I decided to make a 
simple microservice architecture (Diagram shows what it should be but due to lack of time, most of this simply didn't happen, but 
main functionality like creating accounts and making transactions work).

For sharding I decided to use ShardingSphere, it is popular and simple to use. Implementing my own would've taken too long time. 

Functional flow is this:
user-svc                         transaction-svc                user-svc
Account creates new transaction -> Transaction processing -> Transaction complete -> Account balance processing
Other things that work are simple CRUD operations on accounts.

Currently, database is configured to have 1 master and 2 replicas and
each of them are sharded using tables, it should be possible to change this
each database instead and configure sharding to be across multiple machines/containers (for very large datasets 
this is useful, in this project example: transactions, that table data size will grow VERY FAST so distributing data across
multiple shards will reduce size of database).

Databases can be scaled as needed, currently there are 2 replicas and 3 shards, but it is
possible to create more when needed.

Replicas are for read only. Which has no real use in this project unless querying user transactions (can be made possible with kafka streams, 
or through simpler technologies like rest api, whatever the needs are).

When transactions are made, they are stored into database distributed into separate shards using Modulo algorithm
(transaction_id % 3) (There are other algorithms for sharding like volume based range and boundary based range algorithms, but modulo is simple and easy to use) and transaction_ids are generated using SNOWFLAKE algorithm to assure uniqueness of ids across all shards.

Example of b_transaction_2 shard.

| amount | signature | currency\_id\_ref | transaction\_type | datetime | completed | transaction\_id | account\_from\_ref\_id | account\_to\_ref\_id |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 20.00 | 6391c3309c4606bf5a832631648d67435f702b4c | 0 | 1 | 2023-06-09 18:26:23.896924 | 2023-06-09 18:26:23.906630 | 873980478783225857 | d56ea79c-af28-45b1-9b64-432e41bebfbb | 8b9ba8e7-c8ed-4146-a3a1-b63d2711dfbc |
| 20.00 | 6391c3309c4606bf5a832631648d67435f702b4c | 0 | 1 | 2023-06-09 18:26:24.633946 | 2023-06-09 18:26:24.638181 | 873980481832484864 | d56ea79c-af28-45b1-9b64-432e41bebfbb | 8b9ba8e7-c8ed-4146-a3a1-b63d2711dfbc |

Example of b_transaction_1 shard

| amount | account\_from\_ref\_id | signature | currency\_id\_ref | account\_to\_ref\_id | transaction\_type | datetime | completed | transaction\_id |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 20.00 | d56ea79c-af28-45b1-9b64-432e41bebfbb | 6391c3309c4606bf5a832631648d67435f702b4c | 0 | 8b9ba8e7-c8ed-4146-a3a1-b63d2711dfbc | 1 | 2023-06-09 01:19:55.993604 | 2023-06-09 01:19:55.997918 | 873722160374349825 |

And so on.

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
For kafka configuration the port for using outside docker is 9094, using it with other containers - 9092

Then before running services, run migration-runner to migrate transaction-svc database.
Other automatic migrations are handled by JPA. 

After that start services in any desired order, it does not really matter.
