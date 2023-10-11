# Nodo Data Migration

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=pagopa-api-config-data-migration&metric=alert_status)](https://sonarcloud.io/dashboard?id=pagopa-nodo-cfg-data-migration)

A microservice that permits the migration from Nexi's Oracle database to PagoPA's PostgreSQL database

TODO: generate a index with this tool: https://ecotrust-canada.github.io/markdown-toc/

---

## Api Documentation 📖

See the [OpenApi 3 here.](https://editor.swagger.io/?url=https://raw.githubusercontent.com/pagopa/pagopa-nodo-cfg-data-migration/main/openapi/openapi.json)

---

## Technology Stack

- Java 11
- Spring Boot
- Spring Web
- Hibernate
- JPA

---

## Start Project Locally 🚀

### Prerequisites

- docker

### Run docker container

from `./docker` directory

`sh ./run_docker.sh local`

ℹ️ Note: for PagoPa ACR is required the login `az acr login -n <acr-name>`

---

## Develop Locally 💻

### Prerequisites

- git
- maven
- jdk-11

### Run the project

Start the springboot application with this command:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

### Spring Profiles

- **local**: to develop locally.
- _default (no profile set)_: The application gets the properties from the environment (for Azure).

### Testing 🧪

#### Unit testing

To run the **Junit** tests:

`mvn clean verify`

---

## Contributors 👥

Made with ❤️ by PagoPa S.p.A.

### Mainteiners

See `CODEOWNERS` file
