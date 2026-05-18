# LLM-Enhanced Learning Assistant App (SIT305 Task 10.1D)

An Android application that provides personalized learning experiences, adaptive quiz assessments, and AI-powered intelligent tutoring. This project has been expanded from Task 6.1D to include user profiles, learning history, social sharing, and account upgrade features.

---

## Features

### New Features (Task 10.1D)
- **Profile Management**: View personal info, account type, and overall learning statistics (Total Questions, Correct/Incorrect Answers).
- **Quiz History**: A dedicated page to track past quiz performance, including topic names, scores, and timestamps.
- **Social Sharing**: Share your learning progress and profile summary with friends via the system's native sharing panel.
- **Account Upgrade (Mock)**: Explore premium plans (Starter, Intermediate, Advanced) with a simulated purchasing process that unlocks different account tiers.

### Core Features (Task 6.1D)
- **Login/Register**: Secure local authentication using SharedPreferences.
- **Interest Selection**: Personalized onboarding by selecting topics of interest.
- **AI Study Plan**: Generates a 7-day personalized study plan based on user interests using OpenAI GPT.
- **AI-Enhanced Quizzes**: Interactive quizzes with an "Explain Answer" feature that uses AI to provide deep insights into correct and incorrect choices.

---

## Tech Stack

- **Language:** Java
- **UI Architecture:** XML Layouts, Activities, RecyclerView
- **Data Persistence:** SharedPreferences (with GSON for object serialization)
- **AI Integration**: OpenAI GPT-4o-mini API
- **Networking**: OkHttp 4.12
- **Serialization**: GSON 2.10

---

## Project Structure

```
app/src/main/java/com/example/learningassistant/
├── LoginActivity.java
├── RegisterActivity.java
├── InterestSetupActivity.java
├── DashboardActivity.java
├── ProfileActivity.java (New)
├── HistoryActivity.java (New)
├── UpgradeActivity.java (New)
├── QuizActivity.java
├── ResultActivity.java
├── adapter/
│   ├── InterestAdapter.java
│   ├── TaskAdapter.java
│   ├── ResultAdapter.java
│   └── HistoryAdapter.java (New)
├── model/
│   ├── QuizQuestion.java
│   ├── LearningTask.java
│   └── QuizHistory.java (New)
└── util/
    ├── SharedPrefManager.java
    ├── OpenAIService.java
    └── DummyData.java
```

---

## Security Note

**Important for GitHub Submission:**
- This project **does not** contain any hardcoded API keys, secrets, or passwords.
- The OpenAI API key is managed via `BuildConfig.OPENAI_API_KEY`, which should be defined in your `local.properties` file:
  `OPENAI_API_KEY=your_actual_key_here`
- `local.properties` is included in `.gitignore` and will not be uploaded to the repository.
- Payment processing is implemented as a **Mock Purchase** for demonstration purposes and does not handle real financial data or connect to live payment gateways.

---

## How to Run

1. Clone the repository to your local machine.
2. Open the project in **Android Studio**.
3. Create or open `local.properties` in the project root and add your OpenAI API key:
   `OPENAI_API_KEY=YOUR_API_KEY`
4. Sync Gradle and build the project.
5. Run on an Android Emulator or physical device (API 24+).

---
