# Overview

In this project, I built a complete Snake Game using Java and expanded it into a full application with an online backend.

The application includes a graphical interface developed with Swing, a main menu (lobby), and multiple screens that allow the user to navigate between playing the game and viewing a global ranking system.

Unlike a basic Snake game, this project integrates a backend developed with Spring Boot and a PostgreSQL database hosted online. This allows players to submit their scores and view a global leaderboard in real time.

The purpose of this project is to demonstrate my ability to build a full-stack application, combining a desktop Java client with a REST API and a cloud-hosted database.

[Software Demo Video](https://youtu.be/Oz1IrxmhQWc)

# Development Environment

For this project, I used:

Visual Studio Code
IntelliJ IDEA (for backend development)
Git
GitHub
Java JDK
Java Swing (for the graphical interface)
Spring Boot (for backend API)
PostgreSQL (database)
Railway (cloud hosting)

The main programming language used was Java.

# Features

🎮 Game Features
Snake Game built with Java Swing
Keyboard controls (arrow keys + ESC to return to menu)
Real-time gameplay with score tracking

Application Features
Main menu (Lobby) with options:
Play the game
View leaderboard
Exit the application
Navigation between screens (Lobby ↔ Game ↔ Scores)

🌐 Online Backend
REST API built with Spring Boot
Endpoint to save scores (POST /scores)
Endpoint to retrieve scores (GET /scores)
Global leaderboard shared across all players

🗄️ Database
PostgreSQL database hosted online (Railway)
Stores player name and score
Scores retrieved dynamically from the cloud

🏆 Leaderboard
Displays ranking of players
Scores sorted in descending order
Data fetched from online API

💻 Portable Application
Game exported as .exe
Includes bundled Java Runtime (JRE)
Does not require Java installation
Can be executed on any Windows machine

# Useful Websites

https://www.w3schools.com/java/
https://spring.io/projects/spring-boot
https://docs.github.com/en
https://stackoverflow.com/
