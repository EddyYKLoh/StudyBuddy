package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectSubjectTagsActivity extends AppCompatActivity {
    List<Subject> list = new ArrayList<Subject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject_tags);

        SubjectListGetter subjectListGetter = new SubjectListGetter();
        subjectListGetter.GetSubjects(SelectSubjectTagsActivity.this);
        String[] subjects = loadArray("subject", this);
        removeArray(this, "subject");

        for (int i = 0; i < subjects.length; i++) {
            list.add(new Subject(subjects[i]));
        }


        final CustomAdapter adapter = new CustomAdapter(this, list);
        ListView listview = (ListView) findViewById(R.id.selectSubjectTagListView);
        listview.setAdapter(adapter);


        Button myButton = (Button) findViewById(R.id.checkButton);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String responseText = null;
                boolean first = true;
                List<Subject> subjectList = list;
                for (int i = 0; i < subjectList.size(); i++) {
                    Subject subject = subjectList.get(i);
                    if (subject.isSelected()) {
                        if (first) {
                            responseText = subject.getName();
                            first = false;
                        } else {
                            responseText += "\n" + subject.getName();
                        }
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

                String[] subjects = responseText.split(System.getProperty("line.separator"));



            }
        });

    }


    public static void removeArray(Context mContext, String arrayName) {
        SharedPreferences prefs = mContext.getSharedPreferences("SubjectList", 0);
        prefs.edit().clear().commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("SubjectList", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
}

