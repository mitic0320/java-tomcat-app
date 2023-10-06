# Embed Tomcat Web application

## Run

1. Build the project using maven:

```bash
mvn clean compile assembly:single
```

2. Run the jar file

```bash
java -jar target/tomcat-app-jar-with-dependencies.jar
```

3. Visit the website: http://localhost:8083/api/hello
