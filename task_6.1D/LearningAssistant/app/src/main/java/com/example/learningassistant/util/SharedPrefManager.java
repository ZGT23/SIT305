package com.example.learningassistant.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.learningassistant.model.QuizHistory;
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
    
    // New keys for 10.1D
    private static final String KEY_TOTAL_QUESTIONS = "total_questions";
    private static final String KEY_CORRECT_ANSWERS = "correct_answers";
    private static final String KEY_INCORRECT_ANSWERS = "incorrect_answers";
    private static final String KEY_ACCOUNT_TYPE = "account_type";
    private static final String KEY_QUIZ_HISTORY = "quiz_history";

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
            .putString(KEY_ACCOUNT_TYPE, "Free") // Default account type
            .putInt(KEY_TOTAL_QUESTIONS, 0)
            .putInt(KEY_CORRECT_ANSWERS, 0)
            .putInt(KEY_INCORRECT_ANSWERS, 0)
            .apply();
    }

    public boolean checkLogin(String username, String password) {
        return username.equals(prefs.getString(KEY_USERNAME, "")) &&
               password.equals(prefs.getString(KEY_PASSWORD, ""));
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "Student");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "student@example.com");
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

    // New methods for 10.1D
    
    public int getTotalQuestions() {
        return prefs.getInt(KEY_TOTAL_QUESTIONS, 0);
    }

    public int getCorrectAnswers() {
        return prefs.getInt(KEY_CORRECT_ANSWERS, 0);
    }

    public int getIncorrectAnswers() {
        return prefs.getInt(KEY_INCORRECT_ANSWERS, 0);
    }

    public void updateStats(int correct, int incorrect) {
        int total = getTotalQuestions() + correct + incorrect;
        int newCorrect = getCorrectAnswers() + correct;
        int newIncorrect = getIncorrectAnswers() + incorrect;
        prefs.edit()
            .putInt(KEY_TOTAL_QUESTIONS, total)
            .putInt(KEY_CORRECT_ANSWERS, newCorrect)
            .putInt(KEY_INCORRECT_ANSWERS, newIncorrect)
            .apply();
    }

    public String getAccountType() {
        return prefs.getString(KEY_ACCOUNT_TYPE, "Free");
    }

    public void setAccountType(String type) {
        prefs.edit().putString(KEY_ACCOUNT_TYPE, type).apply();
    }

    public void saveQuizHistory(QuizHistory history) {
        List<QuizHistory> historyList = getQuizHistory();
        historyList.add(0, history); // Add to beginning
        prefs.edit().putString(KEY_QUIZ_HISTORY, gson.toJson(historyList)).apply();
    }

    public List<QuizHistory> getQuizHistory() {
        String json = prefs.getString(KEY_QUIZ_HISTORY, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<QuizHistory>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void logout() {
        prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply();
    }
}
