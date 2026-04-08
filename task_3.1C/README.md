# SIT305 Task 3.1C - Quiz Application

## Student Information
- Unit: SIT305 Mobile App Development
- Task: 3.1C
- Student: Wei Zhang

---

## Project Overview

This project is a simple Android **Quiz Application** developed using **Android Studio**.

The purpose of this task is to demonstrate the understanding of:
- Android project structure
- XML-based UI design
- Activity navigation
- Basic user interaction

The application consists of multiple screens that guide the user through a quiz and display the final result.

---

## App Features

The application includes the following features:

- **Main Screen**
  - Entry point of the app
  - Button to start the quiz

- **Quiz Screen**
  - Displays questions
  - Allows user interaction (answer selection)

- **Result Screen**
  - Shows the final score or result
  - Provides feedback to the user

- **Multi-Activity Navigation**
  - Uses Android Intents to switch between screens

---

## Technologies Used

- Java
- Android Studio
- XML Layouts
- Gradle Build System

---

## Project Structure

```text
task_3.1C/
├── app/
│   ├── src/main/java/com/example/quizapp/
│   │   ├── MainActivity.java
│   │   ├── QuizActivity.java
│   │   └── ResultActivity.java
│   │
│   └── src/main/res/
│       ├── layout/
│       │   ├── activity_main.xml
│       │   ├── activity_quiz.xml
│       │   └── activity_result.xml
│       │
│       └── values/
│           ├── strings.xml
│           ├── colors.xml
│           └── themes.xml
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md