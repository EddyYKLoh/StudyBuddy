package com.ykloh.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.signUpToolbar);
        setSupportActionBar(myToolbar);

        final RadioButton virtuallyRadioButton = (RadioButton) findViewById(R.id.virtuallyRadioButton);
        final RadioButton faceToFaceRadioButton = (RadioButton) findViewById(R.id.faceToFaceRadioButton);
        final RadioButton preferUndergraduateRadioButton = (RadioButton) findViewById(R.id.preferUndergraduateRadioButton);
        final RadioButton preferPostGraduateRadioButton = (RadioButton) findViewById(R.id.preferPostgraduateRadioButton);
        final RadioButton preferResearcherRadioButton = (RadioButton) findViewById(R.id.preferResearcherRadioButton);
        final Button savePreferenceButton = (Button) findViewById(R.id.savePreferenceButton);

        savePreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                final String emailAddress = sharedPreferences.getString("emailAddress", null);
                String meetingType = "Not selected";
                String preferredLevelOfStudies = "Not selected";

                if (virtuallyRadioButton.isChecked())
                    meetingType = "VIR";
                else if (faceToFaceRadioButton.isChecked())
                    meetingType = "F2F";


                if (preferUndergraduateRadioButton.isChecked())
                    preferredLevelOfStudies = "UG";
                else if (preferPostGraduateRadioButton.isChecked())
                    preferredLevelOfStudies = "PG";
                else if (preferResearcherRadioButton.isChecked())
                    preferredLevelOfStudies = "R";

                if (meetingType.equals("Not selected") || preferredLevelOfStudies.equals("Not selected")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(PreferenceActivity.this);
                    EmptyBuilder.setMessage("Please complete the form.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                } else {
                    PreferenceUpdater prefUpdater = new PreferenceUpdater();
                    prefUpdater.UpdatePreference(PreferenceActivity.this, emailAddress, meetingType, preferredLevelOfStudies);
                }
            }
        });
    }
}
