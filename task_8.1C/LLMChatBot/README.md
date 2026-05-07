# SIT305 Credit Task 8.1 – LLM ChatBot Android Project

## Project Overview
This project is a simple Android ChatBot application developed in Java. It allows users to log in with a username and chat with an AI model (simulated or real). The app features message persistence using Room Database, a responsive UI with XML layouts, and an LLM service layer using Retrofit.

## Key Features
- **User Login:** Simple username entry to personalize the experience.
- **Chat Interface:** Mobile-style chat bubbles with timestamps and distinct colors for user and bot.
- **History Persistence:** All messages are saved in a local Room database and reloaded on app restart.
- **LLM Integration:** Supports both a Mock service (for offline/demo) and a Gemini API implementation via Retrofit.
- **Modern Architecture:** Uses Repository pattern and LiveData for reactive UI updates.

## Project Structure
- `MainActivity.java`: Host for Login and Chat fragments.
- `LoginFragment.java`: Handles user entry.
- `ChatFragment.java`: Main chat logic, message list, and input.
- `ChatAdapter.java`: Manages RecyclerView display for messages.
- `ChatMessage.java`: Room database entity.
- `AppDatabase.java` & `ChatDao.java`: Room database configuration.
- `LlmService.java`: Interface for LLM communication.
- `ApiLlmService.java`: Retrofit implementation for Gemini API.
- `MockLlmService.java`: Offline fallback for demonstration.

## Setup Instructions
1. Open the project in Android Studio.
2. Sync Gradle to download dependencies (Room, Retrofit, Gson).
3. (Optional) To use a real API:
   - Go to `ApiLlmService.java`.
   - Replace `YOUR_API_KEY_HERE` with a valid Gemini API key.
   - In `ChatFragment.java`, change `llmService = new MockLlmService();` to `llmService = new ApiLlmService();`.
4. Run the app on an emulator or physical device (API 24+).

## Technologies Used
- Java
- XML Layouts
- Room Persistence Library
- RecyclerView
- Retrofit & Gson
- Material Design Components
