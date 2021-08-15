package com.mosericko.aflewo.eventsmanager;

public class Events {
    String id;
    String eventImage, eventName, eventVenue, eventTheme, eventDate, startTime, endTime;

    public Events() {
    }

    public Events(String id) {
        this.id = id;
    }

    public Events(String id, String eventImage, String eventName, String eventVenue, String eventTheme, String eventDate, String startTime, String endTime) {
        this.id = id;
        this.eventImage = eventImage;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventTheme = eventTheme;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Events(String id, String eventImage, String eventName, String startTime, String eventVenue) {
        this.id = id;
        this.eventImage = eventImage;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.startTime = startTime;
    }

    public Events(String id, String eventImage, String eventName, String eventVenue, String eventDate, String startTime, String endTime) {
        this.id = id;
        this.eventImage = eventImage;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getEventImage() {
        return eventImage;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventTheme() {
        return eventTheme;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
