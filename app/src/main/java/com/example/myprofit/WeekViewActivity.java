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
    private RecyclerView calendarRecyclerView;// רשימת תאריכים בפורמט שבועי
    private ListView eventListView;// רשימת אירועים
    private DatabaseReference databaseReference;// הפניה למסד הנתונים של Firebase
    private FirebaseAuth mAuth;// אובייקט לאימות המשתמש ב-Firebase
    ValueEventListener postListener;// אזכור לפעולת רכיב הנתונים ב-Firebase
    EventAdapter eventAdapter;// מתאם לרשימת אירועים
    ArrayList<Event> eventsList;// רשימת האירועים המקומית




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();// הגדרת התאריך הנבחר כהתאריך הנוכחי
        mAuth = FirebaseAuth.getInstance();// אתחול אובייקט האימות של Firebase
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Events/" + uid);// הגדרת הפניה למסד הנתונים של האירועים לפי מזהה המשתמש
        loadEventsFromFirebase();// טעינת האירועים מ-Firebase
        setWeekview();// קביעת התצוגה של הלוח השבועי
        eventsList = new ArrayList<>();// אתחול רשימת האירועים המקומית
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekview() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));// הצגת התאריך והשנה הנוכחיים
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);// קבלת רשימת התאריכים בשבוע הנבחר

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);// יצירת מתאם לרשימת התאריכים
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);// הגדרת סידור התצוגה של הרשימה
        calendarRecyclerView.setLayoutManager(layoutManager);// הגדרת המנהל של התצוגה
        calendarRecyclerView.setAdapter(calendarAdapter);// הגדרת המתאם של התצוגה
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;// קביעת התאריך הנבחר
        setWeekview();// עדכון התצוגה
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        // קביעת האירועים היומיים
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        // יצירת המתאם
        EventAdapter eventAdapter = new EventAdapter(this, dailyEvents);
        // הצבת המתאם בתוך רשימת התצוגה
        eventListView.setAdapter(eventAdapter);
        // רענון רשימת התצוגה
        eventAdapter.notifyDataSetChanged();
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
}