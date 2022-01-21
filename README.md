### Using Tools & Technologies
#### Backend
* Java 11
* Spring Boot 2.6.3 (with Spring Security, Spring Web, Spring Data JPA)
* jjwt 0.9.1
* H2 Database
* Maven 3.8.1
* Mapstruct 1.4.2
* Springdoc 1.6.3

These are APIs that we need to provide:

| Method | Route  | Parameter Type | Parameter Info                                                                                                                                           | Description        |
|--------|---|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| `POST` | {{baseUrl}}/users/signup   | Body           | {   "username": "string",   "password": "string",   "email": "string",   "roleList": [     "ROLE_ADMIN"   ],   "name": "string",   "surname": "string" } | Signup new account |
| `POST` | {{baseUrl}}/users/signin | Body           | {   "username": "string",   "password": "string" }                                                                                                       | Login an account   |
| `GET`  | {{baseUrl}}/users/ | PathVariable   | username                                                                                                                                                 | Search User        |
| `GET`  | {{baseUrl}}/users/me | Empty          | Empty                                                                                                                                                    | Who Am I           |
| `POST` | {{baseUrl}}/invoice/create | Body           | {   "firstName": "string",   "lastName": "string",   "email": "string",   "amount": 0,   "productName": "string",   "billNo": "string" }                 | Create Invoice             |
| `GET`  | {{baseUrl}}/invoice/search  | RequestParam   | invoiceStatus=invoiceStatus&number=int&size=int <br/>InvoiceStatus available values : ACCEPT, REJECT                                                     | Search Invoice     |
___

## :package: Package With Maven

On project root file:

```console
$ mvn clean install
```

## :clipboard: Run With Java

```console
$ java -jar target/accounting-app-0.0.1-SNAPSHOT.jar
```

## :clipboard: Run With Docker

```console
$ docker build -t accounting-app -f Dockerfile .
$ docker run -p 9999:8080 accounting-app
```

## :clipboard: Run With Docker Compose

```console
$ docker compose-up
```

## Documentation

Swagger: http://localhost:8080/api-docs/swagger-ui/index.html
