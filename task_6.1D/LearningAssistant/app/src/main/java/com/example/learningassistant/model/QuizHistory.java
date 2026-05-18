package com.example.learningassistant.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuizHistory implements Serializable {
    private String topic;
    private int score;
    private int totalQuestions;
    private String date;

    public QuizHistory(String topic, int score, int totalQuestions) {
        this.topic = topic;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
    }

    public String getTopic() { return topic; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public String getDate() { return date; }
}
