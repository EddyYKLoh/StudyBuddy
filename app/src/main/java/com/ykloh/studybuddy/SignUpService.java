package com.ykloh.studybuddy;

import android.app.ProgressDialog;
import android.content.Context;
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
 * Created by LYK on 10/2/2016.
 */

public class SignUpService {

    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.77/StudyBuddy/signUp.php");
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
                boolean emailExisted = false;
                boolean sqlError = false;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("E-mail already exist."))
                        emailExisted = true;
                    if (line.equals("Please try again."))
                        sqlError = true;
                    buffer.append(line + '\n');
                }
                if (sqlError)
                    response = "Please try again.";
                else if (emailExisted)
                    response = "E-mail already exist.";
                else
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

    public void SignUp(final Context context, final String name, final String emailAddress, String password, final String gender, final String levelOfStudy) {

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Signing up...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equals("E-mail already exist.") || s.equals("Please try again.") || s.equals("Couldn't connect to server.")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(context);
                    EmptyBuilder.setMessage(s)
                            .setNegativeButton("OK", null)
                            .create()
                            .show();

                } else {

                    String[] returnMessage = s.split(System.getProperty("line.separator"));
                    Toast.makeText(context, returnMessage[0], Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("name", name);
                    editor.putString("emailAddress", emailAddress);
                    editor.putString("gender", gender);
                    editor.putString("lvlOfStudy", levelOfStudy);
                    editor.putString("userID", returnMessage[1]);
                    editor.commit();
                    context.startActivity(new Intent(context, PreferenceActivity.class));

                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("emailAddress", params[1]);
                data.put("password", params[2]);
                data.put("gender", params[3]);
                data.put("levelOfStudy", params[4]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, emailAddress, password, gender, levelOfStudy);

    }
}
