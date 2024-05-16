package com.example.myprofit;

import static com.example.myprofit.Event.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.util.ArrayList;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV,eventTimeTV;
    private LocalTime time;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        mAuth  = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Events/" + uid);//giving reference
        time = LocalTime.now();
        eventDateTV.setText("Date; " + CalendarUtils.formattedData(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time; " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new  Event(CalendarUtils.selectedDate,eventName,time);
        Event.eventsList.add(newEvent);


        if (events.containsKey(newEvent.getDate())) {
            // Add the event to the existing ArrayList
            events.get(newEvent.getDate()).add(newEvent);
        } else {
            // Create a new ArrayList and add the event
            ArrayList<Event> eventList2 = new ArrayList<>();
            eventList2.add(newEvent);
            // Put the new list into the HashMap
            events.put(newEvent.getDate(), eventList2);
        }
//        eventList.add(event);
//        events.put(newEvent.getDate(), eventList);
//        Events dbEvent = new Events(eventName, CalendarUtils.selectedDate, time);
//        databaseReference.child(eventName).setValue(dbEvent);
        finish();
    }
}