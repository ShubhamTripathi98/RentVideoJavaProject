# RentVideo

A RESTful API service for an online video rental system built with Spring Boot.

## Features
- User authentication and registration
- Video management (CRUD)
- Role-based access control
- Exception handling
- Data seeding for development

## Project Structure
```
rentvideo/
├─ src/main/java/com/example/rentvideo/
│  ├─ RentVideoApplication.java
│  ├─ config/
│  ├─ auth/
│  ├─ user/
│  ├─ video/
│  ├─ common/
│  └─ util/
├─ src/main/resources/
│  └─ application.properties
├─ src/test/java/com/example/rentvideo/
│  └─ RentVideoApplicationTests.java
│
├─ build.gradle
├─ settings.gradle
└─ README.md
```

## Getting Started

### Prerequisites
- Java 17+
- Gradle
- MySQL (for main profile)

### Running the Application
```
./gradlew bootRun
```

### Running Tests
```
./gradlew test
```

### Configuration
- Main application properties: `src/main/resources/application.properties`
- Test properties: `src/test/resources/application.properties`

## License
MIT

