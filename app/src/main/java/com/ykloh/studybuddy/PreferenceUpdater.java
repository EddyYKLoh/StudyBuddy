package com.ykloh.studybuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
 * Created by LYK on 10/2/2016.
 */

public class PreferenceUpdater {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.90/StudyBuddy/updatePreference.php");
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
                response = bufferedReader.readLine();
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

    public void UpdatePreference(final Context context, String emailAddress, final String meetingType, final String preferredLvlOfStudy) {

        class preferenceUpdater extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Saving...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                if (s.equals("Saved.")) {
                    context.startActivity(new Intent(context, UpdateProfileActivity.class));
                    SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("meetType", meetingType);
                    editor.putString("preferredLvlOfStudy", preferredLvlOfStudy);
                    editor.commit();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("emailAddress", params[0]);
                data.put("meetingType", params[1]);
                data.put("preferredLvlOfStudy", params[2]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        preferenceUpdater pu = new preferenceUpdater();
        pu.execute(emailAddress, meetingType, preferredLvlOfStudy);

    }
}
