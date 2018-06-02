# User authentication service
A standalone, dedicated service for user registration and authentication

### Features
* Ships on Docker
* Runs on HTTP
* Configurable to user MongoDB or MySQL
* Configurable validation strategies

### Overview
##### Persistence module
* General framework to work with MongoDB/MySQL data sources and swap between them
* Possible to use mixed relational and non-relational data

##### Web module
* SpringBoot app
* Exposing HTTP endpoints for authentication operation

### Requirements
* Java 8+
* Maven
* Docker

### Build/Run
* Build:
```bash
cd webapp-generic
mvn clean package -DskipTests
```

* Run:
```bash
cd webapp-generic/web
docker-compose up
```

* Run for development: This will launch dependant services using docker, while SpringBoot app can be started from the IDE:
```bash
cd webapp-generic/web
docker-compose -f docker-dev.yml up
```