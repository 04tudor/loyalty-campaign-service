# Loyalty Campaign Service

The Loyalty Campaign Service manages loyalty campaigns.

## Table of Contents

- [Description](#description)
- [Features](#features)
- [User Stories](#user-stories)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Clone Repository](#clone-repository)
  - [Set Environment Variables](#set-environment-variables)
  - [Build and Run](#build-and-run)
- [Usage](#usage)
  - [Example API Requests](#example-api-requests)
- [Tests](#tests)
  - [Integration Tests](#integration-tests)
  - [Component Tests](#component-tests)
- [Running in Docker, Podman, or Kubernetes](#running-in-docker-podman-or-kubernetes)
- [Contributing](#contributing)
- [Contact](#contact)

## Description

The Loyalty Campaign Service enables businesses to effectively manage and execute loyalty campaigns, enhancing customer retention, boosting sales, and deepening brand engagement through personalized rewards and strategic initiatives.
## Features

- Create and manage loyalty campaigns in draft and active states
- Search campaigns by metadata/date/id
- View detailed campaign information
- Integration with a rules engine to define and evaluate campaign conditions
- Activate a campaign and delete draft Campaign

## User Stories

As a Loyalty Manager, I want to:
1. Create loyalty campaigns in a draft state.
2. Search created campaigns by their metadata.
3. View all details about a particular campaign.
4. Change the state of a loyalty campaign:
    - Move from draft to active state (activate)
    - Close an active campaign (set end date)
    - Delete a campaign in draft state
5. Find active Campaigns by Date

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 21 or higher
- Maven 3.9.5 or higher
- PostgreSQL 15.4

## Installation

Follow these steps to install and run the Loyalty Campaign Service:

### Clone Repository

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/loyalty-campaign-service.git
    cd loyalty-campaign-service
    ```

### Set Environment Variables

2. Set environment variables for database configuration:

    ```bash
    export SPRING_DATASOURCE_URL=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DATABASE}
    export SPRING_DATASOURCE_USERNAME=${POSTGRESQL_APPLICATION_USERNAME}
    export SPRING_DATASOURCE_PASSWORD=${POSTGRESQL_APPLICATION_PASSWORD}
    ```

### Build and Run

3. Build and run the application:

    ```bash
    mvn clean package -Dsha1={sha1}
    mvn spring-boot:run
    ```
 ##### Health

* liveness `curl localhost:8080/livez`
* readiness `curl localhost:8080/readyz`
  
### Usage

Once the service is running, you can access it at: `https://loyalty-campaign-service.bluewave-4dfdf337.westus2.azurecontainerapps.io` (deployed on an Azure instance).

## Tests

To run tests for the Loyalty Campaign Service:

### Integration Tests

Run integration tests:

```bash
./mvnw clean -Dgroups=integration test
```
### Component Tests
Run Component Tests:

```bash
./mvnw clean -Dgroups=component test
```

### Example API Requests

Here are some example API requests you can use with the Loyalty Campaign Service:

- **Create a new campaign:**

    ```bash
    curl -X POST "localhost:8080/campaigns" -H "Content-Type: application/json" -d {campaign(json)}
    ```

- **Get all campaigns:**

    ```bash
    curl -X GET "localhost:8080/campaigns/listAll"
    ```

- **Search campaigns by metadata:**

    ```bash
    curl -X GET "localhost:8080/campaigns/{key}/{value}"
    ```

- **Get details of a particular campaign:**

    ```bash
    curl -X GET "localhost:8080/campaigns/{campaignId}"
    ```

- **Activate a campaign:**

    ```bash
    curl -X POST "localhost:8080/campaigns/activate/{campaignId}"
    ```

- **Delete a draft campaign:**

    ```bash
    curl -X DELETE "localhost:8080/campaigns/{campaignId}"
    ```

    - **Find active campaigns by date**

    ```bash
    curl -X GET "localhost:8080/campaigns/date/{date}"
    ```

## Running in Docker, Podman, or Kubernetes

Building and running the service as a Docker image is the preferred way:

1. Build the Docker image:

    ```bash
    docker build -t loyalty-campaign-service .
    ```

2. Run the Docker container:

    ```bash
    docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DATABASE} -e SPRING_DATASOURCE_USERNAME=${POSTGRESQL_APPLICATION_USERNAME} -e SPRING_DATASOURCE_PASSWORD=${POSTGRESQL_APPLICATION_PASSWORD} loyalty-campaign-service
    ```

3. Deploying to Kubernetes:

    Create a Kubernetes deployment file (e.g., `deployment.yaml`) with the necessary configuration, and apply it:

    ```bash
    kubectl apply -f deployment.yaml
    ```

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any features, bug fixes, or enhancements.

1. Fork the repository
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a pull request

Please make sure to update tests as appropriate.


## Contact

If you have any questions or suggestions, feel free to contact me at [tudorcoretchi119@gmail.com](mailto:tudorcoretchi119@gmail.com).
