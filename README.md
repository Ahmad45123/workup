![Workup](https://i.imgur.com/kMMN6As.png)

# WorkUp: A Scalable Distributed Microservices Application
![ApacheCassandra](https://img.shields.io/badge/cassandra-%231287B1.svg?style=for-the-badge&logo=apache-cassandra&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)


Welcome to WorkUp, a scalable distributed microservices application designed to replicate the core functionalities of Upwork. 🚀 This project leverages a suite of modern technologies to ensure blazingly fast performance 🔥, reliability, and scalability. 💪

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Components](#components)
   - [Microservices](#microservices)
     - [Users Microservice](#users-microservice)
     - [Payments Microservice](#payments-microservice)
     - [Jobs Microservice](#jobs-microservice)
     - [Contracts Microservice](#contracts-microservice)
   - [Message Queues](#message-queues)
   - [Web Server](#web-server)
   - [Controller](#controller)
   - [Auto-scaler](#auto-scaler)
4. [Testing](#testing)
5. [Contributing](#contributing)
6. [License](#license)

## Project Overview

WorkUp is a microservices-based application that allows freelancers and clients to connect, collaborate, and complete projects, similar to Upwork. 🤝💼 The system is designed with scalability and resilience in mind, utilizing various technologies to handle high traffic and large data volumes efficiently. 🚀📈

## Architecture

![workup_arch](https://github.com/Ahmad45123/workup/assets/37817681/832314bd-b77e-43fb-a75e-7f905e46d55c)


## Components
Swarm on the machines

![image](https://github.com/Ahmad45123/workup/assets/35760882/27bcc9e8-316c-4248-b470-b975a6411962)
![image](https://github.com/Ahmad45123/workup/assets/35760882/4d2222b6-127d-4fb3-9f04-150b3f495daa)


### Microservices

All microservices are implemented using Java Spring Boot. They consume messages from the RabbitMQ message broker and respond through RabbitMQ as well. Requests are cached in Redis, so if the same request is sent more than once, there is no need to recompute the response every time as it can be retrieved directly from the cache. All the microservices are stateless, as authentication and authorization are handled by the web server. In some cases, a microservice would have to communicate with another one to complete a certain functionality, which is done through RabbitMQ. Every microservice can be scaled up or down independently of other microservices to adapt to the amount of incoming traffic. Every microservice has its own database that is shared by all the instances of the same microservice but cannot be accessed by other microservices. All the microservices have multithreading, where a thread pool is created, and every request is assigned to a thread from that pool to allow asynchronous computation.

#### Users Microservice

This microservice handles user-related operations, like updating, fetching, deleting, and creating profiles, register & login. In the case of register or login, a JWT is created and returned in the response, which will be used by the client to authorize future communications with the system. The database used for this microservice is MongoDB, as it achieves horizontal scalability, is highly available, and provides higher performance for reads and writes than relational databases. MongoDB 4.0 and later supports ACID transactions to some extent, which was enough for the users' microservice use case.

#### Payments Microservice

This microservice handles payment-related requests. Both freelancers and clients have wallets, which they can add or withdraw money from. Both of them have a history of transactions, and freelancers can issue payment requests that are then paid by the clients, and the money is deposited into the freelancer's wallet. When the payment is completed, the contracts service is notified that the contract associated with this payment should be marked as done. The DB used for this service is PostgreSQL, as payments require lots of ACID transactions in addition to strict consistency, which is achieved by relational databases.

#### Jobs Microservice

This microservice handles jobs and proposal requests. Clients can create jobs, and view, and accept proposals for their jobs. Freelancers can browse jobs and search for them using keywords. They can submit a proposal to any job, specifying the milestones that will achieve that job and any extra attachments. When a client accepts a proposal, the contracts service is notified about it to initiate a contract for that job. The used DB for this microservice is Cassandra, as it can be scaled horizontally easily and is highly available and fault-tolerant, thanks to its peer-to-peer decentralized architecture. There is no need to have a relational DB for jobs, since there are not many joins performed, and there is no need for strong consistency and strict schema. However, high availability is critical, as most of the time users will be browsing jobs, which implies high traffic on the DB.

#### Contracts Microservice

This microservice is responsible for contract-related logic. It handles the termination and creation of contracts. Freelancers can update their progress in a milestone of their contracts, while clients can add evaluation to every milestone. Cassandra was used as a database for this microservice for the same reasons as the jobs microservice.

### Message Queues

For this project, RabbitMQ was used for asynchronous communication between different components. A worker queue is assigned to every microservice, where all instances listen to it, while only one of them (the most available one) consumes the message and sends the response back if needed. In addition, a fanout exchange is created to allow the controller to send configuration messages that are consumed by all the running instances of a certain microservice.

### Web Server

The web server is considered the API gateway for the backend of the system. It has a defined REST endpoint for every command in the 4 microservices. The web server takes the HTTP requests and converts them to message objects that can be sent and consumed by the designated microservice. After the execution is done, the web server receives the response as a message object, converts it to an HTTP response, and sends it back to the client. The web server handles authentication, as it checks the JWT sent with the request to know whether the user is currently logged in. After that, it extracts the user ID from the JWT and attaches it to the message that is sent to the worker queues.

### Controller

The controller provides a CLI that is used to broadcast configuration messages to all the running instances of a certain microservice. These messages configure the services in runtime without having to redeploy any instance, to achieve zero downtime updates. Here are the messages sent by the controller:

- `setMaxThreads`: Sets the maximum size of the thread pool used by a microservice.
- `setMaxDbConnections`: Sets the maximum number of database connections in the connections pool for a microservice. This is used only for the payments microservice since it is the only one using PostgreSQL, which allows connection pooling.
- `freeze`: Makes the microservice stop consuming any new messages, finishes the execution of any messages received by them, then releases all resources.
- `start`: Restarts a frozen microservice.
- `deleteCommand`: Deletes a command in a certain microservice in runtime. So the microservice no longer accepts requests of a certain type without having to redeploy all instances.
- `updateCommand`: Updates the logic performed by a certain command in runtime by byte code manipulation, which allows runtime updates while having zero downtime.
- `addCommand`: Adds a command that was deleted before from the microservice, to allow it to accept requests of that type again.
- `setLoggingLevel`: Sets the level of logs that should be logged (ERROR, INFO, etc.).
- `spawnMachine`: Replicates the whole backend on a new machine (the web server, the 4 microservices, the messaging queue, etc.).

### Media Server

## Deployment

## Auto-scaling

## Load Balancing

## Testing

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
