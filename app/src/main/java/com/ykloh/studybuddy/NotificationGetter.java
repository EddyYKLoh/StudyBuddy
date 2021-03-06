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
 * Created by LYK on 12/9/2016.
 */
public class NotificationGetter {

    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/notificationGetter.php");
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

    public void notificationLoader(final Context context, String userID, final ListView listView) {

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


                    List<Notification> list = new ArrayList<Notification>();
                    String postString = s;


                    if (postString.equals("")) {

                    } else {
                        String[] individualPost = postString.split(System.getProperty("line.separator"));

                        for (int i = 0; i < individualPost.length; i++) {
                            String[] postElements = individualPost[i].split("<SEPARATE>");
                            list.add(new Notification(postElements[0], postElements[1], postElements[2]));
                        }

                        NotificationAdapter adapter = new NotificationAdapter(context, list);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Notification notification = (Notification) parent.getItemAtPosition(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("userID", notification.getFromUser());
                                ProfileFragment profileFragment = new ProfileFragment();
                                android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                                profileFragment.setArguments(bundle);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.mainContentFrame, profileFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });

                    }


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
