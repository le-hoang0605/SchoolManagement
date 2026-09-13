# SCHOOL MANAGEMENT SYSTEM

Spring Boot Backend Project

## 1. Project Description

The School Management System (OMK School Management System) is a backend RESTful API developed using Java and Spring Boot. The system is designed to manage educational administration for high schools, including users, classes, sections, courses, grades, attendance, lesson preparations, and class schedules.

The project requires developing a backend system mapped to an existing MySQL database (`omk_db` with 15 tables) using a Database-First approach. The system handles database interaction, CRUD operations, authentication, authorization, schedule conflict validation, and automated testing.

The system provides RESTful APIs across four user roles: ADMIN, COORDINATOR, TEACHER, and STUDENT.

The backend is implemented using Spring Boot, Spring Web, Spring Data JPA, Hibernate, Spring Security, and JWT. The system follows a layered architecture consisting of controllers, services, repositories, entities, DTOs, security components, and exception handlers.

## 2. Main Objectives

The main objectives of this project are:

* Build a complete backend application using Spring Boot.


* Design and implement RESTful APIs based on an existing MySQL database schema (`omk_db`).


* Use Spring Data JPA and Hibernate for Database-First entity mapping.


* Implement CRUD operations for main administrative and academic entities.


* Model entity relationships based on existing foreign keys in the database.


* Implement authentication using JWT and BCrypt password hashing.


* Implement multi-role authorization across four user roles.


* Validate user input and complex business logic (e.g., grade range verification and timetable conflict detection).


* Write unit tests using JUnit 5 and Mockito.


* Apply dependency injection and clean backend architecture.



## 3. Main Entities

The system consists of the following main entities:

* **User** – stores user credentials, roles (ADMIN, COORDINATOR, TEACHER, STUDENT), and personal details.


* **Class & Section** – represents grade levels and specific classroom sections.


* **Course** – represents subject courses taught in the school.


* **TeacherCourse** – represents teacher course assignments per section and academic year.


* **StudentSection** – links enrolled students to their assigned sections.


* **Grade** – stores assessment types, student scores, and recording metadata.


* **Attendance** – tracks daily attendance status for students.


* **LessonPreparation** – stores teacher lesson plans and approval statuses.


* **Program** – represents section timetables and teacher schedules.



The relationships between these entities are:

* A **Class** has many **Sections**.


* A **Section** belongs to one **Class** and is managed by one **Coordinator** (User).


* A **TeacherCourse** links one **Teacher** (User), one **Course**, and one **Section**.


* A **StudentSection** links one **Student** (User) to a **Section**.


* A **Grade** belongs to one **Student**, one **Course**, and one **Section**.


* An **Attendance** record belongs to one **Student**, one **Course**, and one **Section**.


* A **Program** (schedule slot) belongs to one **Section**, one **Teacher**, and one **Course**.


* A **LessonPreparation** belongs to one **Teacher**, one **Course**, and one **Section**.



## 4. Core Functionalities

### 4.1 Authentication and Authorization

The system provides JWT-based authentication through a public login endpoint (`/api/auth/login`).

Users are divided into four roles:

* **ADMIN**: Full access to manage all system entities and configurations.


* **COORDINATOR**: Manages sections, assigns teacher courses, reviews lesson preparations, and views class reports.


* **TEACHER**: Prepares lesson plans, records student grades and class attendance, and views teaching timetables.


* **STUDENT**: Read-only access to view personal grades, attendance records, and class timetables.



### 4.2 Academic and Course Management

The system provides APIs to:

* Retrieve available classes and sections.


* Retrieve the list of students belonging to a specific section.


* Retrieve available subject courses.


* Assign teachers to teach specific courses in designated sections.



### 4.3 Grade Management

Teachers and administrators can record and update student grades.

When a grade is entered, the system automatically:

1. Identifies the current user from the JWT token.


2. Verifies whether the teacher is assigned to teach that course in that section.


3. Validates that the score satisfies 0.00 ≤ score ≤ max_score.


4. Sets the `recorded_by` field using the logged-in user's ID.


5. Saves the grade record.



Students can view only their own grades, while teachers and coordinators can view grade reports by section.

### 4.4 Attendance Management

The system enables batch attendance entry for sections.

Authorized users can:

* Record attendance status (present, absent, late, excused) for a list of students.


* View class attendance logs by section and date.


* View attendance summary statistics for individual students.



### 4.5 Lesson Plan and Schedule Management

Teachers submit lesson preparations, which are reviewed by Coordinators or Admins. Updating status updates `reviewed_by` and `reviewed_at` fields.

When creating new timetable slots (`Program`), the system automatically:

1. Checks for teacher schedule overlaps (Teacher Conflict Check).


2. Checks for room reservation overlaps (Room Conflict Check).


3. Throws a 409 Conflict exception if an overlap is detected.



## 5. Security

The system applies Spring Security to protect restricted endpoints.

The security requirements include:

* JWT-based authentication via custom `JwtAuthenticationFilter`.


* Password hashing using BCrypt (compatible with existing `$2y$` hash patterns).


* Role-based authorization enforced at endpoint levels.


* Input validation.


* Restriction preventing students from accessing other students' records.



Public access is restricted to authentication endpoints like `/api/auth/login`.

## 6. Technology Stack

The project uses the following technologies:

* Java 


* Spring Boot 


* Spring Web


* Spring Data JPA


* Hibernate


* Spring Security


* JWT (JSON Web Token)


* Jakarta Bean Validation


* MySQL (`omk_db`)


* Maven


* JUnit 


* Mockito




## 7. Project Architecture

The project follows a layered backend architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
Database (MySQL)

```

Security components are integrated into the request processing flow:

```text
Client
   ↓
JWT Authentication Filter
   ↓
Spring Security
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database

```

The project separates responsibilities between controllers, services, repositories, entities, DTOs, exception handling, and security components.

## 8. Validation and Error Handling

The system validates incoming requests using Jakarta Bean Validation.

Examples of validation rules include:

* Email must be valid and required.


* User names are required.


* Grade scores must be non-negative and within upper limits.


* Schedule times must avoid room and teacher overlaps.


* Referenced entities must exist.




## 9. Testing

The project includes unit tests for important business logic.

At minimum, the following cases should be tested:

* Successful grade entry by an authorized teacher.


* Unauthorized grade entry attempt by an unassigned teacher.


* Schedule collision detection throwing `ConflictException`.


* Successful lesson preparation approval updating review metadata.



JUnit 5 is used for testing, while Mockito is used to mock dependencies such as repositories.
