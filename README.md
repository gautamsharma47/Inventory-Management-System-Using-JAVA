# Inventory-Management-System-Using-JAVA
A sleek, desktop-based Inventory Management System built with Java Swing and MySQL. This project was designed to move away from the "old-school" Java look by utilizing a modern, dark-themed UI with custom accent styling.
✨ Key Features

    Modern UI/UX: Powered by the FlatLaf library for a clean, dark aesthetic inspired by VS Code and modern dashboards.

    Real-time Database Integration: Full CRUD (Create, Read, Update, Delete) functionality connected to a MySQL backend.

    Persistent Storage: Data remains safe and synced even after the application is closed.

    Dynamic Data Formatting: Automatic currency (₹) and ID formatting for a professional enterprise feel.

    Error Handling: Robust JDBC connection management to handle authentication and driver issues gracefully.

🛠️ Tech Stack

    Language: Java (JDK 17+)

    GUI Library: Java Swing & AWT

    Theme: FlatLaf (Dark Mode)

    Database: MySQL (v8.0+)

    Connector: JDBC (MySQL Connector/J)

🚀 Getting Started
Prerequisites

    XAMPP or a standalone MySQL Server running on your machine.

    Java Runtime Environment (JRE) installed.

Database Setup

    Open phpMyAdmin or your MySQL terminal.

    Create a database named bca_inventory.

    Run the following SQL query to set up the products table:

SQL

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    p_name VARCHAR(100),
    price DOUBLE,
    quantity INT
);

Installation & Execution

    Clone the repository or download the ZIP.

    Ensure flatlaf-3.x.jar and mysql-connector-j-x.x.x.jar are in the project root.

    Compile the project:
    Bash

    javac -cp ".;flatlaf-3.4.1.jar;mysql-connector-j-9.6.0.jar" InventoryApp.java config/Database.java

    Run the application:
    Bash

    java -cp ".;flatlaf-3.4.1.jar;mysql-connector-j-9.6.0.jar" InventoryApp

📂 Project Structure
Plaintext

├── config/
│   └── Database.java      # Database connection logic
├── InventoryApp.java      # Main UI and application logic
├── flatlaf-3.4.1.jar      # UI Look & Feel library
└── mysql-connector-j.jar  # JDBC Driver

💡 Why I Built This

This project was developed as part of my Bachelor of Computer Applications (BCA) curriculum at Chandigarh University. The goal was to bridge the gap between backend logic and frontend presentation, creating a tool that is both functional for businesses and visually appealing for users.
