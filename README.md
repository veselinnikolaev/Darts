# Darts - Job Finder & Recruitment System

Darts is a full-stack web application built with Java, Spring Boot, and MySQL. It is designed to simplify the process of finding job offers and managing recruitment workflows. The system enables users to browse jobs via an external API, apply for them, and manage their profiles, while administrators can manage users and moderate listings. Employers can also register and create job offers.

## 🚀 Technologies Used

* Java 17
* Spring Boot (Spring Web, Spring Data JPA, Spring Security)
* MySQL
* HTML, CSS, SCSS, JavaScript (for dynamic behavior)
* Thymeleaf (server-side rendering)
* Cloudinary (image upload & optimization)
* RapidAPI (external job offers API)
* Hibernate Validator (custom validations)

## 🔑 Key Features

### 🔐 Authentication & Authorization

* User registration & login
* Session-based Spring Security
* Role-based access (User, Employer, Admin)

### 👨‍💻 Job Search

* Integration with external API via RapidAPI
* Keyword search & job filters
* Paginated results

### 📄 Job Applications

* Apply for internal job listings
* Application tracking for users
* Admins can manage applications

### 🧑‍💼 Employer Module

* Create, update, and delete job postings
* Upload company logos (via Cloudinary)
* See applicants for each posting

### 📁 User Profiles

* Editable profiles with profile pictures
* Resume and personal info management
* Secure password handling

### 📬 Notifications

* Success and error feedback on actions
* Validation messages for form inputs

## 💻 UI Preview

The UI uses Thymeleaf templates combined with SCSS for custom styles. Sample pages include:

* Home page with featured jobs
* User dashboard
* Employer panel
* Admin control panel

## 🧪 Error Handling & Validation

* Global exception handler with custom error pages
* JSON parsing from external API responses
* Custom validators for phone numbers, emails, and more

## 🛠️ Setup Instructions

1. Clone the repo
2. Configure `application.properties`:

   * Database URL, username, password
   * Cloudinary credentials
   * External API key
3. Run the application via your IDE or `mvn spring-boot:run`
4. Open in browser: `http://localhost:8080`

## 🧑‍🏫 Project Purpose

Darts was developed as a learning and portfolio project to demonstrate proficiency in full-stack Java development and API integration. It also mimics real-world job portals with responsive design and secure data handling.

## 🙋 Author

Developed by a student software engineer with strong experience in Java, Spring, and web technologies.

---

Feel free to explore the source code and improve or contribute new features.
