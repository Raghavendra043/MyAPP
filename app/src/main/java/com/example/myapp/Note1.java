package com.example.myapp;

public class Note1 {
    private String title;
    private String description;
    private int priority;
    public Note1() {
        //empty constructor needed
    }
    public Note1(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
