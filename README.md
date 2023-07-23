
# HomeTest

This repository contain Front-end, API integration and UML diagram for this project 




## Features

This application offers an extensive range of features:

- **User Authentication**: Register and log in to access the exclusive features of our application. 


- **Log Tracking**: This application offers users the ability to trace their actions within the application through detailed logs.

- **Responsive User Interface**: The application is designed with a responsive user interface, ensuring a seamless user experience across different device screen sizes, from desktops to tablets and smartphones.

- **Form Handling**: This application ensures  form handling.


### Requirements

- Node.js v18.15.0
- npm v9.5.0

## Installation

Clone the Repository

```bash
  git clone https://github.com/aishwaryaabalu777/hometest.git
```
Navigate to the Directory Frontend
 ```bash
    cd hometest/frontend

  ```

  Installation for required dependencies

  ```bash
    npm install

  ```
  3. Build and export for production

 ```bash
    npm run build
    npm run export
 ```
## Start Application

 Run the following command

```bash
  npm start
```

## Backend 

### Overview

The backend provides a REST API with Spring Boot and connects to a MySQL database.

### Features

- JWT authentication  for login and register
- Database migration
- Unit and integration tests

### Installation

1. Build using Maven

    ```bash
    mvn clean install
    ```

1. In  Application.yaml update database credentails
2. Start the API

    ```bash
    java -jar target/authentication-0.0.1-SNAPSHOT.jar
    ```

3. The API will run on http://localhost:7777
4. Import the Postman collection in `/postman` for example requests

### Requirements

- Java 17
- Maven 3.8+
- MySQL

Let me know if you would like me to expand or modify this README further.