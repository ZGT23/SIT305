package com.example.learningassistant.util;

import com.example.learningassistant.model.LearningTask;
import com.example.learningassistant.model.QuizQuestion;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<LearningTask> getLearningTasks() {
        List<LearningTask> tasks = new ArrayList<>();
        tasks.add(new LearningTask("Algorithms", "Learn sorting and searching algorithms", 3));
        tasks.add(new LearningTask("Data Structures", "Explore arrays, lists, trees and graphs", 3));
        tasks.add(new LearningTask("Web Development", "Master HTML, CSS and JavaScript basics", 3));
        tasks.add(new LearningTask("AI/ML Basics", "Introduction to machine learning concepts", 3));
        return tasks;
    }

    public static List<QuizQuestion> getQuestionsForTopic(String topic) {
        switch (topic) {
            case "Algorithms":
                return Arrays.asList(
                    new QuizQuestion(
                        "What is the time complexity of Binary Search?",
                        new String[]{"O(n)", "O(log n)", "O(n²)"},
                        1
                    ),
                    new QuizQuestion(
                        "Which sorting algorithm has the best average-case performance?",
                        new String[]{"Bubble Sort", "Selection Sort", "Quick Sort"},
                        2
                    ),
                    new QuizQuestion(
                        "What data structure does Depth First Search (DFS) use internally?",
                        new String[]{"Queue", "Stack", "Heap"},
                        1
                    )
                );
            case "Data Structures":
                return Arrays.asList(
                    new QuizQuestion(
                        "Which data structure follows FIFO (First In First Out) order?",
                        new String[]{"Stack", "Queue", "Tree"},
                        1
                    ),
                    new QuizQuestion(
                        "What is the time complexity of accessing an element in an array by index?",
                        new String[]{"O(1)", "O(log n)", "O(n)"},
                        0
                    ),
                    new QuizQuestion(
                        "Which tree traversal visits the root node first?",
                        new String[]{"Inorder", "Preorder", "Postorder"},
                        1
                    )
                );
            case "Web Development":
                return Arrays.asList(
                    new QuizQuestion(
                        "Which HTML tag is used to create a hyperlink?",
                        new String[]{"<link>", "<href>", "<a>"},
                        2
                    ),
                    new QuizQuestion(
                        "What does CSS stand for?",
                        new String[]{"Creative Style Sheets", "Cascading Style Sheets", "Computer Style Syntax"},
                        1
                    ),
                    new QuizQuestion(
                        "Which JavaScript method adds an element to the end of an array?",
                        new String[]{"push()", "pop()", "shift()"},
                        0
                    )
                );
            case "AI/ML Basics":
                return Arrays.asList(
                    new QuizQuestion(
                        "What does ML stand for in the context of computer science?",
                        new String[]{"Machine Learning", "Model Language", "Multi Layer"},
                        0
                    ),
                    new QuizQuestion(
                        "Which type of machine learning uses labeled training data?",
                        new String[]{"Unsupervised Learning", "Supervised Learning", "Reinforcement Learning"},
                        1
                    ),
                    new QuizQuestion(
                        "What is overfitting in machine learning?",
                        new String[]{
                            "Model performs poorly on training data",
                            "Model memorizes training data but fails on new data",
                            "Model has too few parameters to learn"
                        },
                        1
                    )
                );
            default:
                return new ArrayList<>();
        }
    }
}
