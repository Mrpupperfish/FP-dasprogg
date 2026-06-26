# Simple Tic-Tac-Toe Game with Java Swing, Login, and Statistics

## Student Information

Name : Desanda Verdely Yulisar
Student ID: 5026251164
Class : E

---

## Project Description

This project is a simple Tic-Tac-Toe game built using Java Swing GUI.
The player competes against the computer. The application includes login
with database validation, game statistics recording, and a Top 5 scorer
leaderboard — all using a single database table.

---

## Features

- Login using database (username + password validation)
- Play Tic-Tac-Toe using Java Swing GUI (3x3 button board)
- Record wins, losses, draws, and score after each game
- Display personal statistics
- Display Top 5 scorers using JTable

---

## Score Calculation

| Result | Points |
| ------ | ------ |
| Win    | +10    |
| Draw   | +3     |
| Lose   | +0     |

---

## Database

- Database used : MySQL
- Database name : game_project
- Table name : players

### Table Schema

| Column   | Type         | Description              |
| -------- | ------------ | ------------------------ |
| id       | INT (PK, AI) | Auto-increment player ID |
| username | VARCHAR(50)  | Unique username          |
| password | VARCHAR(100) | Player password          |
| wins     | INT          | Number of wins           |
| losses   | INT          | Number of losses         |
| draws    | INT          | Number of draws          |
| score    | INT          | Total accumulated score  |

---

## How to Run

### Step 1 — Create Database

1. Open MySQL Workbench or HeidiSQL
2. Run the file `database/schema.sql`
3. This will create `game_project` database and `players` table with sample data

### Step 2 — Configure Database Connection

Open `src/DatabaseManager.java` and edit these lines:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/game_project";
private static final String USER     = "root";
private static final String PASSWORD = ""; // fill with your MySQL password
```

### Step 3 — Add JDBC Driver

- Download `mysql-connector-j-x.x.x.jar` from https://dev.mysql.com/downloads/connector/j/
- In IntelliJ: `File → Project Structure → Libraries → + → select the .jar file`
- In Eclipse: Right-click project → `Build Path → Add External JARs`

### Step 4 — Run the Program

- Run `Main.java`
- Login using: username `student1`, password `12345`

---

## Class Explanation

| Class           | Responsibility                                                     |
| --------------- | ------------------------------------------------------------------ |
| Main            | Entry point. Starts the program and opens LoginFrame.              |
| DatabaseManager | Handles JDBC connection to MySQL database.                         |
| Player          | Model class. Stores id, username, wins, losses, draws, score.      |
| PlayerService   | Handles login, updateStatistics, getTopFiveScorers, getPlayerById. |
| GameLogic       | Handles makeMove, checkWinner, isDraw, computerMove.               |
| LoginFrame      | Swing window for username and password input.                      |
| MainMenuFrame   | Swing window for main menu navigation.                             |
| GameFrame       | Swing window for playing the game (3x3 button board).              |
| StatisticsFrame | Swing window for showing personal statistics.                      |
| TopScorersFrame | Swing window for showing Top 5 scorers using JTable.               |

---

## Screenshots

<img width="1066" height="583" alt="Screenshot 2026-06-25 095331" src="https://github.com/user-attachments/assets/9af70e06-20cb-4f30-bf5e-e85a3af39fd1" />
<img width="928" height="493" alt="image" src="https://github.com/user-attachments/assets/5c4faadb-c702-4e2b-8bd8-c02ee8ee1f21" />
<img width="866" height="478" alt="image" src="https://github.com/user-attachments/assets/f9633f9f-3cf6-401b-84c0-c1db687e2756" />
<img width="434" height="305" alt="image" src="https://github.com/user-attachments/assets/cbde00a9-4455-4b58-b943-3128131e03c9" />
<img width="682" height="347" alt="image" src="https://github.com/user-attachments/assets/b01a8114-3392-4044-870a-3d9e712fc047" />



---

## GitHub Link

https://github.com/Mrpupperfish/FP-dasprogg/tree/main

## YouTube Link

https://youtu.be/C9ESj8pJ3ro

