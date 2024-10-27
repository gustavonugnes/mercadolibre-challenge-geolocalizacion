# Mercado Libre Challenge Geolocalización

## Overview

This application is a geolocation service built with **Java** and the **Spring Framework**.
It allows users to get geolocation data based on an IP address provided by the user.
It also allows retrieving some usage statistics.
The service was designed with high availability in mind, utilizing a cache with Redis.
A simple web page is provided to use the features of the service.

## Features

- Geolocation functionalities
- RESTful API for accessing geolocation data
- Integrated with MySQL for persistent data storage
- Runs in Docker containers for easy deployment

## Technologies Used

- **Java 21**
- **Spring Boot**
- **MySQL 8.0.22**
- **Docker**
- **Redis**

## External APIs

- **ipapi** (https://ipapi.com/)
  - To get geolocation data based on an IP address
- **fixer** (https://fixer.io/)
  - To get exchange rates for a country's currency

## Installation

To install and run the application, ensure you have Docker and Docker Compose installed on your machine. Then, run the following command:

```bash
docker-compose up -d --build
