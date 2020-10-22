package com.mosericko.aflewo.eventsmanager;

public class Events {
    int id;
    String eventImage,eventName,eventVenue,eventTheme,eventDate,startTime,endTime;

    public Events(int id,String eventImage, String eventName, String eventVenue, String eventTheme, String eventDate, String startTime, String endTime) {
        this.id = id;
        this.eventImage=eventImage;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventTheme = eventTheme;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public int getId() {
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
