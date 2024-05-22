package com.example.myprofit;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
// ץRecyclerView שנועד להציג רשימה של פריטים בצורה יעילה UI הוא רכיב ממשק משתמש
//פRecyclerView משמש להצגת רשימות גדולות של נתונים בצורה חלקה ויעילה
//הוא עושה זאת על ידי "מיחזור" (recycling) של תצוגות (views) שיצאו מהמסך במקום ליצור חדשות כל הזמן,
//מה שמשפר ביצועים.
class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)//הפעולה הזאת מסדרת את התא של הימים בלוח
    {
        // יוצרים אובייקט של LayoutInflater שיעזור לנו להמיר את ה-layout של כל פריט ברשימה מתוך קובץ XML.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // משתמשים ב-LayoutInflater כדי ליצור את התצוגה של כל פריט ברשימה מה-layout שנמצא בקובץ ה-XML בשם calendar_cell
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        //מקבלים את הפרמטרים של ה-layout של התצוגה.
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //בודקים אם יש יותר מ-15 ימים ברשימת התאריכים.
        if(days.size() > 15) //month view
            //אם יש יותר מ-15 ימים (תצוגת חודש), מגדירים את גובה התא כ-16.6666% מגובה ה-RecyclerView
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
        //אם יש 15 ימים או פחות (תצוגת שבוע), מגדירים את גובה התא כגובה המלא של ה-RecyclerView
            layoutParams.height = (int) parent.getHeight();
        //מחזירים את ה-ViewHolder החדש שנוצר עבור התצוגה של הפריט ברשימה.
        return new CalendarViewHolder(view, onItemListener,days);
    }

    //הפעולה משנה את צבע הרקע של ה-ViewHolder לאפור בהיר כדי להדגיש את התא שנבחר.
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        //מקבלים את התאריך המתאים לפריט במקום המתאים ברשימה.
        LocalDate date = days.get(position);
        //בודקים האם התאריך הוא null, מה שמציין שזהו תא ריק.
        if(date == null)
            //אם התאריך הוא null, מנקים את הטקסט בתיבת הטקסט של יום החודש ב-ViewHolder.
            holder.dayOfMonth.setText("");
        else
        {
            //אם התאריך אינו null, ממשיכים לשלב הבא.
            //מציבים את טקסט יום החודש בתיבת הטקסט של יום החודש ב-ViewHolder, על ידי קבלת הערך המספרי של יום החודש מהתאריך והמרהו למחרוזת.
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            //בודקים האם התאריך שווה לתאריך שנבחר, כלומר האם זהו התאריך שהמשתמש בחר.
            if(date.equals(CalendarUtils.selectedDate))
                //אם התאריך שווה לתאריך שנבחר, משנים את צבע הרקע של ה-ViewHolder לאפור בהיר. המטרה כאן היא לציין ולהדגיש למשתמש את התא שנבחר כתא נוכחי בלוח השנה.
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }//פעולה זו מחזירה את מספר הפריטים ברשימה days

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
