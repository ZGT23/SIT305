package com.example.learningassistant.model;

public class LearningTask {
    private String title;
    private String description;
    private int questionCount;

    public LearningTask(String title, String description, int questionCount) {
        this.title = title;
        this.description = description;
        this.questionCount = questionCount;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getQuestionCount() { return questionCount; }
}
