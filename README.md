# Career-hub

# Introduction

This project, Career Hub, is a web-based career placement platform designed to connect top university students with potential employers. Universities can register and recommend their best students by submitting their profiles and resumes. Companies can then browse these profiles, evaluate candidates, and hire suitable individuals for job opportunities. The system facilitates seamless interaction between students, universities, and employers to support efficient and transparent recruitment.


## Problem statement

Many talented university students face difficulties in securing job opportunities due to a lack of visibility and limited connections with potential employers. Simultaneously, companies often struggle to find motivated, qualified candidates who align with their values and long-term goals. This disconnect between educational institutions and the job market hinders both student career growth and company talent acquisition. Career Hub addresses this issue by providing a centralized platform where universities can promote their top students and companies can easily discover, evaluate, and hire emerging talent. For companies, this also presents an opportunity to recruit young, promising individuals early and invest in their professional development — building a strong, loyal workforce from the ground up.

## Objectives

- To bridge the gap between universities, students, and companies in the recruitment process.
- To provide universities with a platform to promote their top-performing students and increase their employment prospects.
- To give companies access to a curated pool of qualified, motivated young talent.
- To help students showcase their skills, achievements, and resumes to potential employers in a professional environment.
- To support companies in identifying, hiring, and developing future professionals early in their careers.
- To create a transparent, efficient, and scalable system for managing university-to-industry talent pipelines.

## Technology Stack

- Frontend: ReactJS – for building dynamic and responsive user interfaces.
- Backend: Java, Spring Boot – for developing robust RESTful microservices.
- Database: PostgreSQL – as the primary relational database for data storage.
- Architecture: Microservices – to ensure modularity, scalability, and independent service deployment.
- Messaging: Apache Kafka – for asynchronous communication between services.
- Security: Spring Security with JWT – for secure authentication and authorization.
- Email Services: JavaMailSender – for sending notifications such as registration confirmations and application updates.
- Service Communication: Feign Client – for simplifying HTTP calls between microservices.
- Service Discovery: Spring Cloud with Eureka – for dynamic service registration and discovery.

## Installation Instructions
### Backend

### Prerequisites
Ensure you have the following installed:

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL (optional locally if not using Docker)
- Apache Kafka



#### 1. Clone the repository
    git clone https://github.com/kakhramanovruslan/Career-hub_17P.git
#### 2. The current version of the project is located in the dev branch. To access all the latest files, please make sure to switch to the dev branch.
    git checkout -b dev origin/dev
#### 3. Navigate into the project directory
    cd career-hub
#### 4. Start docker-compose.yml file in each of service
    docker-compose up -d

#### Make sure the following services are running:
    PostgreSQL (on localhost:5432)
    Kafka & Zookeeper
    Eureka Discovery Server (usually on http://localhost:8761)

#### 4. Configure Environment
Update each microservice's application.yml or file:

    Database URL, username, password
    Kafka broker address
    Eureka server URL
    JWT secret keys
    Mail server config (if needed)

#### 5. Build all service
    mvn clean install

#### 6. Run each microservice
    mvn spring-boot:run



### Frontend
#### 1. Clone the repository
    git clone https://github.com/suzerain-r/career-hub-front
#### 2. Navigate into the project directory
    cd career-hub-front
#### 3. Install dependencies
    npm install
#### 4. Start the application
    npm start

# Usage Instructions
## When application is fully started, this page must be opened automatically
![Image](/screenshots/mainPage.png)

## Also with role of admin with credentials: admin, admin you can create a university or company
![Image](/screenshots/createUniComp.png)

## Then you should sign-in to the system and then you can see navigation tab on top of the page
![Image](/screenshots/afterRegister.png)

![Image](/screenshots/candidatesPage.png)

![Image](/screenshots/universitiesPage.png)

![Image](/screenshots/companiesPage.png)

## Also you can see your profile and update it if needed
![Image](/screenshots/profilePage.png)

## If you signed in with credentials of university(means your role is university) you have opportunity of creating an account for students of your university
![Image](/screenshots/createStudent.png)

## Also you can see the profile of the candidates and you can send review for them
![Image](/screenshots/candidateProfilePage.png)


# Team Members
- Zhenisbek Olzhas, 220103388, 17-P
- Saduakas Rassul, 220103231, 17-P
- Kakhramanov Ruslan, 220103329, 17-P
- Toktamyssov Bekzhan, 220103114, 17-P
- Muratbekuly Meiirzhan, 230103036, 17-P
