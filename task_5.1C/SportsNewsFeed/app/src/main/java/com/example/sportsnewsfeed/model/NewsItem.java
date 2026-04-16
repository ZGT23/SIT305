package com.example.sportsnewsfeed.model;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private int id;
    private String title;
    private String description;
    private String category;
    private int imageResourceId;
    private boolean isFeatured;

    public NewsItem(int id, String title, String description, String category, int imageResourceId, boolean isFeatured) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResourceId = imageResourceId;
        this.isFeatured = isFeatured;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public int getImageResourceId() { return imageResourceId; }
    public boolean isFeatured() { return isFeatured; }
}
