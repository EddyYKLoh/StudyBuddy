package com.ykloh.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    private Button createAccountButton;
    private Button loginButton;
    private EditText emailAddressEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        boolean loggedin = sharedPreferences.getBoolean("loggedIn",false);
        if(loggedin){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        loginButton = (Button) findViewById(R.id.loginButton); 
        
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailAddressEditText = (EditText) findViewById(R.id.emailAddEditText);
                passwordEditText = (EditText) findViewById(R.id.passwordEditText);
                String emailAddress = emailAddressEditText.getText().toString().trim().toLowerCase();
                String password = passwordEditText.getText().toString().trim();

                if (emailAddress.equals("")||emailAddress.equals(null)||password.equals("")||password.equals(null)) {

                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(LoginActivity.this);
                    EmptyBuilder.setMessage("Please fill in your email and password.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {

                    LoginService login = new LoginService();
                    login.Login(LoginActivity.this, emailAddress, password);

                }
            }
        });


    }
}
