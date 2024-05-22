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
    DatabaseReference databaseReference;// הפניה למסד הנתונים של Firebase
    FirebaseAuth mAuth;// אובייקט לאימות המשתמש ב-Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        mAuth  = FirebaseAuth.getInstance();// אתחול אובייקט האימות של Firebase
        String uid = mAuth.getCurrentUser().getUid();// קבלת מזהה המשתמש הנוכחי
        databaseReference = FirebaseDatabase.getInstance().getReference("Events/" + uid);// הגדרת הפניה למסד הנתונים של האירועים לפי מזהה המשתמש
        eventDateTV.setText("Date; " + CalendarUtils.formattedData(CalendarUtils.selectedDate));// הצגת התאריך הנבחר בשדה המתאים
        eventTimeTV.setText("Time; " + CalendarUtils.formattedTime(LocalTime.now()));// הצגת השעה הנוכחית בשדה המתאים
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();// קבלת שם האירוע שהוזן
        String date = CalendarUtils.formattedDate(CalendarUtils.selectedDate);// קבלת התאריך בפורמט המתאים
        Event newEvent = new  Event(date,eventName);// יצירת אובייקט אירוע חדש עם התאריך והשם
        Event.eventsList.add(newEvent);// הוספת האירוע החדש לרשימת כל האירועים

        // בדיקה אם כבר יש אירועים בתאריך זה במפה
        if (events.containsKey(newEvent.getDate())) {
            // הוספת האירוע לרשימת האירועים הקיימת בתאריך זה
            events.get(newEvent.getDate()).add(newEvent);
        } else {
            // יצירת רשימת אירועים חדשה והוספת האירוע
            ArrayList<Event> eventList2 = new ArrayList<>();
            eventList2.add(newEvent);
            // הוספת הרשימה החדשה למפה
            events.put(newEvent.getDate(), eventList2);
        }
        // שמירת האירוע החדש במסד הנתונים של Firebase
        databaseReference.child(eventName).setValue(newEvent);

        startActivity(new Intent(this, WeekViewActivity.class));
        // מעבר לפעילות WeekViewActivity (לוח שבועי)
        finish();
    }
}