package com.ykloh.studybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.signUpToolbar);
        setSupportActionBar(myToolbar);

        final Button savePreferenceButton = (Button) findViewById(R.id.savePreferenceButton);

        savePreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreferenceActivity.this,UpdateProfileActivity.class));
            }
        });
    }
}
