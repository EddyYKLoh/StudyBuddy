package com.ykloh.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateProfileActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageButton profilePictureImageView;
    private Button uploadProfilePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.signUpToolbar);
        setSupportActionBar(myToolbar);

        profilePictureImageView = (ImageButton) findViewById(R.id.profilePicturePreviewImageView);
        uploadProfilePictureButton = (Button) findViewById(R.id.uploadProfilePictureButton);


        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        uploadProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                final String emailAddress = sharedPreferences.getString("emailAddress", null);

                if (bitmap != null) {
                    String imageString = BitMapToString(bitmap);
                    ProfilePictureUploader profilePictureUploader = new ProfilePictureUploader();
                    profilePictureUploader.UploadPicture(UpdateProfileActivity.this, emailAddress, imageString);

                } else {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(UpdateProfileActivity.this);
                    EmptyBuilder.setMessage("Please choose a photo.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                }
            }
        });

    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilePictureImageView.setImageBitmap(bitmap);
                profilePictureImageView.setBackgroundColor(Color.WHITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
