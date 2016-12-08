package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    public void PublicPostLoader(final Context context, final ListView listView) {

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

                    List<PublicPost> list = new ArrayList<PublicPost>();
                    String postString = s;

                    if (postString.equals("")) {

                    } else {
                        String[] individualPost = postString.split(System.getProperty("line.separator"));

                        for (int i = 0; i < individualPost.length; i++) {
                            String[] postElements = individualPost[i].split("<SEPARATE>");
                            list.add(new PublicPost(postElements[0], postElements[1], postElements[2], postElements[3], postElements[4], postElements[5]));
                        }

                        final RequestAdapter adapter = new RequestAdapter(context, list);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                PublicPost publicPost = (PublicPost) parent.getItemAtPosition(position);
                                UIPickerHelperFilter UIPickerHelperFilter = new UIPickerHelperFilter();
                                UIPickerHelperFilter.processUISelection(context, publicPost);

                            }
                        });

                    }

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
