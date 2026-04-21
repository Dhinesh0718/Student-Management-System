# 🎓 Student Management System

A desktop-based application developed using **Java Swing** and **MySQL** to manage student records efficiently.

---

## 🚀 Features

* ➕ Add new student records
* 📋 View all students
* 🔍 Search student by ID
* ✏️ Update student details
* ❌ Delete student records
* 📊 Dashboard showing total student count

---

## 🛠️ Tech Stack

* **Java** (Core Java + Swing for UI)
* **MySQL** (Database)
* **JDBC** (Database Connectivity)

---

## 📂 Project Structure

* `StudentGUI.java` – Main dashboard UI
* `LoginPage.java` – Login interface
* `JDBCStudentSystem.java` – Database connection & logic

---

## ⚙️ How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/Student-Management-System.git
   ```

2. Open the project in your IDE (VS Code / IntelliJ / Eclipse)

3. Setup MySQL database:

   ```sql
   CREATE DATABASE studentdb;

   USE studentdb;

   CREATE TABLE students (
       id INT PRIMARY KEY,
       name VARCHAR(50),
       age INT
   );
   ```

4. Update database credentials in the code:

   ```java
   DriverManager.getConnection(
       "jdbc:mysql://localhost:3306/studentdb",
       "root",
       "your-password"
   );
   ```

5. Run `StudentGUI.java`

---

## 🎯 Key Highlights

* Clean and user-friendly GUI
* Full CRUD operations
* Real-time database integration
* Beginner-friendly project with practical implementation

---

## 📌 Future Improvements

* Add table view instead of text area
* Export data to Excel
* Add user authentication system
* Improve UI with modern design

---

## 👨‍💻 Author

**Dhinesh**
Final Year Student

---
