# Lost and Found Map App (Task 9.1P)

An advanced version of the Lost and Found application built for **SIT305 - Mobile Application Development**. This version extends the original functionality by integrating Google Maps, geolocation services, and radius-based searching.

## New Features (9.1P Upgrade)

- **Map View**: Visualize all lost and found items as markers on an interactive Google Map.
- **Location Autocomplete**: integrated Google Places SDK to provide accurate address suggestions during post creation.
- **GPS Integration**: Fetch the user's precise current location using the device's GPS.
- **Radius-Based Search**: Filter map markers to show only items within a user-defined distance (in kilometers) from their current position.
- **Secure Key Management**: Uses the `Secrets Gradle Plugin` to keep API keys safe and out of version control (GitHub).

## Technologies Used

- **Maps & Location**: Google Maps SDK, Google Places SDK, FusedLocationProviderClient.
- **Database**: SQLite (Updated schema with Latitude and Longitude support).
- **Architecture**: Java with XML Layouts.
- **Security**: Secrets Gradle Plugin for Android.

## Project Structure

```text
com.example.lostfoundapp
├── MapActivity.java           # New activity for the Google Map view
├── CreateAdvertActivity.java  # Updated with Places Autocomplete & GPS
├── data/DatabaseHelper.java   # Database updated for Geo-coordinates
└── ... (Standard 7.1P files)
```

## Setup Instructions

To run this project locally, you must provide your own Google Maps API Key:

1. Obtain an API Key from the [Google Cloud Console](https://console.cloud.google.com/).
2. Enable **Maps SDK for Android** and **Places API**.
3. In your project root, open or create a file named `local.properties`.
4. Add your key: `MAPS_API_KEY=YOUR_REAL_API_KEY_HERE`.
5. **Sync Gradle** and run the app. 

*Note: The `local.properties` file is ignored by Git to prevent your API key from being leaked.*


