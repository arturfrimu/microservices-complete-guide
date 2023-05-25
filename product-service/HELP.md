### Start a container with PostgresSQL database 

```shell
docker run --name product-service-postgres-database -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```

