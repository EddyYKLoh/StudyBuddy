package com.ykloh.studybuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LYK on 10/9/2016.
 */


public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private ImageView targetIV;

    public void getImageView(ImageView targetIV) {
        this.targetIV = targetIV;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlString = urls[0];
        Bitmap mIcon11 = null;

        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(urlString);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        this.targetIV.setImageBitmap(bmp);
    }
}



