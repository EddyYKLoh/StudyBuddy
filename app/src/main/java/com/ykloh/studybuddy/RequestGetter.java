package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

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
 * Created by LYK on 10/14/2016.
 */

public class RequestGetter {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.77/StudyBuddy/requestGetter.php");
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

    public void RequestLoader(final Context context, String userID) {

        class Loader extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("Couldn't connect to server.")) {

                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage(s)
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString("RequestList", s);

                    RequestFragment requestFragment = new RequestFragment();
                    android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                    requestFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContentFrame, requestFragment )
                            .addToBackStack(null)
                            .commit();


                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userID", params[0]);

                String result = sendPostRequest(data);

                return result;
            }
        }

        Loader lo = new Loader();
        lo.execute(userID);

    }
}
