# 🚌 Bus Pass System

> Simple Java Swing Project with MySQL Database.
> Undergraduate College Project 🎓

---

## About
This is a simple Java Swing Desktop Application for managing bus passes connected to MySQL Database.

---

## Screenshots

### Login Page
<img width="806" height="870" alt="Login Page" src="https://github.com/user-attachments/assets/c95a30f7-2143-4411-b78e-20b8aec81542" />

### Home Page
<img width="827" height="542" alt="Home Page" src="https://github.com/user-attachments/assets/88b578a3-51f5-4f8b-8518-75f5aeead0e3" />

### Add Person
<img width="636" height="657" alt="Add Person" src="https://github.com/user-attachments/assets/91bf3805-0024-4c32-a481-9211ef281b5a" />

### Add Pass
<img width="772" height="572" alt="Add Pass" src="https://github.com/user-attachments/assets/3cec8774-7204-40da-9908-fe46be8b28fc" />

### Person Pass
<img width="916" height="901" alt="Person Pass" src="https://github.com/user-attachments/assets/a1746166-5745-471d-8108-a09f81bc6b72" />

### Reports
<img width="1919" height="1082" alt="Reports" src="https://github.com/user-attachments/assets/bbe85c58-5422-4a6f-b0a3-1288c49916a6" />

### Update Pass
<img width="474" height="486" alt="Update Pass" src="https://github.com/user-attachments/assets/f0583f13-5df8-4ede-aac5-5db8cf433edc" />

---

## Technologies Used
- Java Swing
- MySQL 8.0
- JDBC
- mysql-connector-j-9.6.0.jar
- VS Code

---

## SQL Connector
```
Driver   = com.mysql.cj.jdbc.Driver
JAR      = mysql-connector-j-9.6.0.jar
URL      = jdbc:mysql://localhost:3306/bus_pass_system
Username = root
Password = root
Port     = 3306
```

---

## Database Setup
```sql
CREATE DATABASE bus_pass_system;
USE bus_pass_system;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE person (
    p_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50),
    address VARCHAR(200),
    contact VARCHAR(15),
    city VARCHAR(100)
);

CREATE TABLE pass (
    pass_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    validity INT,
    price DOUBLE
);

CREATE TABLE person_pass (
    id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT,
    pass_id INT,
    source VARCHAR(100),
    destination VARCHAR(100),
    from_date DATE,
    to_date DATE,
    month VARCHAR(20)
);

INSERT INTO users VALUES (1, 'admin', 'admin123');
```

---

## How to Run

1. Clone karo
```
git clone https://github.com/MOHIT247ij/-BusPassSystem.git
```

2. Database setup karo
```
mysql -u root -p < database.sql
```

3. Compile karo
```
javac -cp "lib\mysql-connector-j-9.6.0.jar" *.java
```

4. Run karo
```
java -cp ".;lib\mysql-connector-j-9.6.0.jar" LoginPage
```

---

## Login
- Username: admin
- Password: admin123

---

## Features
- Login System
- Add Person
- Add Pass
- Link Person with Pass
- Monthly Reports
- Update Pass Details

---

## Made by
Mohit
Undergraduate Student
