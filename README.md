# Smart Expense Tracker

A multi-user web application built using **Java, Spring Boot, Hibernate, and MySQL** to help users manage their daily expenses and understand their spending habits.

## Overview

Smart Expense Tracker allows users to register, log in securely, and manage their personal expenses through a simple web interface. Users can record expenses, organize them by category, search their records, and view spending summaries and visual reports.

This project is an improved version of my original JDBC-based Expense Tracker, redesigned using Spring Boot and Spring Data JPA with Hibernate.

## Features

- **User Registration:** Create a new account.
- **Secure Authentication:** Log in and log out using Spring Security.
- **Password Security:** Store passwords using BCrypt hashing.
- **Expense Management:** Add, view, edit, and delete expenses.
- **Search Expenses:** Search expense records by title.
- **User-Specific Data:** Associate expenses with their respective users.
- **Dashboard:** View total spending, expense counts, and recent expenses.
- **Reports and Analytics:** View category-wise spending summaries and charts.
- **Database Integration:** Store users and expenses in MySQL.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Application framework |
| Spring MVC | Handle web requests |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database operations |
| Hibernate | Object-relational mapping (ORM) |
| MySQL | Relational database |
| Thymeleaf | Dynamic HTML pages |
| Bootstrap | Responsive user interface |
| Chart.js | Visualize expense reports |
| Maven | Dependency management and build |

## Application Architecture

The application follows a layered architecture:

```text
User Interface
  (Thymeleaf + Bootstrap)
          |
          v
   Spring Security
          |
          v
      Controller
          |
          v
       Service
          |
          v
      Repository
          |
          v
  Spring Data JPA
          |
          v
      Hibernate
          |
          v
        MySQL
```

## Project Structure

```text
SmartExpenseTrackerPro/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/expense/SmartExpenseTracker/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── SmartExpenseTrackerApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Prerequisites

Before running the project, install:

- Java Development Kit (JDK) 21
- MySQL Server 8.0 or compatible version
- Git (optional if downloading the ZIP file)

An IDE such as Eclipse is recommended.

## Installation and Setup

### 1. Clone the repository

```bash
git clone https://github.com/Syamala10/SmartExpenseTracker-SpringBoot.git
cd SmartExpenseTracker-SpringBoot
```

### 2. Create the MySQL database

Open MySQL Workbench or your MySQL command line and execute:

```sql
CREATE DATABASE smart_expense_tracker_v2;
```

Hibernate is configured to update the database schema when the application starts.

### 3. Configure database credentials

Set your MySQL username and password through environment variables instead of storing real credentials in the source code.

For Windows Command Prompt:

```cmd
set DB_USERNAME=root
set DB_PASSWORD=your_mysql_password
```

Replace the example password with your own local MySQL password. Run the application from the same Command Prompt session.

Ensure the database URL and environment variable names match the configuration in `src/main/resources/application.properties`.

### 4. Run the application

Using the Maven Wrapper on Windows:

```cmd
mvnw.cmd spring-boot:run
```

Alternatively, import the project into Eclipse as an existing Maven project and run the main application class.

### 5. Open the application

If the configured port is `8085`, open:

http://localhost:8085/login

The application requires a running local MySQL server with valid credentials.

## Security Notes

- Passwords are hashed using BCrypt.
- Spring Security protects authenticated pages.
- Expenses are associated with users.
- Database credentials should be configured outside source control.
- Never commit real passwords, API keys, or other secrets to GitHub.

## Future Enhancements

- Monthly and yearly spending reports
- Budget planning and spending limits
- Export reports to PDF and Excel
- Advanced filtering by date and category
- Improved automated testing
- Cloud deployment

## Learning Outcomes

Through this project, I explored:

- Building web applications with Spring Boot
- Implementing authentication using Spring Security
- Using Spring Data JPA and Hibernate for persistence
- Designing a layered application architecture
- Connecting Java applications to MySQL
- Creating dynamic pages using Thymeleaf
- Generating category-wise spending reports

## Author

**Syamalamani Pamarthi**

GitHub: [Syamala10](https://github.com/Syamala10)


If you find this project useful, feel free to explore the repository and share feedback.
