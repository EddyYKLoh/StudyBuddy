package com.ykloh.studybuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Intent myIntent = getIntent();

        final String helperID = myIntent.getStringExtra("helperID");
        String helperName = myIntent.getStringExtra("helperName");
        final String postID = myIntent.getStringExtra("postID");
        String publicPostTitle = myIntent.getStringExtra("publicPostTitle");
        String status = myIntent.getStringExtra("status");

        TextView ratingMsg = (TextView) findViewById(R.id.ratingMsg);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button) findViewById(R.id.ratingSubmitButton);

        if(status.equals("ENDED_BY_HELPER")){
            AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(this);
            EmptyBuilder.setMessage(helperName + " said he is done teaching you for the post:\n"+publicPostTitle+"\n\nDid he/she really help?")
                    .setCancelable(false)
                    .setNegativeButton("YES", null)
                    .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateHelpStatus updateStatus = new UpdateHelpStatus();
                            updateStatus.submitUpdate(RatingActivity.this, helperID, postID);
                        }
                    })
                    .create()
                    .show();
        }else if(status.equals("NOT_YET_RATE")){
            AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(this);
            EmptyBuilder.setMessage(helperName + " helped you for the ended post:\n"+publicPostTitle+"\n\nDid he/she really help?")
                    .setCancelable(false)
                    .setNegativeButton("YES", null)
                    .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateHelpStatus updateStatus = new UpdateHelpStatus();
                            updateStatus.submitUpdate(RatingActivity.this, helperID, postID);
                        }
                    })
                    .create()
                    .show();
        }

        ratingMsg.setText("Please rate your buddy for his effort.");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float rating = ratingBar.getRating();
                SubmitRating submitRating = new SubmitRating();
                submitRating.sendRating(RatingActivity.this, helperID, postID, rating);
            }
        });



    }

    @Override
    public void onBackPressed() {

    }
}
