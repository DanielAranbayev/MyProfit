package com.example.myprofit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdapterEvents extends ArrayAdapter<Events> {
    Context context;
    List<Events> objects;
    public AdapterEvents(Context context, int resource, int textViewResourceId, List<Events> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Events events = getItem(position);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.event_cell,parent,false);
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = events.getDate() + " - " + events.getName();
        eventCellTV.setText(eventTitle);
        return view;
    }
}