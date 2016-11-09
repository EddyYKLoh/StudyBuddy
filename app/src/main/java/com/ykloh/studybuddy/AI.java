package com.ykloh.studybuddy;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by LYK on 10/13/2016.
 */

public class AI {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/runningNBC.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(this.getPostDataString(postDataParams));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }

                    response = buffer.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = "Couldn't connect to server.";
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder dataString = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                dataString.append("&");

            dataString.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            dataString.append("=");
            dataString.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return dataString.toString();
    }

    public void RunAlgorithm(final Context context, final String subjectTags) {

        class RunAI extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                context.startActivity(new Intent(context, MainActivity.class));

//                AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
//                EmptyBuilder.setMessage(s)
//                        .setCancelable(false)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                context.startActivity(new Intent(context, MainActivity.class));
//                            }
//                        })
//                        .create()
//                        .show();

                }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userPreferredMeetingType", params[0]);
                data.put("levelOfStudyPreference", params[1]);
                data.put("postID", params[2]);
                data.put("subjects", params[3]);
                data.put("userID", params[4]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("PublicPostUtil", Context.MODE_PRIVATE);
        String postID = sharedPreferences.getString("publicPostID", null);
        SharedPreferences sharedPreferencesUser = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String preferredMeetingType = sharedPreferencesUser.getString("meetingType", null);
        String preferredLevelOfStudy = sharedPreferencesUser.getString("prefLvlOfStudy", null);
        String userID = sharedPreferencesUser.getString("userID", null);


        RunAI ai = new RunAI();
        ai.execute(preferredMeetingType, preferredLevelOfStudy, postID, subjectTags, userID);

    }


}
