![Workup](https://i.imgur.com/kMMN6As.png)

# WorkUp: A Scalable Distributed Microservices Application
![ApacheCassandra](https://img.shields.io/badge/cassandra-%231287B1.svg?style=for-the-badge&logo=apache-cassandra&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-%23FF6600.svg?style=for-the-badge&amp;logo=rabbitmq&amp;logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![](https://img.shields.io/badge/Node%20js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)
![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![](https://img.shields.io/badge/DATADOG-632CA6?style=for-the-badge&logo=datadog&logoColor=white)
![](https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000)
![](https://img.shields.io/badge/New-Relic-1CE783?style=for-the-badge&logo=newrelic&logoColor=white)
![](https://img.shields.io/badge/UpWork-6FDA44?style=for-the-badge&logo=Upwork&logoColor=white)
![](https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-badge&logo=githubactions&logoColor=367cfe)


## Builds

![build](https://github.com/Ahmad45123/workup/actions/workflows/maven.yml/badge.svg)
![build](https://github.com/Ahmad45123/workup/actions/workflows/maven-publish.yaml/badge.svg)
![build](https://github.com/Ahmad45123/workup/actions/workflows/maven-temp.yml/badge.svg)

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

All microservices are implemented using Java Spring Boot ☕. They consume messages from the RabbitMQ message broker 🐰 and respond through RabbitMQ as well. Requests are cached in Redis, so if the same request is sent more than once, there is no need to recompute the response every time as it can be retrieved directly from the cache 🗃️. All the microservices are stateless, as authentication and authorization are handled by the web server 🔐. In some cases, a microservice would have to communicate with another one to complete a certain functionality, which is done through RabbitMQ. Every microservice can be scaled up or down independently of other microservices to adapt to the amount of incoming traffic 📈. Every microservice has its own database that is shared by all the instances of the same microservice but cannot be accessed by other microservices 💾. All the microservices have multithreading, where a thread pool is created, and every request is assigned to a thread from that pool to allow asynchronous computation. 🌐

#### Users Microservice

This microservice handles user-related operations, like updating, fetching, deleting, and creating profiles, register & login 👤. In the case of register or login, a JWT is created and returned in the response, which will be used by the client to authorize future communications with the system 🔑. The database used for this microservice is MongoDB 🍃, as it achieves horizontal scalability, is highly available, and provides higher performance for reads and writes than relational databases. MongoDB 4.0 and later supports ACID transactions to some extent, which was enough for the users' microservice use case. ✅

#### Payments Microservice

This microservice handles payment-related requests 💳. Both freelancers and clients have wallets, which they can add or withdraw money from. Both of them have a history of transactions, and freelancers can issue payment requests that are then paid by the clients, and the money is deposited into the freelancer's wallet. When the payment is completed, the contracts service is notified that the contract associated with this payment should be marked as done 📜. The DB used for this service is PostgreSQL 🐘, as payments require lots of ACID transactions in addition to strict consistency, which is achieved by relational databases.

#### Jobs Microservice

This microservice handles jobs and proposal requests 📝. Clients can create jobs, and view, and accept proposals for their jobs. Freelancers can browse jobs and search for them using keywords 🔍. They can submit a proposal to any job, specifying the milestones that will achieve that job and any extra attachments. When a client accepts a proposal, the contracts service is notified about it to initiate a contract for that job 📜. The used DB for this microservice is Cassandra, as it can be scaled horizontally easily and is highly available and fault-tolerant, thanks to its peer-to-peer decentralized architecture. There is no need to have a relational DB for jobs, since there are not many joins performed, and there is no need for strong consistency and strict schema. However, high availability is critical, as most of the time users will be browsing jobs, which implies high traffic on the DB 🌐.

#### Contracts Microservice


This microservice is responsible for contract-related logic 📑. It handles the termination and creation of contracts. Freelancers can update their progress in a milestone of their contracts, while clients can add evaluation to every milestone. Cassandra was used as a database for this microservice for the same reasons as the jobs microservice: scalability, high availability, and fault tolerance 🌐.

### Message Queues

For this project, RabbitMQ was used for asynchronous communication between different components 🐰. A worker queue is assigned to every microservice, where all instances listen to it, while only one of them (the most available one) consumes the message and sends the response back if needed. In addition, a fanout exchange is created to allow the controller to send configuration messages that are consumed by all the running instances of a certain microservice 📡.

### Web Server

The web server is considered the API gateway for the backend of the system 🌐. It has a defined REST endpoint for every command in the four microservices. The web server takes the HTTP requests and converts them to message objects that can be sent and consumed by the designated microservice. After the execution is done, the web server receives the response as a message object, converts it to an HTTP response, and sends it back to the client. The web server handles authentication, as it checks the JWT sent with the request to determine whether the user is currently logged in 🔑. After that, it extracts the user ID from the JWT and attaches it to the message that is sent to the worker queues.

### Controller


The controller provides a CLI that is used to broadcast configuration messages to all the running instances of a certain microservice 🖥️. These messages configure the services in runtime without having to redeploy any instance, to achieve zero downtime updates ⏲️. Here are the messages sent by the controller:

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
A media server that serves static resources.

#### ENV 

- `UPLOAD_USER`
- `UPLOAD_PASSWORD`
- `DISCOGS_API_KEY`


#### Static Endpoints

For all static resources, this server will attempt to return a relevant resource 📁, or else if the resource does not exist, it will return a default 'placeholder' resource 🖼️. This prevents clients from having no resource to display at all; clients can make use of this media server's 'describe' endpoint to learn about what resources are available 📋.

#### `GET` /static/icons/:icon.png

Returns an icon based on the filename.

`:icon` can be any icon filename.

Example: return an icon with filename 'accept.png'.

```bash
curl --request GET \
  --url http://path_to_server/static/icons/accept.png
```

#### `GET` /static/resume/:resume.pdf
 
Returns a resume based on the filename.

`:resume` can be any resume filename.

Example: return a resume with filename 'resume.pdf'.

```bash
curl --request GET \
  --url http://path_to_server/static/resume/resume.pdf
```


#### Describe

#### `GET` /describe

Returns a JSON representation of the media groups.

Example: return JSON containing of all groups present.

```bash
curl --request GET \
  --url http://localhost:8910/describe
```

```json
{
  "success": true,
  "path": "/static/",
  "groups": ["icons", "resume"]
}
```

#### `GET` /describe/:group

Returns a JSON representation of all the files current present for a given group.

`:group` can be any valid group.

Example: return JSON containing all the media resources for a the exec resource group.

```bash
curl --request GET \
  --url http://localhost:8910/describe/exec
```

```json
{
  "success": true,
  "path": "/static/exec/",
  "mimeType": "image/jpeg",
  "files": []
}
```

#### Upload

Upload and convert media to any of the given static resource group.

All upload routes are protected by basic HTTP auth. The credentials are defined by ENV variables `UPLOAD_USER` and `UPLOAD_PASSWORD`.

#### `POST` /upload/:group

POST a resource to a given group, assigning that resource a given filename.

`:group` can be any valid group.

```bash
curl --location 'http://path-to-server/upload/resume/' \
--header 'Authorization: Basic dXNlcjpwYXNz' \
--form 'resource=@"/C:/Users/ibrah/Downloads/Ibrahim_Abou_Elenein_Resume.pdf"' \
--form 'filename="aboueleyes-reume-2"'
```

```json
{
  "success": true,
  "path": "/static/resume/aboueleyes-reume-2.pdf"
}
```

A resource at `http://path_to_server/static/resume/aboueleyes-reume-2.pdf` will now be available.

## Deployment

![Digital Ocean](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQxeW7i7mdvQqs7IdiokF0IIaenP9OvqO7NZLVNca&s)

We were able to setup a tenant on [Digital ocean](https://try.digitalocean.com/cloud/?utm_campaign=emea_brand_kw_en_cpc&utm_adgroup=Misspellings&_keyword=digitalocean%27&_device=c&_adposition=&utm_content=conversion&utm_medium=cpc&utm_source=google&gad_source=1&gclid=CjwKCAjwr7ayBhAPEiwA6EIGxFUoqeEw6G9c-Vh1FvlsGCa6kaL7uDHwQMuVojoNVrMNXKcXnJ906BoCFVwQAvD_BwE) 🌐 Where we can create new intance(Droplet) 💧  from the Controller as explained above and feeding scripts in the startup of this instance to join docker swarm 🐳 of other machines. 


We can monitor the performance of each running container on each instance using [Portainer](https://www.portainer.io/)  📊 where we can manually run more containers of the same service, configure it to auto scale when load is above the threshold. 


We tested the app in the hosted state and eveny performed the load testing on it.✅

## Auto-scaling

his script utilizes Prometheus paired with cAdvisor metrics to determine CPU usage 📊. It then utilizes a manager node to determine if a service wants to be autoscaled and uses another manager node to scale the service.

Currently, the project only utilizes CPU to autoscale. If CPU usage reaches 85%, the service will scale up; if it reaches 25%, it will scale down ⬆️⬇️.

2. if you want to autoscale you will need a deploy label `swarm.autoscaler=true`. 

```
deploy:
  labels:
    - "swarm.autoscaler=true"
```

This is best paired with resource constraints limits. This is also under the deploy key.

```
deploy:
  resources:
    reservations:
      cpus: '0.25'
      memory: 512M
    limits:
      cpus: '0.50'
```

## Configuration
| Setting | Value | Description |
| --- | --- | --- |
| `swarm.autoscaler` | `true` | Required. This enables autoscaling for a service. Anything other than `true` will not enable it |
| `swarm.autoscaler.minimum` | Integer | Optional. This is the minimum number of replicas wanted for a service. The autoscaler will not downscale below this number |
| `swarm.autoscaler.maximum` | Integer | Optional. This is the maximum number of replicas wanted for a service. The autoscaler will not scale up past this number | 

## Load Balancing

We used a round-robin-based load balancing 🔄 that is handled by Docker in the Swarm 🐳. Simply, it sends the first request to the first instance of the app (not for a machine) and the next request to the next instance, and so on until all instances have received a request. Then, it starts again from the beginning. 🚀

## Testing

### Functional Testing

To test our app functionality we created functional testing for each serice on its own to test functionality in isolation as well as testing its functionality in integeration with other services for example [here](services\payments\src\test\java\com\workup\payments\PaymentsApplicationTests.java) is the tests implemented for Payments service.🧪🔧

### Load Testing

![jmeter](data:image/png;base64,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)

We used JMeter to load test our app we configured it to simulate thousands of users' requests and the number grows gradually over the span of 10 seconds. here are some results for different endpoints. Here are few examples for endoints performance.

#### Login Performance
![login command](https://media.discordapp.net/attachments/1210626240986226741/1241779393614057562/image.png?ex=664ebc6e&is=664d6aee&hm=f33b046fa5bb5117cf1b75dd70d34f3b6bbcb2e11f6e639e3dc1bf3802ff7b79&=&format=webp&quality=lossless)

#### Create Job

![Create Job](https://media.discordapp.net/attachments/1210626240986226741/1241782178824716339/image.png?ex=664ebf06&is=664d6d86&hm=673ee8ac6d1d218aa49aa0491e96f11dd3f536a8f69d6fb69d8e47bf14863a84&=&format=webp&quality=lossless)

#### Get Jobs

![get jobs](https://media.discordapp.net/attachments/1210626240986226741/1241787039603490858/image.png?ex=664ec38d&is=664d720d&hm=61b2c43df718b627191b90be4c24ff6d870e44b86294546b73da0f566015cdcc&=&format=webp&quality=lossless)

#### Get Contracts
![get contracts](https://media.discordapp.net/attachments/1210626240986226741/1241803839947145387/image.png?ex=664ed333&is=664d81b3&hm=2ab4e1c58e3224a0c0f6ef8e4eee08330fb8e68e914ef2e3f586bc16f41d0b90&=&format=webp&quality=lossless)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
