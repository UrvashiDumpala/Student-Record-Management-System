# 🎓 Student Record Management System

## Project Title / Headline

**Student Record Management System – Java Full-Stack Web Application**

A Java-based web application developed to simplify student registration and record management. The system allows students to register their details online and enables administrators to view, search, and manage student records through an admin dashboard. It uses Java for backend processing, HTML, CSS, and JavaScript for the frontend, and MySQL for database management.

## Short Description / Purpose

The Student Record Management System is a web-based application designed to replace traditional manual student record management with a centralized digital system.

The application provides an online registration interface for students and an admin dashboard to access and view registered student information. It includes input validation, duplicate roll number detection, and student search functionality.

The main purpose of this project is to reduce paperwork, minimize data-entry errors, and make student information easier to access and manage.

## Tech Stack

The application was developed using the following tools and technologies:

- ☕ **Core Java** – Backend development and application logic.
- 🌐 **HTML** – Structure of the web pages.
- 🎨 **CSS** – Styling and layout of the user interface.
- ⚡ **JavaScript** – Frontend interactions and form validation.
- 🗄️ **MySQL** – Storing and managing student records.
- 🔗 **JDBC** – Connecting the Java application with the MySQL database.
- ⚙️ **Java HttpServer** – Handling HTTP requests and backend communication.

## Database Details

**Database Name:** `student_db`  
**Table Name:** `students`

The database stores student information, including:

- Student ID
- Roll Number
- Student Name
- Email
- Phone Number
- Branch
- Year
- Section
- Registration Date

The roll number is maintained as a unique field to prevent duplicate student registrations.

## Features / Highlights

### Problem Statement

Managing student information manually using paperwork or separate spreadsheets can lead to duplicate records, data-entry errors, difficulty in searching, and increased administrative workload.

This project provides a centralized web-based solution that simplifies student registration and allows administrators to access student records efficiently.

### Goal of the Project

- Develop an online student registration system.
- Store student information in a centralized MySQL database.
- Provide secure admin login functionality.
- Allow administrators to view all registered student records.
- Implement search functionality using student name or roll number.
- Reduce manual work and improve record management.

### Key Application Features

- **Student Registration:** Students can enter and submit their personal and academic details through an online form.
- **Input Validation:** Validates mandatory fields and checks email format.
- **Duplicate Detection:** Prevents registration using an already-existing roll number.
- **Admin Login:** Provides an authentication interface for administrators.
- **Admin Dashboard:** Displays registered student records in a structured table.
- **Student Search:** Allows administrators to search records using student names or roll numbers.
- **Student Count:** Displays the total number of registered students.
- **Refresh Functionality:** Updates the displayed student records.

## Project Modules

The application consists of the following modules:

1. **Student Registration Module** – Collects student details through the registration form.
2. **Admin Login Module** – Provides administrator authentication.
3. **Student Management Module** – Displays and searches registered student records.
4. **Backend Processing Module** – Handles HTTP requests and processes application logic using Java.
5. **Database Management Module** – Uses JDBC and MySQL to store and retrieve student information.

## System Workflow

The application follows a simple frontend-backend-database architecture.

1. Students enter their details through the registration page.
2. The frontend sends the registration request to the Java backend.
3. The backend validates the submitted information.
4. JDBC connects the backend to the MySQL database.
5. Student records are stored in the `students` table.
6. The administrator logs in to access the dashboard.
7. The dashboard retrieves and displays student records.
8. Administrators can search for specific student information.

## Results and Outcomes

The project demonstrates the implementation of a Java-based student record management application with frontend, backend, and database integration.

The major functionalities, including student registration, duplicate roll number checking, admin login, displaying student records, and searching, were tested.

The application provides a simple and organized way to maintain student information while reducing the difficulties associated with manual record management.

## Future Enhancements

- Add options to edit and delete student records.
- Implement encrypted password storage and OTP-based authentication.
- Introduce multiple user roles and access permissions.
- Support bulk student data upload through CSV or Excel files.
- Generate student reports.
- Deploy the application on a cloud platform.
- Develop a mobile-friendly application.
- Implement automated database backup functionality.

## Repository Contents

The repository can contain the following project resources:

- **Source Code** – Java backend and frontend files.
- **Database Files** – MySQL database and table structure.
- **Project Report** – Documentation explaining the project.
- **Screenshots** – Images of the registration page and admin dashboard.
- **README.md** – Project overview, technologies, features, modules, and documentation.

## Project Information

- **Project Name:** Student Record Management System
- **Project Type:** Full-Stack Web Application
- **Domain:** Java Development
- **Backend:** Core Java
- **Frontend:** HTML, CSS, JavaScript
- **Database:** MySQL
- **Connectivity:** JDBC
