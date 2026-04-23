package com.example.learningassistant.model;

import java.io.Serializable;

public class QuizQuestion implements Serializable {
    private String question;
    private String[] options;
    private int correctIndex;
    private int selectedIndex = -1;

    public QuizQuestion(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public int getSelectedIndex() { return selectedIndex; }
    public void setSelectedIndex(int idx) { this.selectedIndex = idx; }

    public boolean isCorrect() { return selectedIndex == correctIndex; }
    public String getCorrectAnswer() { return options[correctIndex]; }
    public String getSelectedAnswer() {
        return selectedIndex >= 0 ? options[selectedIndex] : "Not answered";
    }
}
