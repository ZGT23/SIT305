# LLM-Enhanced Learning Assistant App

An Android application that provides personalized learning experiences, adaptive quiz assessments, and AI-powered intelligent tutoring — built for SIT305 Task 6.1D.

---

## Features

### Screens
| Screen | Description |
|--------|-------------|
| **Login** | Sign in with username and password |
| **Register** | Create a new account (username, email, password, phone) |
| **Interest Setup** | Select up to 10 topics of interest |
| **Dashboard** | View learning tasks and generate an AI study plan |
| **Quiz** | Answer multiple-choice questions for a selected topic |
| **Results** | View quiz results with AI-powered answer explanations |

### LLM-Powered Features
1. **7-Day Study Plan Generator** (Dashboard) — Sends your selected interests to OpenAI and generates a personalized weekly study plan. Displays the prompt and AI response in the UI with loading and error states.
2. **Answer Explanation** (Results screen) — For each quiz question, tap "Explain Answer" to have AI explain why the correct answer is right and why your answer was correct or incorrect. Displays the prompt and AI response with loading and error states.

---

## Tech Stack

- **Language:** Java
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36
- **LLM API:** OpenAI GPT-4o-mini
- **HTTP Client:** OkHttp 4.12
- **JSON Parsing:** Gson 2.10
- **UI:** RecyclerView, CardView, Material Design 3

---

## Project Structure

```
app/src/main/java/com/example/learningassistant/
├── LoginActivity.java
├── RegisterActivity.java
├── InterestSetupActivity.java
├── DashboardActivity.java
├── QuizActivity.java
├── ResultActivity.java
├── adapter/
│   ├── InterestAdapter.java
│   ├── TaskAdapter.java
│   └── ResultAdapter.java
├── model/
│   ├── QuizQuestion.java
│   └── LearningTask.java
└── util/
    ├── SharedPrefManager.java
    ├── OpenAIService.java
    └── DummyData.java
```

---

## App Flow

```
Login ──────────────────────────────► Dashboard
  │                                       │
  └── Need an Account? ──► Register       ├── Click Task ──► Quiz ──► Results
                               │          │                              │
                               ▼          └── Generate Study Plan        └── Continue
                        Interest Setup         (AI Response)
                               │
                               ▼
                           Dashboard
```

---

## Dummy Data

The app includes pre-built quiz content for four topics:

- **Algorithms** — Binary Search, Sorting, DFS
- **Data Structures** — Queue/Stack, Array access, Tree traversal
- **Web Development** — HTML, CSS, JavaScript
- **AI/ML Basics** — Supervised learning, Overfitting, ML concepts

Each topic has 3 multiple-choice questions.

---

## Setup & Running

1. Clone the repository
2. Open the project in **Android Studio**
3. Let Gradle sync and download dependencies
4. Run on an emulator or physical device (Android 7.0+)

> **Note:** An active internet connection is required for AI features (OpenAI API calls).

---

