package com.ykloh.studybuddy;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class SelectSubjectTagsActivity extends AppCompatActivity {
    public AppCompatActivity activity = SelectSubjectTagsActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject_tags);

        SubjectListGetter subjectListGetter = new SubjectListGetter();
        subjectListGetter.GetSubjects(SelectSubjectTagsActivity.this, activity);




    }
}
