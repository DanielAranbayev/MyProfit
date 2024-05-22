package com.example.myprofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter  extends ArrayAdapter<Event>
{
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0,events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);//מקבלת את אובייקט האירוע במיקום הנוכחי של הרשימה.
        if (convertView == null)//אם אין תצוגה ממוחזרת זמינה, מנפחים תצוגה חדשה
            // מנפח את פריסת התא המותאם אישית לאירוע
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        // מחפש את TextView בפריסה המנופחת שבו יוגדר שם האירוע
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        // יוצר מחרוזת עבור שם האירוע שכוללת את התאריך ושם האירוע
        String eventTitle = event.getDate() + " - " + event.getName();

        // מגדיר את מחרוזת שם האירוע ל-TextView
        eventCellTV.setText(eventTitle);
        // מחזיר את התצוגה שהושלמה להצגה על המסך
        return convertView;
    }
}
