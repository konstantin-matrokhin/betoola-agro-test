# Agro test API

### About
Test REST API listening on 8080 port.

Swagger URL: `http://localhost:8080/swagger-ui.html`

Open API file path: `src/main/resources/agro-api.yml`

### Installation

* Clone the repository
    ```shell
    git clone git@github.com:remillary/betoola-agro-test.git
    ```
* Make sure you have installed JDK 11 or higher and Maven
* Go to project directory and run the following command
    ```shell
    mvn clean install
    ```
* Maven will generate `target` directory, which contains 
  `betoola-test-0.0.1-SNAPSHOT.jar`. Go to `target`
  and make jar executable:
  ```shell
    chmod +x betoola-test-0.0.1-SNAPSHOT.jar
  ```
* Start the server:
    ```shell
    java -jar betoola-test-0.0.1-SNAPSHOT.jar
    ```

If you have some problems with project building, use compiled jar in the directory
`__if_cannot_compile_jar`.
