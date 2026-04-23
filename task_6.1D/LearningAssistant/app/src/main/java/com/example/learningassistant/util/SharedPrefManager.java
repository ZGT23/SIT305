package com.example.learningassistant.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {
    private static final String PREF_NAME = "LearningAssistantPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_INTERESTS = "interests";
    private static final String KEY_SETUP_DONE = "setup_done";

    private static SharedPrefManager instance;
    private final SharedPreferences prefs;
    private final Gson gson;

    private SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveUser(String username, String email, String password, String phone) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putString(KEY_PHONE, phone)
            .apply();
    }

    public boolean checkLogin(String username, String password) {
        return username.equals(prefs.getString(KEY_USERNAME, "")) &&
               password.equals(prefs.getString(KEY_PASSWORD, ""));
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "Student");
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void saveInterests(List<String> interests) {
        prefs.edit().putString(KEY_INTERESTS, gson.toJson(interests)).apply();
    }

    public List<String> getInterests() {
        String json = prefs.getString(KEY_INTERESTS, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void setSetupDone(boolean done) {
        prefs.edit().putBoolean(KEY_SETUP_DONE, done).apply();
    }

    public boolean isSetupDone() {
        return prefs.getBoolean(KEY_SETUP_DONE, false);
    }

    public void logout() {
        prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply();
    }
}
