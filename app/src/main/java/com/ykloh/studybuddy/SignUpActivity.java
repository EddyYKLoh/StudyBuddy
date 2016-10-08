package com.ykloh.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.signUpToolbar);
        setSupportActionBar(myToolbar);

        final Button signUpButton = (Button) findViewById(R.id.signUpButton);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText newEmailAddressEditText = (EditText) findViewById(R.id.newEmailAddressEditText);
        final EditText newPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
        final EditText confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        final RadioButton maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        final RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        final RadioButton undergraduateRadioButton = (RadioButton) findViewById(R.id.undergraduateRadioButton);
        final RadioButton postgraduateRadioButton = (RadioButton) findViewById(R.id.postgraduateRadioButton);
        final RadioButton researcherRadioButton = (RadioButton) findViewById(R.id.researcherRadioButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString().trim();
                String emailAddress = newEmailAddressEditText.getText().toString().trim().toLowerCase();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String gender = "Not Selected";
                String levelOfStudy = "Not selected";
                String password = null;


                if (maleRadioButton.isChecked())
                    gender = "male";
                else if (femaleRadioButton.isChecked())
                    gender = "female";

                if (undergraduateRadioButton.isChecked())
                    levelOfStudy = "UG";
                else if (postgraduateRadioButton.isChecked())
                    levelOfStudy = "PG";
                else if (researcherRadioButton.isChecked())
                    levelOfStudy = "R";

                if (name.equals("") ||
                        name.equals(null) ||
                        emailAddress.equals("") ||
                        emailAddress.equals(null) ||
                        newPassword.equals("") ||
                        newPassword.equals(null) ||
                        confirmPassword.equals("") ||
                        confirmPassword.equals(null) ||
                        gender.equals("Not Selected") ||
                        levelOfStudy.equals("Not Selected")) {

                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(SignUpActivity.this);
                    EmptyBuilder.setMessage("Please complete the form.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                } else {
                    if (newPassword.equals(confirmPassword)) {
                        password = newPassword;
                        SignUpService signUpService = new SignUpService();
                        signUpService.SignUp(SignUpActivity.this, name, emailAddress, password, gender, levelOfStudy);

                    } else {
                        AlertDialog.Builder WrongPasswordBuilder = new AlertDialog.Builder(SignUpActivity.this);
                        WrongPasswordBuilder.setMessage("Confirm password doesn't match.")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }
                }


            }
        });


    }
}
