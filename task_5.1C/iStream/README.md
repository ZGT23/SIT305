# iStream - Personal Video Playlist App

iStream is a simple and efficient Android application designed for managing personal YouTube video playlists. It allows users to create accounts, search for YouTube videos, play them instantly within the app, and save their favorite videos to a personalized playlist.

## Features

### 1. Authentication System
*   **Sign Up:** Users can register by providing their Full Name, a unique Username, and a Password. The app validates that passwords match and ensures usernames are not duplicated.
*   **Login:** Secure login using local credentials.
*   **Session Management:** Once logged in, the session is persisted using `SharedPreferences`. The app automatically navigates to the Home screen on subsequent launches if the user is still logged in.
*   **Logout:** Users can securely log out, which clears the session and returns them to the Login screen.

### 2. Home Screen (Video Player)
*   **YouTube Search/Play:** Enter any valid YouTube URL to load the video.
*   **Embedded Playback:** Uses a custom-configured `WebView` with the YouTube iFrame Embed API to provide a seamless, in-app viewing experience.
*   **Safety & Validation:** Validates YouTube URLs and extracts video IDs automatically.
*   **Add to Playlist:** Save the currently loaded video to the user's private playlist with a single click.

### 3. Personal Playlist
*   **User-Specific Content:** Each user sees only their own saved videos.
*   **Instant Playback:** Click any item in the playlist to return to the Home screen and automatically start playback of that video.
*   **Persistence:** All playlist data is stored locally using the Room Database.

## Technical Stack

*   **Language:** Java
*   **UI Framework:** XML (Android View System)
*   **Architecture:** Single Activity with Fragments for efficient navigation.
*   **Local Database:** Room Persistence Library (SQL-based).
*   **Video Integration:** WebView + YouTube iFrame Embed API.
*   **Session Helper:** SharedPreferences.

## Database Schema

*   **User Table:** Stores `id`, `fullName`, `username`, and `password`.
*   **Playlist Table:** Stores `id`, `userId` (Foreign Key), and `videoUrl`.

## Setup & Installation

1.  Open the project in **Android Studio**.
2.  Ensure you have the latest **Android SDK** installed (Targeting API 34/36).
3.  Sync the project with Gradle files to download Room dependencies.
4.  Run the app on a physical device or a stable emulator (Recommended: Pixel 7 with API 33/34 for best WebView performance).
5.  **Important:** The app requires an active internet connection to load and play YouTube videos.

## Project Structure

*   `MainActivity.java`: Host Activity for all fragments.
*   `LoginFragment` / `SignUpFragment`: Handles user entry.
*   `HomeFragment`: Main video player and URL input.
*   `PlaylistFragment`: Displays the list of saved videos.
*   `database/`: Contains Room Entity, DAO, and Database classes.
*   `SessionManager.java`: Manages the user login state.
