package com.example.myapp;

public class Reminder {
    private String event;
    private String time;

    public String getEventName() {
        return event;
    }
    public String getTime() {
        return time;
    }

    public Reminder(String event, String time){
        this.event = event;
        this.time = time;
    }

}