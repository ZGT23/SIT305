# SportsNewsFeed

A simple and clean Android application that provides a feed of sports news and featured matches. This project demonstrates modern Android development practices using Java and XML, following a Single Activity Architecture.

## Features

- **Home Screen**: 
    - **Featured Matches**: A horizontal `RecyclerView` showcasing top stories and matches.
    - **Latest News**: A vertical `RecyclerView` listing the latest sports updates.
    - **Search & Filter**: Filter news items in real-time by category (e.g., Football, Basketball, Cricket).
- **Detail View**: 
    - Comprehensive view of a news story with a large image, title, and full description.
    - **Related Stories**: A vertical list of news items from the same category at the bottom of the screen.
    - **Bookmarks**: Save your favorite stories locally with a single tap.
- **Bookmarks Screen**:
    - A dedicated fragment to view all saved stories.
- **Single Activity Architecture**: 
    - All navigation is handled using Fragments within one `MainActivity`.
- **Local Storage**: 
    - Bookmarks are persisted using `SharedPreferences`.

## Tech Stack & Architecture

- **Language**: Java
- **UI**: XML Layouts (ConstraintLayout, CardView, RecyclerView, NestedScrollView)
- **Navigation**: Fragment Transactions with Backstack support
- **Data**: Hardcoded dummy data for demonstration
- **Persistence**: SharedPreferences for local bookmarks

## Project Structure

- `model/`: `NewsItem.java` - The data model for news stories.
- `data/`: `DummyData.java` - Provider for hardcoded news data.
- `ui/`: 
    - `HomeFragment.java`: Main dashboard logic.
    - `DetailFragment.java`: Detail view and bookmarking logic.
    - `BookmarksFragment.java`: Saved items display.
- `adapter/`:
    - `FeaturedAdapter.java`: Adapter for horizontal featured list.
    - `NewsAdapter.java`: Multi-purpose adapter for news feeds.
- `res/layout/`: XML definitions for fragments and list items.

## How to Run

1. Open the project in **Android Studio**.
2. Sync the project with Gradle files.
3. Run the app on an Android Emulator or a physical device (Minimum SDK: 24).
4. Browse the feed, search for a sport like "Football", click on a story to view details, and try out the bookmark feature!
