package com.ykloh.studybuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

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
 * Created by LYK on 10/3/2016.
 */

public class ProfilePictureUploader {
    private String sendPostRequest(HashMap<String, String> postDataParams) {
        URL url = null;
        String response = "";
        try {
            url = new URL("http://192.168.43.77/StudyBuddy/uploadPicture.php");
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
                boolean uploadFailed = false;
                boolean sqlError = false;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("Upload failed."))
                        uploadFailed = true;
                    if (line.equals("Request failed."))
                        sqlError = true;
                    buffer.append(line + '\n');
                }
                if (sqlError)
                    response = "Request failed.";
                else if (uploadFailed)
                    response = "Upload failed.";
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

    public void UploadPicture(final Context context, String emailAddress, String image) {

        class PictureUploader extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("Request failed.") || s.equals("Upload failed.") || s.equals("Couldn't connect to server.")) {
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
                    editor.putString("profPicPath", returnMessage[1]);
                    editor.commit();
                    context.startActivity(new Intent(context, MainActivity.class));

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("emailAddress", params[0]);
                data.put("image", params[1]);

                String result = sendPostRequest(data);

                return result;
            }

        }

        PictureUploader pu = new PictureUploader();
        pu.execute(emailAddress, image);

    }
}
