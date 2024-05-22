package com.example.myprofit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;//מציינים את רשימת התאריכים שימוש לוח השנה.
    public  final View parentView;//מציינים את התיבה העליונה של התא התצוגה. זו התיבה המכילה את כל תוכן התא התצוגה.
    public final TextView dayOfMonth;//מציינים את התיבת הטקסט שמציגה את מספר היום בחודש בתא התצוגה.
    private final CalendarAdapter.OnItemListener onItemListener;//מציינים את מאזין האירועים שמטפל בלחיצות על פריטים ברשימה
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener,ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.cellDayText);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
