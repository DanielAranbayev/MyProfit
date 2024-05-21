package com.example.myprofit;

import static android.app.PendingIntent.getActivity;
import static com.example.myprofit.Event.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.ArrayList;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV,eventTimeTV;
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
        eventDateTV.setText("Date; " + CalendarUtils.formattedData(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time; " + CalendarUtils.formattedTime(LocalTime.now()));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        String date = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        Event newEvent = new  Event(date,eventName);
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
        databaseReference.child(eventName).setValue(newEvent);
        startActivity(new Intent(this, WeekViewActivity.class));
        finish();
    }
}