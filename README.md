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

## Curl
| Description | Curl  | Token  | Role       |
|-------------|-------|--------|------------|
| Signup new account   | curl --location --request POST 'http://localhost:8080/users/signup' \ --header 'Content-Type: application/json' \ --data-raw '{     "username": "username-1",     "password": "password-1",     "email": "mail-1@mail.com",     "roleList": [         "ROLE_ACCOUNTANT"     ],         "name": "name-1",     "surname": "surname-1" }' | None   | None       |
| Login an account   | curl --location --request POST 'http://localhost:8080/users/signin' \ --header 'Content-Type: application/json' \ --data-raw '{     "username": "username-1",     "password": "password-1" }' | None   | None       |
| Search User   | curl --location --request GET 'http://localhost:8080/users/username-1' \ --header 'Authorization: Bearer ' | Bearer | ROLE_ADMIN |
| Who Am I   | curl --location --request GET 'http://localhost:8080/users/me' \ --header 'Authorization: Bearer ' |Bearer | NONE       |
| Create Invoice   | curl --location --request POST 'http://localhost:8080/invoice/create' \ --header 'Content-Type: application/json' \ --header 'Authorization: Bearer ' \ --data-raw '{     "firstName": "John",     "lastName": "Doe",     "email": "john@doe.com",     "amount": 400,     "productName": "USB DISC",     "billNo": "TR000" }' | Bearer |ROLE_ACCOUNTANT |
| Search Invoice   | curl --location --request GET 'http://localhost:8080/invoice/search?number=int&size=int&invoiceStatus=invoiceStatus' \ --header 'Authorization: Bearer ' |Bearer |ROLE_ACCOUNTANT |
