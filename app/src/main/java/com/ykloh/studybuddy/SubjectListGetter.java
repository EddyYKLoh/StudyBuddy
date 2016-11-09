package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by LYK on 10/9/2016.
 */

public class SubjectListGetter {
    private String sendGetRequest() {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/subjectGetter.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                boolean sqlError = false;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("Request failed."))
                        sqlError = true;
                    buffer.append(line + '\n');
                }
                if (sqlError)
                    response = "Request failed.";
                else
                    response = buffer.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = "Couldn't connect to server.";
        }

        return response;
    }


    public void GetSubjects(final Context context) {

        class subjectGetter extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("Request Failed.")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage(s)
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {

                    Intent myIntent = new Intent(context, SelectSubjectTagsActivity.class);
                    myIntent.putExtra("subjectList", s);
                    context.startActivity(myIntent);

                }
            }

            @Override
            protected String doInBackground(Void... Params) {

                String result = sendGetRequest();
                return result;
            }

        }

        subjectGetter sg = new subjectGetter();
        sg.execute();
    }


}
