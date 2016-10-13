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

        String subjectString = getIntent().getStringExtra("subjectList");
        String[] subjects = subjectString.split(System.getProperty("line.separator"));

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

                String responseText = "";
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

                if (responseText.equals("")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(SelectSubjectTagsActivity.this);
                    EmptyBuilder.setMessage("Subject tags are required for your post.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                } else {
                    AI AI = new AI();
                    AI.RunAlgorithm(SelectSubjectTagsActivity.this, responseText);


                    SubjectTagAdder subjectTagAdder = new SubjectTagAdder();
                    subjectTagAdder.AddSubjectTag(SelectSubjectTagsActivity.this, responseText);


                }


            }
        });

    }

}

