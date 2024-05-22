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
[![codecov](https://codecov.io/gh/Ahmad45123/workup/graph/badge.svg?token=DICIEAQBM2)](https://codecov.io/gh/Ahmad45123/workup)

Welcome to WorkUp, a scalable distributed microservices application designed to replicate the core functionalities of Upwork. 🚀 This project leverages a suite of modern technologies to ensure blazingly fast performance 🔥, reliability, and scalability.

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

WorkUp is a microservices-based application that allows freelancers and clients to connect, collaborate, and complete projects, similar to Upwork. 💼 The system is designed with scalability and resilience in mind, utilizing various technologies to handle high traffic and large data volumes efficiently. 🚀

## 🏛️ Architecture

![workup_arch](https://github.com/Ahmad45123/workup/assets/37817681/832314bd-b77e-43fb-a75e-7f905e46d55c)


## 🧩 Components
Swarm on the machines

![image](https://github.com/Ahmad45123/workup/assets/35760882/27bcc9e8-316c-4248-b470-b975a6411962)
![image](https://github.com/Ahmad45123/workup/assets/35760882/4d2222b6-127d-4fb3-9f04-150b3f495daa)


### 🏗️ Microservices

All microservices are implemented using Java Spring Boot ☕. They consume messages from the RabbitMQ message broker 🐰 and respond through RabbitMQ as well. Requests are cached in Redis, so if the same request is sent more than once, there is no need to recompute the response every time as it can be retrieved directly from the cache 🗃️. All the microservices are stateless, as authentication and authorization are handled by the web server 🔐. In some cases, a microservice would have to communicate with another one to complete a certain functionality, which is done through RabbitMQ. Every microservice can be scaled up or down independently of other microservices to adapt to the amount of incoming traffic 📈. Every microservice has its database that is shared by all the instances of the same microservice but cannot be accessed by other microservices. All the microservices have multithreading, where a thread pool is created, and every request is assigned to a thread from that pool to allow asynchronous computation.

#### 👥 Users Microservice

This microservice handles user-related operations, like updating, fetching, deleting, and creating profiles, register & login 👤. In the case of register or login, a JWT is created and returned in the response, which will be used by the client to authorize future communications with the system 🔑. The database used for this microservice is MongoDB 🍃, as it achieves horizontal scalability, is highly available, and provides higher performance for reads and writes than relational databases. MongoDB 4.0 and later supports ACID transactions to some extent, which was enough for the users' microservice use case. 

#### 💳 Payments Microservice


This microservice handles payment-related requests 💳. Both freelancers and clients have wallets, which they can add or withdraw money from. Both of them have a history of transactions, and freelancers can issue payment requests that are then paid by the clients, and the money is deposited into the freelancer's wallet. When the payment is completed, the contracts service is notified that the contract associated with this payment should be marked as done. The DB used for this service is PostgreSQL 🐘, as payments require lots of ACID transactions in addition to strict consistency, which is achieved by relational databases.

#### 🔧 Jobs Microservice

This microservice handles jobs and proposal requests 📝. Clients can create jobs, and view, and accept proposals for their jobs. Freelancers can browse jobs and search for them using keywords 🔍. They can submit a proposal to any job, specifying the milestones that will achieve that job and any extra attachments. When a client accepts a proposal, the contracts service is notified about it to initiate a contract for that job 📜. The used DB for this microservice is Cassandra, as it can be scaled horizontally easily and is highly available and fault-tolerant, thanks to its peer-to-peer decentralized architecture. There is no need to have a relational DB for jobs, since there are not many joins performed, and there is no need for strong consistency and strict schema. However, high availability is critical, as most of the time users will be browsing jobs, which implies high traffic on the DB.

#### 📜 Contracts Microservice

This microservice is responsible for contract-related logic 📑. It handles the termination and creation of contracts. Freelancers can update their progress in a milestone of their contracts, while clients can add evaluation to every milestone. Cassandra was used as a database for this microservice for the same reasons as the jobs microservice: scalability, high availability, and fault tolerance.

### 📬 Message Queues

For this project, RabbitMQ was used for asynchronous communication between different components 🐰. A worker queue is assigned to every microservice, where all instances listen to it, while only one of them (the most available one) consumes the message and sends the response back if needed. In addition, a fanout exchange is created to allow the controller to send configuration messages that are consumed by all the running instances of a certain microservice.

### 🌐 Web Server

The web server is considered the API gateway for the backend of the system. It has a defined REST endpoint for every command in the four microservices. The web server takes the HTTP requests and converts them to message objects that can be sent and consumed by the designated microservice. After the execution is done, the web server receives the response as a message object, converts it to an HTTP response, and sends it back to the client. The web server handles authentication, as it checks the JWT sent with the request to determine whether the user is currently logged in 🔑. After that, it extracts the user ID from the JWT and attaches it to the message that is sent to the worker queues.

#### API documentation
##### Introduction
Welcome to the Workup API documentation. This API provides endpoints for managing jobs, proposals, contracts, payments, and user authentication.

##### Authentication
All endpoints require authentication using a bearer token. Include the token in the Authorization header of your requests. 

```bash
Authorization: Bearer <your_access_token>
```

#### Get Job
Description: Retrieves details of a specific job.

- URL: /api/v1/jobs/{job_id}
- Method: GET
Request Parameters
- job_id (path parameter): The ID of the job to retrieve.
- Example Request
```bash
GET /api/v1/jobs/7ddda13b-8221-4766-983d-9068a6592eba
Authorization: Bearer <your_access_token>
```

Response
- 200 OK: Returns the details of the requested job.

```json
{
  "id": "7ddda13b-8221-4766-983d-9068a6592eba",
  "title": "Sample Job",
  "description": "This is a sample job description.",
  ...
}
```
- 404 Not Found: If the job with the specified ID does not exist.

#### Get Proposals
Description: Retrieves all proposals for a specific job.
- URL: /api/v1/jobs/{job_id}/proposals
- Method: GET

Request Parameters
- job_id (path parameter): The ID of the job to retrieve - - - proposals for.
```bash
GET /api/v1/jobs/7ddda13b-8221-4766-983d-9068a6592eba/proposals
Authorization: Bearer <your_access_token>
```

Response
- 200 OK: Returns a list of proposals for the specified job.
```json
[
  {
    "id": "73fb1269-6e05-4756-93cc-947e10dac15e",
    "job_id": "7ddda13b-8221-4766-983d-9068a6592eba",
    "cover_letter": "Lorem ipsum dolor sit amet...",
    ...
  },
  ...
]

```
- 404 Not Found: If the job with the specified ID does not exist

#### Get Contract
Description: Retrieves details of a specific contract.

- URL: /api/v1/contracts/{contract_id}
- Method: GET

Request Parameters
- contract_id (path parameter): The ID of the contract to retrieve.


```bash
GET /api/v1/contracts/702a6e9a-343b-4b98-a86b-0565ee6d8ea5
Authorization: Bearer <your_access_token>
```

Response
- 200 OK: Returns the details of the requested contract.

```json
{
  "id": "702a6e9a-343b-4b98-a86b-0565ee6d8ea5",
  "client_id": "2d816b8f-592c-48c3-b66f-d7a1a4fd0c3a",
  ...
}

```


- 404 Not Found: If the contract with the specified ID does not exist.


#### Create Proposal
Description: Creates a new proposal for a specific job.

- URL: /api/v1/jobs/{job_id}/proposals
- Method: POST
Request Parameters
- job_id (path parameter): The ID of the job to create a proposal for.

Request Body
```json
{
  "coverLetter": "I am interested in this job...",
  "jobDuration": "LESS_THAN_A_MONTH",
  "milestones": [
    {
      "description": "First milestone",
      "amount": 500,
      "dueDate": "2024-06-01"
    },
    ...
  ]
}

```


Response
- 201 Created: Returns the newly created proposal.

```json
{
  "id": "73fb1269-6e05-4756-93cc-947e10dac15e",
  "job_id": "7ddda13b-8221-4766-983d-9068a6592eba",
  ...
}

```
- 404 Not Found: If the job with the specified ID does not exist.


#### Get Proposal
Description: Retrieves details of a specific proposal.

- URL: /api/v1/proposals/{proposal_id}
- Method: GET

Request Parameters
- proposal_id (path parameter): The ID of the proposal to retrieve.
```bash
GET /api/v1/proposals/73fb1269-6e05-4756-93cc-947e10dac15e
Authorization: Bearer <your_access_token>

```

Response
- 200 OK: Returns the details of the requested proposal.

```json
{
  "id": "73fb1269-6e05-4756-93cc-947e10dac15e",
  "job_id": "7ddda13b-8221-4766-983d-9068a6592eba",
  ...
}
```




- 404 Not Found: If the proposal with the specified ID does not exist.

##### Update Proposal
Description: Updates an existing proposal.

- URL: /api/v1/proposals/{proposal_id}
- Method: PUT

Request Parameters
- proposal_id (path parameter): The ID of the proposal to update.


Request Body
```json
{
  "coverLetter": "Updated cover letter...",
  "jobDuration": "ONE_TO_THREE_MONTHS",
  "milestones": [
    {
      "description": "Updated milestone",
      "amount": 600,
      "dueDate": "2024-06-15"
    },
    ...
  ]
}

```

Response
- 200 OK: Returns the updated proposal.
```json
{
  "id": "73fb1269-6e05-4756-93cc-947e10dac15e",
  "job_id": "7ddda13b-8221-4766-983d-9068a6592eba",
  ...
}
```
- 404 Not Found: If the proposal with the specified ID does not exist.

### 🎛️ Controller


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

### 📺 Media Server
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

## 🚀 Deployment

![Digital Ocean](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQxeW7i7mdvQqs7IdiokF0IIaenP9OvqO7NZLVNca&s)

We were able to setup a tenant on [Digital ocean](https://try.digitalocean.com/cloud/?utm_campaign=emea_brand_kw_en_cpc&utm_adgroup=Misspellings&_keyword=digitalocean%27&_device=c&_adposition=&utm_content=conversion&utm_medium=cpc&utm_source=google&gad_source=1&gclid=CjwKCAjwr7ayBhAPEiwA6EIGxFUoqeEw6G9c-Vh1FvlsGCa6kaL7uDHwQMuVojoNVrMNXKcXnJ906BoCFVwQAvD_BwE) 🌐 Where we can create new intance(Droplet) 💧  from the Controller as explained above and feeding scripts in the startup of this instance to join docker swarm 🐳 of other machines. 


We can monitor the performance of each running container on each instance using [Portainer](https://www.portainer.io/)  📊 where we can manually run more containers of the same service, and configure it to auto-scale when the load is above the threshold. 


We tested the app in the hosted state and even performed load testing on it.✅

## 📈 Auto-scaling

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

## 🧪 Testing

### Functional Testing

To test our app functionality we created functional testing for each service on its own to test functionality in isolation as well as testing its functionality in integration with other services for example [here](services\payments\src\test\java\com\workup\payments\PaymentsApplicationTests.java) is the tests implemented for Payments service.🧪🔧

### ⚖️ Load Balancing

![jmeter](https://i0.wp.com/cdn-images-1.medium.com/max/800/1*KeuQ7uNalz2l4rBOyPAUpg.png?w=1180&ssl=1)

We used JMeter to load test our app we configured it to simulate thousands of users' requests and the number grew gradually over the span of 10 seconds. here are some results for different endpoints. Here are a few examples of endpoint performance.

#### Login Performance
![login command](https://media.discordapp.net/attachments/1210626240986226741/1241779393614057562/image.png?ex=664ebc6e&is=664d6aee&hm=f33b046fa5bb5117cf1b75dd70d34f3b6bbcb2e11f6e639e3dc1bf3802ff7b79&=&format=webp&quality=lossless)

#### Create Job

![Create Job](https://media.discordapp.net/attachments/1210626240986226741/1241782178824716339/image.png?ex=664ebf06&is=664d6d86&hm=673ee8ac6d1d218aa49aa0491e96f11dd3f536a8f69d6fb69d8e47bf14863a84&=&format=webp&quality=lossless)

#### Get Jobs

![get jobs](https://media.discordapp.net/attachments/1210626240986226741/1241787039603490858/image.png?ex=664ec38d&is=664d720d&hm=61b2c43df718b627191b90be4c24ff6d870e44b86294546b73da0f566015cdcc&=&format=webp&quality=lossless)

#### Get Contracts
![get contracts](https://media.discordapp.net/attachments/1210626240986226741/1241803839947145387/image.png?ex=664ed333&is=664d81b3&hm=2ab4e1c58e3224a0c0f6ef8e4eee08330fb8e68e914ef2e3f586bc16f41d0b90&=&format=webp&quality=lossless)



![locust](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOchmJvYjMucoFZDd2NVX6uLPNci5uEhBzF1vHDJMJ0Q&s)

We alos used python [locust](https://locust.io/) for load testing. check the test file [here](https://github.com/Ahmad45123/workup/blob/main/locustfile.py). Here is the the results of this load test

![requeststats](https://media.discordapp.net/attachments/1210626240986226741/1242789353558769664/image.png?ex=664f1d47&is=664dcbc7&hm=c6838d6f086a3f04688f7189fa042a0f3d07dee9026f389f804ef56fad88be84&=&format=webp&quality=lossless&width=863&height=676)

![charts](https://media.discordapp.net/attachments/1210626240986226741/1242789434923946076/image.png?ex=664f1d5b&is=664dcbdb&hm=d551fd87c5ebc96de32e9119ff41596bf54de326491ce3007f4a6f695865f1fb&=&format=webp&quality=lossless&width=861&height=676)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
