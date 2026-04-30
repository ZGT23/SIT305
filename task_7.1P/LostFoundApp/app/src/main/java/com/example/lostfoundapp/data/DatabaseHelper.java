package com.example.lostfoundapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lostfoundapp.model.Advert;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lost_found_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ADVERTS = "adverts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADVERTS_TABLE = "CREATE TABLE " + TABLE_ADVERTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_IMAGE_URI + " TEXT,"
                + COLUMN_TIMESTAMP + " TEXT" + ")";
        db.execSQL(CREATE_ADVERTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVERTS);
        onCreate(db);
    }

    public long insertAdvert(Advert advert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, advert.getType());
        values.put(COLUMN_NAME, advert.getName());
        values.put(COLUMN_PHONE, advert.getPhone());
        values.put(COLUMN_DESCRIPTION, advert.getDescription());
        values.put(COLUMN_CATEGORY, advert.getCategory());
        values.put(COLUMN_LOCATION, advert.getLocation());
        values.put(COLUMN_IMAGE_URI, advert.getImageUri());
        values.put(COLUMN_TIMESTAMP, advert.getTimestamp());

        long id = db.insert(TABLE_ADVERTS, null, values);
        db.close();
        return id;
    }

    public List<Advert> getAllAdverts() {
        List<Advert> advertList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ADVERTS + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Advert advert = new Advert();
                advert.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                advert.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                advert.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                advert.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                advert.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                advert.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                advert.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
                advert.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)));
                advert.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
                advertList.add(advert);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return advertList;
    }

    public List<Advert> getAdvertsByCategory(String category) {
        List<Advert> advertList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ADVERTS, null, COLUMN_CATEGORY + "=?",
                new String[]{category}, null, null, COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Advert advert = new Advert();
                advert.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                advert.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                advert.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                advert.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                advert.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                advert.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                advert.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
                advert.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)));
                advert.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
                advertList.add(advert);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return advertList;
    }

    public Advert getAdvertById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ADVERTS, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Advert advert = new Advert();
            advert.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            advert.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
            advert.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            advert.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
            advert.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            advert.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            advert.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
            advert.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)));
            advert.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)));
            cursor.close();
            db.close();
            return advert;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public void deleteAdvert(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADVERTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
