# Alumni LinkedIn Profile Searcher

A Spring Boot application that allows users to search for LinkedIn profiles of alumni from a specified educational institution using the PhantomBuster API.

## Features

- Search for LinkedIn profiles based on university, designation, and graduation year
- Save alumni profiles to PostgreSQL database
- Retrieve all saved alumni profiles
- RESTful API with proper error handling
- Mock data generation for testing purposes

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **API Integration**: PhantomBuster API
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito

## Prerequisites

- Java 17 or higher
- PostgreSQL database
- Maven 3.6 or higher
- PhantomBuster API key

## Setup Instructions

### 1. Database Setup

Create a PostgreSQL database:
```sql
CREATE DATABASE alumni_db;
```

### 2. Configuration

Update the `application.properties` file with your database credentials and PhantomBuster API key:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/alumni_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# PhantomBuster API Configuration
phantombuster.api.key=your_phantombuster_api_key
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Search Alumni Profiles

**POST** `/api/alumni/search`

**Request Body:**
```json
{
  "university": "NAME_OF_THE_UNIVERSITY",
  "designation": "CURRENT_DESIGNATION",
  "passedYear": "OPTIONAL_PASSED_YEAR"
}
```

**Response:**
```json
{
  "status": "success",
  "data": [
    {
      "name": "John Doe",
      "currentRole": "Software Engineer",
      "university": "University of XYZ",
      "location": "New York, NY",
      "linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
      "passedYear": "2020"
    }
  ]
}
```

### 2. Get All Saved Alumni Profiles

**GET** `/api/alumni/all`

**Response:**
```json
{
  "status": "success",
  "data": [
    {
      "name": "John Doe",
      "currentRole": "Software Engineer",
      "university": "University of XYZ",
      "location": "New York, NY",
      "linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
      "passedYear": "2020"
    }
  ]
}
```

## Testing

Run the tests using Maven:
```bash
mvn test
```

## PhantomBuster Integration

This application integrates with PhantomBuster API to scrape LinkedIn profiles. To use this feature:

1. Sign up for a PhantomBuster account
2. Create a LinkedIn search phantom
3. Get your API key
4. Update the `phantombuster.api.key` in application.properties

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/alumni/
│   │       ├── AlumniLinkedInSearcherApplication.java
│   │       ├── config/
│   │       │   └── WebClientConfig.java
│   │       ├── controller/
│   │       │   └── AlumniController.java
│   │       ├── dto/
│   │       │   ├── ApiResponse.java
│   │       │   └── SearchRequest.java
│   │       ├── model/
│   │       │   └── Alumni.java
│   │       ├── repository/
│   │       │   └── AlumniRepository.java
│   │       └── service/
│   │           └── PhantomBusterService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/alumni/
            └── AlumniControllerTest.java
```

## Important Notes

1. **Code Quality**: The assignment code is submitted as Java files with proper REST compliance
2. **Database**: PostgreSQL is used for data storage
3. **API Integration**: PhantomBuster API is integrated for LinkedIn scraping
4. **Testing**: Unit tests are included for the controller layer
5. **Error Handling**: Proper error handling with fallback to mock data
6. **Validation**: Input validation using Jakarta Bean Validation

## License

This project is for educational purposes only.
