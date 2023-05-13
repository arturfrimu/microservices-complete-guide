### Start a container with PostgresSQL database
```shell
docker run --name customer-service-postgres-database -e POSTGRES_PASSWORD=postgres -d -p 5433:5432 postgres
```