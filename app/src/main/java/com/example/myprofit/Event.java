package com.example.myprofit;

import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static HashMap<String, ArrayList<Event>> events = new HashMap<>();


    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> allEvents = new ArrayList<>();
        for (String key : events.keySet()) {
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
    private String date;
    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    }


    public Event(String date, String name )
    {
        this.name = name;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

}
