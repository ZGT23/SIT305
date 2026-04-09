# Personal Event Planner App (SIT305 Task 4.1P)

## 📱 Overview
This Android application is a Personal Event Planner that allows users to manage their upcoming events such as work tasks, social activities, and travel plans.

The app provides a simple and structured interface to create, view, update, and delete events. All data is stored locally on the device using the Room database, ensuring persistence even after the app is closed or restarted.

---

## 🚀 Features

### 1. Create Event
Users can add a new event with:
- Title
- Category (Work, Social, Travel)
- Location
- Date and Time

Input validation ensures:
- Title is not empty
- Date is selected
- Date is not in the past

---

### 2. View Events (Read)
- Displays all upcoming events in a list
- Events are automatically sorted by date and time
- Uses RecyclerView for efficient display

---

### 3. Update Event
- Users can edit an existing event
- A dialog allows updating all fields
- Changes are saved instantly to the database

---

### 4. Delete Event
- Users can delete events directly from the list
- Snackbar feedback confirms successful deletion

---

### 5. Data Persistence (Room Database)
- All data is stored locally using Room
- Events remain saved even after closing or restarting the app

---

### 6. Navigation
- Implemented using Jetpack Navigation Component
- Bottom Navigation Bar for switching between:
  - Event List
  - Add Event

---

### 7. Validation and User Feedback
- Input validation prevents incorrect data entry
- Toast messages for errors
- Snackbar messages for successful operations

---

## 🛠️ Technologies Used

- Java
- Android Studio
- Room Persistence Library
- RecyclerView
- Jetpack Navigation Component
- Material Design Components

---

## 📂 Project Structure
com.example.eventplanner
│
├── MainActivity.java
├── Event.java
├── EventDao.java
├── EventDatabase.java
├── EventAdapter.java
├── AddEventFragment.java
├── EventListFragment.java

---

## ▶️ How to Run

1. Open the project in Android Studio
2. Sync Gradle
3. Run the app on an emulator or physical device
4. Use the bottom navigation to explore features

---

## 🎥 Demonstration

A demonstration video is provided showing:
- App functionality
- Code explanation
- UI navigation

---

