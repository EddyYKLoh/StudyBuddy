package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by LYK on 10/10/2016.
 */

public class CustomeAdapter extends ArrayAdapter<String> {
    String[] subjects = null;
    Context context;

    public CustomeAdapter(Context context, String[] resource) {
        super(context, R.layout.select_subject_row, resource);
        this.context = context;
        this.subjects = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.select_subject_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.selectSubjectTextView);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.selectSubjectCheckBox);
        name.setText(subjects[position]);

        return convertView;
    }
}
