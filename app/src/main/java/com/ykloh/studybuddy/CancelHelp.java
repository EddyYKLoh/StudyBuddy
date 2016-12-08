package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by LYK on 12/8/2016.
 */
public class CancelHelp {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/cancelHelp.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(getPostDataString(postDataParams));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                response = buffer.toString();

            }


        } catch (
                Exception e
                )

        {
            e.printStackTrace();
            response = "Couldn't connect to server.";
        }

        return response;
    }

    //TODO profile
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

    public void submitCancelHelp(final Context context, final String postID, final Bundle bundle) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        final String userID = sharedPreferences.getString("userID", null);

        class cancelHelp extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("Couldn't connect to server.")||s.equals("Query failed.")){
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage(s)
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                }else{
                    Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                    ViewPublicPostFragment viewPublicPostFragment = new ViewPublicPostFragment();
                    android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                    viewPublicPostFragment.setArguments(bundle);
                    fragmentManager.popBackStack();
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContentFrame, viewPublicPostFragment )
                            .addToBackStack(null)
                            .commit();
                }


            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("postID", params[0]);
                data.put("helperID", params[1]);
                data.put("ownerID", params[2]);
                data.put("title", params[3]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        cancelHelp ch = new cancelHelp();
        ch.execute(postID, userID, bundle.getString("ownerID"), bundle.getString("title"));

    }
}


