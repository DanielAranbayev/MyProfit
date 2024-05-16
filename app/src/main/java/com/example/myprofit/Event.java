package com.example.myprofit;

import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static HashMap<LocalDate, ArrayList<Event>> events = new HashMap<>();


    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> allEvents = new ArrayList<>();
        for (LocalDate key : events.keySet()) {
            allEvents.addAll(events.get(key));
        }
        return allEvents;
//        if (events != null) {
//            if (events.containsKey(date)) {
//                return events.get(date);
//            }
//        }
//        return new ArrayList<>();
    }


    private String name;
    private LocalDate date;
    private LocalTime time;
    public Event() {
    }

    public Event(LocalDate date, String name, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
