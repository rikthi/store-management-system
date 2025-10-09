# Quick look
https://jolly-bush-04a04080f.2.azurestaticapps.net/
Backend might need time to wake

Try with:

User ID: 1

Email: bob@gmail.com

Password: 123
 
# Store Management System
This is a guide on how to clone and run this application on your local machine
---

## 📂 Repository Structure

```
store-management-system/
├── README.md                                    # This documentation
├── backend/                                     # Spring Boot backend
│   ├── .env.example                             # Sample env file for DB credentials
│   ├── docker-compose.yml                       # Docker Compose for MySQL
│   └── src/                                     # Backend source code
└── frontend/                                    # Vite + React frontend
    └── src/                                     # Frontend source code
```

---

## Required Dependencies

Before you begin, make sure you have the following software installed on your machine:

- **Docker** 
- **Java 21 JDK** or higher
- **Maven** (optional; you can use the bundled `mvnw` wrapper)
- **Node.js 18+** & **npm** (for the frontend)

---

## Quick Start

### 1. Clone the Monorepo
```bash
git clone https://github.com/rikthi/store-management-system.git
cd store-management-system
```

### 2. Backend Setup
```bash
cd backend
```

1. Copy the env example and configure your database credentials:
   ```bash
   cp .env.example .env
   # Optional: Edit .env: set DB_NAME, DB_ROOT_PASSWORD, DB_USER, DB_PASSWORD NOTE
   ```
2. Launch MySQL with Docker Compose (reads from your `.env`):
   ```bash
   docker compose up -d
   ```
   NOTE: Make sure you have Docker running
3. Run the Spring Boot application:
   ```bash
   ./mvnw clean spring-boot:run
   ```


The backend API will be available at http://localhost:8081

### 3. Frontend Setup
Open up a new terminal and run the following scripts
```bash
cd {file-path-of-cloned-repository}/store-management-system/frontend
npm install
npm run dev
```
### NOTE: 
1. You must have node.js installed. 
2. You must also have Docker running. 
3. If npm doesn't work, run terminal as administrator and use the command:
```bash
    Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```
The frontend will launch at a local host port which will be specified in the terminal.
Copy this link and paste it onto your browser to use the application. It should look something like http://localhost:3001

---

## Running the program again

### 1. Running the Backend 
Navigate to the backend directory:
```bash
cd {file-path-of-cloned-repository}/store-management-system/backend
```
Run the following command:
```bash
./mvnw clean spring-boot:run
```

### 2. Running the frontend
Navigate to the frontend directory:
```bash
cd {file-path-of-cloned-repository}/store-management-system/frontend
```
Run the following command:
```bash
npm run dev
```

### NOTE: Make sure you have Docker running
---


## Environment Variables

### Backend (`store-management-system-backend/.env`)
```dotenv
# Database configuration
DB_NAME={your_value}
DB_ROOT_PASSWORD={your_value}
DB_USER={your_value}
DB_PASSWORD={your_value}

# Spring Boot datasource
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/${DB_NAME}?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
SPRING_DATASOURCE_USERNAME=${DB_USER}
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
```

---

## Available Scripts & Commands

| Task                          | Location                                | Command                          |
|-------------------------------|-----------------------------------------|----------------------------------|
| Start MySQL (Docker Compose)  | `store-management-system-backend/`      | `docker compose up -d`           |
| Run backend                   | `store-management-system-backend/`      | `./mvnw spring-boot:run`         |
| Install frontend deps         | `store-management-system-frontend/`     | `npm install`                    |
| Run frontend                  | `store-management-system-frontend/`     | `npm run dev`                    |

---

## Backend Tech Details

- **Framework**: Spring Boot 3.4
- **Language**: Java 21
- **Database**: MySQL 8 (via Docker)
- **ORM**: Hibernate/JPA
- **Build**: Maven Wrapper (`mvnw`)

---

## Frontend Tech Details

- **Tooling**: Vite + React + TypeScript
- **Styling**: Tailwind CSS

---







