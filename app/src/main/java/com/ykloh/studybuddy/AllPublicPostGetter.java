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
 * Created by LYK on 11/9/2016.
 */

public class AllPublicPostGetter {
    private String sendGetRequest() {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/allPublicPostGetter.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

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


    public void PublicPostLoader(final Context context) {

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
                    bundle.putString("PublicPostList", s);

                    HomeFragment homeFragment = new HomeFragment();
                    android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                    homeFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContentFrame, homeFragment )
                            .addToBackStack(null)
                            .commit();


                }

            }

            @Override
            protected String doInBackground(String... params) {

                String result = sendGetRequest();

                return result;
            }
        }

        Loader lo = new Loader();
        lo.execute();

    }
}
