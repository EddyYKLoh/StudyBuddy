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
 * Created by LYK on 10/8/2016.
 */

public class LoginService {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.90/StudyBuddy/login.php");
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
                boolean correctAccount = true;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("Invalid email or password."))
                        correctAccount = false;

                    buffer.append(line + '\n');
                }
                if (correctAccount)
                    response = buffer.toString();
                else
                    response = "Invalid email or password.";

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

    public void Login(final Context context, String emailAddress, String password) {

        class login extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("Invalid email or password.") || s.equals("Couldn't connect to server.")) {
                    Toast.makeText(context, s, Toast.LENGTH_LONG).show();

                } else {

                    String[] userData = s.split(System.getProperty("line.separator"));
                    SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("userID", userData[0]);
                    editor.putString("name", userData[1]);
                    editor.putString("emailAddress", userData[2]);
                    editor.putString("password", userData[3]);
                    editor.putString("gender", userData[4]);
                    editor.putString("lvlOfStudy", userData[5]);
                    editor.putString("meetingType", userData[6]);
                    editor.putString("prefLvlOfStudy", userData[7]);
                    editor.putString("profPicPath", userData[8]);

                    editor.commit();
                    context.startActivity(new Intent(context, MainActivity.class));

                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("emailAddress", params[0]);
                data.put("password", params[1]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        login lo = new login();
        lo.execute(emailAddress, password);

    }
}
