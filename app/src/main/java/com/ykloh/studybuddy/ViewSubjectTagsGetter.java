package com.ykloh.studybuddy;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

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
 * Created by LYK on 12/7/2016.
 */

public class ViewSubjectTagsGetter {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/viewSubjectGetter.php");
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
                boolean sqlError = false;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("Query failed."))
                        sqlError = true;
                    buffer.append(line + '\n');
                }
                if (sqlError)
                    response = "Query failed.";
                else
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

    public void load (final Context context, String postID, final TextView textView) {

        class loadTags extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("Query failed.") || s.equals("Couldn't connect to server.")) {

                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage(s)
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {

                    textView.setText(s);

                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("postID", params[0]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        loadTags lo = new loadTags();
        lo.execute(postID);

    }
}
