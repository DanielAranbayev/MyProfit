package com.example.myprofit;

import static com.example.myprofit.CalendarUtils.daysInWeekArray;
import static com.example.myprofit.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ValueEventListener postListener;
    EventAdapter eventAdapter;
    ArrayList<Event> eventsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Events/" + uid);
        loadEventsFromFirebase();
        setWeekview();
        eventsList = new ArrayList<>();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekview() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(this, dailyEvents);
        eventListView.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged(); // Ensure the ListView is refreshed
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekview();
    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekview();
    }

    public void newEventAction(View view) {//open Event edit activity by clicking on the button
        startActivity(new Intent(this, EventEditActivity.class));
        finish();

    }
    private void loadEventsFromFirebase() {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    eventsList.add(event);
                }
                eventAdapter = new EventAdapter(WeekViewActivity.this, eventsList);
                eventAdapter.notifyDataSetChanged();
                eventListView.setAdapter(eventAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);

    }

//    private void loadEventsFromFirebase() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Event.events.clear();
//                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
//                    Event event = eventSnapshot.getValue(Event.class);
//                    if (event != null) {
//                        if (Event.events.containsKey(event.getDate())) {
//                            Event.events.get(event.getDate()).add(event);
//                        } else {
//                            ArrayList<Event> eventList = new ArrayList<>();
//                            eventList.add(event);
//                            Event.events.put(event.getDate(), eventList);
//                        }
//                    }
//                }
//                setEventAdapter();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.e("WeekViewActivity", "Failed to load events from Firebase", error.toException());
//            }
//        });
//    }
}