# Lost and Found App (Task 7.1P)

A native Android application built for **SIT305 - Mobile Application Development**. This app allows users to create, view, and manage advertisements for lost and found items. It uses a local SQLite database for data persistence and supports image selection from the device gallery.

## Features

- **Home Screen**: Quick access to create new adverts or view the list of items.
- **Create Advert**: 
  - Choose between "Lost" and "Found" types.
  - Input details: Name, Phone, Description, and Location.
  - Select category from a dropdown (Electronics, Pets, Wallets, etc.).
  - Upload an image from the gallery.
  - Automatic timestamp generation for each post.
- **Items List**: 
  - Display all items in a RecyclerView with thumbnails.
  - Filter items by category using a Spinner.
- **Item Details**: 
  - View full details and the full-sized image of a selected item.
  - Remove/Delete an item once it has been returned.
- **Data Persistence**: All data is stored locally using SQLite.

## Technologies Used

- **Language**: Java
- **UI Framework**: XML (Android Views)
- **Database**: SQLite (`SQLiteOpenHelper`)
- **Image Handling**: Android Photo Picker / GetContent API
- **Target SDK**: API 36 (Minimum SDK 24)

## Project Structure

```text
com.example.lostfoundapp
├── MainActivity.java        # Entry point with main navigation
├── CreateAdvertActivity.java # Form for creating new posts
├── AdvertListActivity.java   # Scrollable list with category filtering
├── AdvertDetailActivity.java # Full details and delete functionality
├── data
│   └── DatabaseHelper.java   # SQLite database management
├── model
│   └── Advert.java           # Data model for a Lost/Found item
└── adapter
    └── AdvertAdapter.java    # RecyclerView adapter for the list
```

## SQLite Database Explanation

The application uses a database named `lost_found_db` with a table `adverts`. 
Fields include:
- `id`: Primary key (Integer)
- `type`: "Lost" or "Found"
- `name`: Contact person's name
- `phone`: Contact number
- `description`: Detailed description of the item
- `category`: Item category for filtering
- `location`: Where the item was lost/found
- `image_uri`: Local URI string for the selected image
- `timestamp`: Date and time when the advert was created

## How to Run

1. Open the project in **Android Studio**.
2. Sync the project with **Gradle** files.
3. Run the application on an **Android Emulator** (API 24 or above) or a physical device.
4. To test the image upload on an emulator:
   - Drag and drop an image file (.jpg/.png) onto the emulator.
   - The image will be saved to the `Downloads` folder.
   - Use the "Select Image" button in the app to pick it.


