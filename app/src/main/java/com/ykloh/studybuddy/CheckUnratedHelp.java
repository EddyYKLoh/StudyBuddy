package com.ykloh.studybuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
 * Created by LYK on 12/8/2016.
 */
public class CheckUnratedHelp {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.103/StudyBuddy/checkUnratedHelp.php");
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
                boolean first = true;
                while ((line = bufferedReader.readLine()) != null) {
                    if(first)
                        buffer.append(line);
                    else
                        buffer.append('\n'+line);
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

    public void checkUser(final Context context, String userID) {

        class checkUnrated extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("Couldn't connect to server.")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage("Couldn't connect to server.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {
                    if(s.contains("<SEPARATE>")){
                        String[] individualHelp = s.split("<>");
                        String[] components = individualHelp[0].split("<SEPARATE>");
                        String helperID = components[0];
                        String helperName = components [1];
                        String postID = components[2];
                        String publicPostTitle = components[3];
                        String status = components[4];

                        Intent myIntent = new Intent(context, RatingActivity.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        myIntent.putExtra("helperID",helperID);
                        myIntent.putExtra("helperName",helperName);
                        myIntent.putExtra("postID",postID);
                        myIntent.putExtra("publicPostTitle",publicPostTitle);
                        myIntent.putExtra("status",status);
                        context.startActivity(myIntent);

                    }else if(s.equals("Clear.")){
                        HomeFragment homeFragment = new HomeFragment();
                        android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.mainContentFrame, homeFragment )
                                .addToBackStack(null)
                                .commit();
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

        checkUnrated cu = new checkUnrated();
        cu.execute(userID);

    }
}
