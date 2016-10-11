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
            url = new URL("http://192.168.43.90/StudyBuddy/subjectGetter.php");
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

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("SubjectList", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public static void removeArray(Context mContext, String arrayName) {
        SharedPreferences prefs = mContext.getSharedPreferences("SubjectList", 0);
        prefs.edit().clear().commit();
    }


    public void GetSubjects(final Context context) {

        class subjectGetter extends AsyncTask<Void, Void, String> {
            private AppCompatActivity CurrentActivity = (AppCompatActivity) context;

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
                    String[] subjects = s.split(System.getProperty("line.separator"));
                    saveArray(subjects, "subject", context);


                }
            }

            @Override
            protected String doInBackground(Void... Params) {

                String result = sendGetRequest();
                removeArray(context, "subject");

                return result;
            }

        }

        subjectGetter sg = new subjectGetter();
        sg.execute();


    }


}

