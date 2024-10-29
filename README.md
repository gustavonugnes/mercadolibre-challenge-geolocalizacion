# Mercado Libre Challenge Geolocalización

## Overview

This application is a geolocation service built with **Java** and the **Spring Framework**. 
It allows users to get geolocation data based on an IP address provided by the user.
It also allows retrieving relevant usage statistics.
The service was designed with high availability in mind, utilizing a cache with Redis.
A simple web page is provided to use the features of the service.

## Features

- Geolocation via a user-provided IP address
- Usage statistics, including minimum, maximum and average distance for the provided geolocation
- Runs in Docker containers for easy deployment
- MySQL for persistent storage and Redis for caching

## Technologies Used

- **Java 21**
- **Spring Boot**
- **MySQL 8.0.22**
- **JPA/Hibernate**
- **Docker**
- **Redis**
- **Maven**
- **Flyway**

## External APIs

- **ipapi** (https://ipapi.com/)
  - To get geolocation data based on an IP address
- **fixer** (https://fixer.io/)
  - To get exchange rates for a country's currency

## Installation

To install and run the application, ensure you have Docker and Docker Compose installed on your machine. 
Then, run the following command:

```bash
docker-compose up -d --build
```

Once all the docker images are up, you can make use of the application by accessing:
http://localhost:8080/

## Architecture

The application is structured into different layers to enhance separation of concerns, including **Controllers**,
**Services**, **Repositories**, and **Clients**.

- **Controllers** are responsible for handling user requests. They validate the request format, interact with services 
to obtain the required data and return the responses to the users.
- 
- **Services** act as intermediaries in the application. They contain the business logic and retrieve relevant 
information, formatting it appropriately for the controllers. Services can obtain data from external APIs through 
clients or communicate with repositories for data persistence and retrieval.

- **Repositories** are tasked with persisting and retrieving data from the MySQL database.

- **Clients** handle requests to external APIs and return results in a simplified format to the services.

Each layer may contain multiple classes, depending on the application’s specific concerns. For example, 
there may be a dedicated controller for IP requests and another for statistics.

### High Availability

To ensure the service operates efficiently under heavy request loads, a caching mechanism has been implemented for 
high-demand endpoints. Redis caching allows rapid data retrieval and helps prevent bottlenecks. Additionally, a 
scheduled task has been developed to evict all caches once daily at midnight, a time typically associated with lower 
demand. This timing can be configured for different times or cadences as needed.

### External APIs
The application consumes data from several third-party APIs to obtain necessary information.

- **ipapi**: This API is utilized for geolocation functionality. A request is made by sending the user-provided IP
  address, and a response is received containing essential data for the application's workflow, such as the
  user's coordinates and the corresponding country with its ISO code. However, since a free plan is used
  for the development of this application, we do not receive all available information from this API,
  such as timezone and currency data, which would be useful for other purposes.

  To overcome this limitation, we implemented a workaround for timezone data using the country code and
  name received from the API. We first attempt to find the appropriate locale based on the country's code
  and then retrieve the zone ID. If that does not work, we try to find the zone ID using the country's
  name. With the zone ID, we can obtain the current time in the respective timezone and format it
  appropriately (see `UtilsService` class). While this approach may not always work, it is effective in
  many cases.

  To acquire currency data, we explored another external service mentioned in the problem description,
  **CountryLayer** ([countrylayer.com](https://countrylayer.com/)). However, similar to our experience
  with ipapi, the currency data was not available with a free subscription. Thus, we implemented another
  workaround, again utilizing the country code to find the locale.

- **fixer**: This API is used to obtain the current exchange rate of the user's currency against the US dollar.
  Due to limitations in the free subscription, we had to implement this feature indirectly. We use the API's
  `/latest` endpoint, which provides a list of exchange rates for several currencies against the Euro.
  Since the base currency of this endpoint is fixed as the Euro, we cannot change it under the free
  subscription. To obtain the desired exchange rate, we take two values: the exchange rate of the Euro
  against the US dollar and the rate of the Euro against the user's currency. We then divide the first
  value by the second to derive the needed data.

### Additional Details

- A validation was added to `IPController` to check that the IP value provided by the user has the
  appropriate formatting.
- Test classes were added for controllers and especially for services to ensure the code behaves as expected.
- The application uses Flyway to run the necessary migrations for setting up the MySQL database schema.
- There is an `index.html` file that serves as a frontend for this backend service, allowing users to utilize
  the IP geolocation and statistics features.
- This application is designed to be deployed with Docker. The `docker-compose.xml` file will set up the
  necessary containers (MySQL, Redis, and the app itself). The `Dockerfile` will build and deploy the application.
- During the development of this application, external services were sometimes mocked by reading a hardcoded
  file to avoid limitations of the free subscription plans (100 requests/month for each service). The usage
  of real clients or mocks is configured with a property and an environment variable (by default, mocks are not used).
