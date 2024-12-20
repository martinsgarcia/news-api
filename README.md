# News-API

[![Java Maven CI CodeClimate](https://github.com/martinsgarcia/news-api/actions/workflows/java-maven-ci-codeclimate.yml/badge.svg)](https://github.com/martinsgarcia/news-api/actions/workflows/java-maven-ci-codeclimate.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/583c765582c9f252aaf9/maintainability)](https://codeclimate.com/github/martinsgarcia/news-api/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/583c765582c9f252aaf9/test_coverage)](https://codeclimate.com/github/martinsgarcia/news-api/test_coverage)

## Overview

This project is a Java application developed using Java 17, Spring Boot 3.2.1, Hibernate, and QueryDSL. It includes Swagger for API documentation, and JUnit for unit testing. The purpose of this README is to provide instructions and information for developers who are working on or reviewing the codebase.

**Note: This application is a proof of concept, and as such, the database is running in-memory using H2.**

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Project Structure](#project-structure)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [License](#license)

## Prerequisites

Ensure you have the following tools installed:

- Java 17
- Maven
- Your preferred IDE (e.g., Eclipse, IntelliJ, Visual Studio Code)

## Setup

1. Clone the repository. 
2. Open the project in your IDE.
3. Build the project using Maven:
```bash
mvn clean install
```

## Project Structure

The project follows a standard Maven directory structure. Key directories include:

- src/main/java: Main source code.
- src/main/resources: Configuration files and static resources.
- src/test: Unit and integration tests.
- target: Compiled bytecode and build artifacts.

## Running the Application

To run the application locally, use the following Maven command:

```bash
mvn spring-boot:run
```

##  API Documentation

Swagger has been integrated for API documentation. After starting the application, you can access the Swagger UI at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Testing

Unit tests use JUnit. Run the tests with:

```bash
mvn test
```

## Code Climate
Check the quality of this code by accessing the link: [https://codeclimate.com/github/martinsgarcia/news-api]

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute the code.
