# Video Rental System API

## Project Overview

The Video Rental System API is a modern, secure RESTful service designed to manage a video rental business. This system allows administrators to manage video inventory while providing customers with access to browse available videos. Built with Spring Boot and secured with Spring Security, it offers a robust platform for video rental operations.

### Key Business Features

1. **Video Management**
   - Maintain a comprehensive catalog of videos
   - Track video availability status
   - Manage video details (title, director, genre)
   - Administrative controls for inventory management

2. **User Management**
   - Two-tier user system: Administrators and Customers
   - Secure user registration and authentication
   - Role-based access control
   - Password encryption for security

### Technical Highlights

- **Secure API Design**: Implements industry-standard security practices with Spring Security
- **Database Integration**: Utilizes MySQL for production and H2 for testing
- **Clean Architecture**: Follows REST principles and Spring best practices
- **Comprehensive Testing**: Includes unit and integration tests
- **API Documentation**: Detailed endpoint documentation with request/response examples

## Features

- **User Authentication and Authorization**
  - Secure registration and login
  - Role-based permissions (ADMIN/CUSTOMER)
  - Token-based authentication
  - Password encryption with BCrypt

- **Video Catalog Management**
  - Create, update, and remove videos (ADMIN)
  - Browse available videos (authenticated users)
  - Search and filter capabilities
  - Availability tracking

- **Security Features**
  - CSRF protection configuration
  - Input validation
  - Error handling
  - Secure password storage

- **Database Management**
  - MySQL integration for production
  - Automatic schema updates
  - Data persistence
  - Transaction management

## Technologies

- **Core Framework**: Java 17, Spring Boot 3.5.4
- **Security**: Spring Security with BCrypt encryption
- **Database**: 
  - Primary: MySQL 8.0
  - Test: H2 Database
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle 8.3
- **Testing**: 
  - JUnit 5
  - Spring Test
  - MockMvc
- **Development Tools**:
  - Lombok for boilerplate reduction
  - Spring DevTools for development
  - Spring Actuator for monitoring

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Gradle 8.3 or higher

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/ShubhamTripathi98/RentVideoJavaProject.git
   cd rentvideo
   ```

2. Configure MySQL:
   ```bash
   mysql -u root -p
   CREATE DATABASE rentvideo;
   ```

3. Update application.properties (if needed):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/rentvideo
   spring.datasource.username=root
   spring.datasource.password=root
   ```

4. Build the project:
   ```bash
   ./gradlew clean build
   ```

5. Run the application:
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`

## Authentication & Authorization (JWT)

- **JWT-based Authentication**: Secure login and API access using JSON Web Tokens (JWT).
- **Endpoints:**
  - `/api/auth/register` — Register a new user (see example below)
  - `/api/auth/login` — Login and receive a JWT token
- **How to use:**
  1. Register or login to receive a JWT token.
  2. For all protected endpoints, add the header:
     ```
     Authorization: Bearer <your-jwt-token>
     ```
- **No Basic Auth**: Basic authentication is removed. All access is via JWT.
- **Secret Key**: The JWT secret key is generated dynamically at runtime for security. For production, configure a persistent secret key.

### Example: Login and Use JWT

**Login:**
```
POST /api/auth/login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123"
}
```
**Response:**
```
{
    "token": "<jwt-token>"
}
```

**Use JWT for protected endpoints:**
```
GET /api/videos
Authorization: Bearer <jwt-token>
```

## API Endpoints

### Authentication

#### Register a new user
```
POST /api/auth/register
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"  // or "ADMIN"
}
```

#### Login and get JWT token
```
POST /api/auth/login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123"
}
```
**Response:**
```
{
    "token": "<jwt-token>"
}
```

### Videos

#### Get all videos (requires authentication)
```
GET /api/videos
Authorization: Bearer <jwt-token>
```

#### Create a new video (requires ADMIN role)
```
POST /api/videos
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "title": "Movie Title",
    "director": "Director Name",
    "genre": "Genre",
    "available": true
}
```

#### Update a video (requires ADMIN role)
```
PUT /api/videos/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
    "title": "Updated Title",
    "director": "Director Name",
    "genre": "Genre",
    "available": true
}
```

#### Delete a video (requires ADMIN role)
```
DELETE /api/videos/{id}
Authorization: Bearer <jwt-token>
```

## Security

- The API uses JWT Authentication (no Basic Auth)
- Passwords are encrypted using BCrypt
- CSRF protection is disabled for API endpoints
- Role-based access control is implemented
- JWT secret key is generated dynamically at runtime (for production, persist the key)

## Testing

The project includes comprehensive unit tests and integration tests. To run the tests:

```bash
./gradlew test
```

Tests use H2 in-memory database to avoid affecting the production database.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/rentvideo/
│   │       ├── auth/         # Authentication related classes
│   │       ├── config/       # Configuration classes
│   │       ├── user/         # User management
│   │       └── video/        # Video management
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/rentvideo/
            └── ...           # Test classes
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -am 'Add my feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
