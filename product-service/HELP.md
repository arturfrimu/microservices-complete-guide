### Start a container with PostgresSQL database 

```shell
docker run --name product-service-postgres-database -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```

## Open HTTP requests collection in your IDEA or use postman
### Create a product
```
POST http://localhost:8080/products
Content-Type: application/json

{
  "name": "Example Product ####",
  "description": "This is an example product",
  "price": 99.99,
  "categoryId": 1
}
```
### Fetch all products
```
GET http://localhost:8080/products
```
### Find a product by id
```
GET http://localhost:8080/products/1
```
### Delete a product by id
```
DELETE http://localhost:8080/products/1
```
