![Workup](https://i.imgur.com/kMMN6As.png)

# WorkUp: A Scalable Distributed Microservices Application

Welcome to WorkUp, a scalable distributed microservices application designed to replicate the core functionalities of Upwork. This project leverages a suite of modern technologies to ensure high performance, reliability, and scalability.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Components](#Components)
   - [Microservices](#microservices)
     - [Users microservice](#users-microservice)
     - [Payments microservice](#payments-microservice)
     - [Jobs microservice](#jobs-microservice)
     - [Contracts microservice](#contracts-microservice)
   - [Message Queues](#message-queues)
   - [Web server](#web-server)
   - [Controller](#controller)
   - [Auto-scaler](#auto-scaler)
4. [Testing](#testing)
5. [Contributing](#contributing)
6. [License](#license)

## Project Overview

WorkUp is a microservices-based application that allows freelancers and clients to connect, collaborate, and complete projects. The system is designed with scalability and resilience in mind, utilizing various technologies to handle high traffic and large data volumes efficiently.

## Architecture

Nasser insert photo xD.

## Componenets

### Microservices

All microservices are implemented using Java Spring Boot. They consume messages from the RabbitMQ message broker, and respond to them through RabbitMQ as well. Requests are cached in Redis, so that if the same request is sent more than once, there is no need to recompute the response everytime as it can be retreived directly from cache. All the microservices are stateless, as authentication and authorization is handled by the webserver. In some cases, a microservice would have to communicated with another one to complete a certain functionality, which is done through RabbitMQ. Every microservice can be scaled up or down independent of other microservices to adapt to the amount of incoming traffic. Every microservice has its own databse that is shared by all the instances of the same microservice, but can not be accessed by other microservices.

#### Users microservice

This microservice handles user related operations, like updating, fetching, deleting and creating profiles, redister & login. In case of register or login, a JWT is created and retured in the response, that will be used by the client to autherize future communications with the system. The databse used for this microservice is MongoDB, as it acheives horizontally scalable, highlyy available and provide higher performence for reads and writes than relational databases. MongoDB 4.0 and later supports ACID transactions to some extent that was enough for the users microservice use case.

#### Payments microservice

This microservice handles payment related requests. Both freelancers and clients have wallets, which they can add or withdraw money from. Both of them has a history of transactions, and freelancers can issue payments requests that are then paid by the clients, and the money is deposited to the freelancers wallet. When the payment is completed, the contracts service is notified that the contract assiciated with this payment to mark it as done.

#### Jobs microservice

#### Contracts microservice

### Message Queues

### Web server

### Controller

### Auto-scaler

## Testing

Each service contains unit and integration tests. You can run these tests using Maven:

```sh
mvn test
```

Ensure all tests pass before deploying the application.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
