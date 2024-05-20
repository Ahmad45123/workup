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

WorkUp is a microservices-based application that allows freelancers and clients to connect, collaborate, and complete projects (similar to Upwork). The system is designed with scalability and resilience in mind, utilizing various technologies to handle high traffic and large data volumes efficiently.

## Architecture

Nasser insert photo xD.

## Componenets

### Microservices

All microservices are implemented using Java Spring Boot. They consume messages from the RabbitMQ message broker, and respond to them through RabbitMQ as well. Requests are cached in Redis, so that if the same request is sent more than once, there is no need to recompute the response everytime as it can be retreived directly from cache. All the microservices are stateless, as authentication and authorization is handled by the webserver. In some cases, a microservice would have to communicated with another one to complete a certain functionality, which is done through RabbitMQ. Every microservice can be scaled up or down independent of other microservices to adapt to the amount of incoming traffic. Every microservice has its own databse that is shared by all the instances of the same microservice, but can not be accessed by other microservices. All the microservices have multithreading, where a threadpool is created, and every rrequest is assigned to a thread from that pool to allow asynchronous computation.

#### Users microservice

This microservice handles user related operations, like updating, fetching, deleting and creating profiles, redister & login. In case of register or login, a JWT is created and retured in the response, that will be used by the client to autherize future communications with the system. The databse used for this microservice is MongoDB, as it acheives horizontally scalable, highlyy available and provide higher performence for reads and writes than relational databases. MongoDB 4.0 and later supports ACID transactions to some extent that was enough for the users microservice use case.

#### Payments microservice

This microservice handles payment related requests. Both freelancers and clients have wallets, which they can add or withdraw money from. Both of them has a history of transactions, and freelancers can issue payments requests that are then paid by the clients, and the money is deposited to the freelancers wallet. When the payment is completed, the contracts service is notified that the contract assiciated with this payment to mark it as done. The DB used for this service is PostgreSQL, as payments require lots of ACID transactions in addition to strict consistency, which is acheived by relational databases.

#### Jobs microservice

This microservices handles jobs and proposals requests. Clients can create jobs, view and accept proposals to their jobs. Freelancers can browse jobs and search for them using key words. They can submit a proposal to any job, specifying the milestones that will acheive that job and any extra attachments. When a client accepts a proposal, the contracts service is notified about it to initiate a contract for that job. The used DB for this micro-service is Cassandra, as it can be scaled horizontally easily and is highly available and fault tolerant, thanks to its peer to peer decentralized architecture. There is no need to have a relationa DB for jobs, since there are not many joins performed, and there is no need for strong consistency and strict schema. However, high availability is critical, as most of the time users will be browsing jobs, which implies high traffic on the DB.

#### Contracts microservice

This microservice is responsible for contract related logic. It handles the termination and creation of contracts. Freelancers can update their progress in a milestone of their contracts, while clients can add evaluation to every milestone. Cassandra was used as a databse for this microservice for the same reasons as jobs microservice.

### Message Queues

For this project, RabbitMQ was used for asynchronous communication between different comonents. A worker queue is assigned to every microservice, where all instances listen to, while only one of them (the most available one) consumes the message and send the response back if needed. In addition, a fanout exchange is created to allow the controller to send configuration messages that are consumed by all the running instances of a certain microservice.

### Web server

The webserver is considered as the API gateway for the backend of the system. It has a defined REST endpoint for every command in the 4 microservices. The webserver takes the HTTP requests and convert them to message objects that can be sent and consumed by the designated microservice. After the execution is done, the webserver receives the response as a message object, converts it to a HTTP response and send it back to the client. The webserver handles authentication, as it checks the JWT sent with the request to know whether the user is currently logged in. After that, it extracts the user ID from the JWT and attach it to the message that is sent to the worker queues.

### Controller

The controller provides a CLI that is used to broadcast configuration messages to all the running instances of a certain microservice. These messages configure the services in runtime without having to redeploy any instance, to acheive zero down time updates. Here are the messages sent by the controller:

- setMaxThreads: Sets the maximum size of the thread pool used by a microservice.
- setMaxDbConnections: Sets the maximum number of of database connections in the connections pool for a microservice. This is used only for the payments microservice, since it is the only one using postgres, which allows connections pooling.
- freeze: makes the microservice stop consuming any new messages, and finishes the execution of any messages received by them, then release all resources.
- start: restarts a frozen microservice.
- deleteCommand: deletes a command in a certain microservice in runtime. So the microservice no longer accepts requests of a certain type without having to redeploy all instance.
- updateCommand: updates the logic performed by a certain command in runtime by byte code manipulation, which allows runtime updates while having zero downtime.
- addCommand: adds a command that was deleted before from the microservice, to allow it to acceot requests of that type again.
- setLoggingLevel: sets the level of logs that should be logged (ERROR, INFO, etc.).
- spawnMachine: replicates the whole backend on a new machine (the webserver, the 4 microservices, the messaging queue, etc.).

### Media server

## Deployment

## Auto-scaling

## Load balancing

## Testing

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
