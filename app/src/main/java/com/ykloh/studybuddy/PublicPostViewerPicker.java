package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by LYK on 12/6/2016.
 */
public class PublicPostViewerPicker {
    private String profilePictureUrl;
    private String userName;
    private String publicPostTitle;
    private String details;
    private String ownerID;
    private String postID;
    private Context context;

    public PublicPostViewerPicker(String profilePictureUrl, String userName, String publicPostTitle, String details, String ownerID, String postID, Context context) {
        this.profilePictureUrl = profilePictureUrl;
        this.userName = userName;
        this.publicPostTitle = publicPostTitle;
        this.details = details;
        this.ownerID = ownerID;
        this.postID = postID;
        this.context = context;
        this.selectUI();
    }

    private void selectUI() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);
        //TODO helperID
        if(this.ownerID.equals(userID)){
            Bundle bundle = new Bundle();
            bundle.putString("picture", this.profilePictureUrl);
            bundle.putString("name", this.userName);
            bundle.putString("title", this.publicPostTitle);
            bundle.putString("detail", this.details);
            bundle.putString("ownerID", this.ownerID);
            bundle.putString("postID", this.postID);

            ViewOwnPostFragment viewOwnPostFragment = new ViewOwnPostFragment();
            android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            viewOwnPostFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, viewOwnPostFragment )
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("picture", this.profilePictureUrl);
            bundle.putString("name", this.userName);
            bundle.putString("title", this.publicPostTitle);
            bundle.putString("detail", this.details);
            bundle.putString("ownerID", this.ownerID);
            bundle.putString("postID", this.postID);

            ViewPublicPostFragment viewPublicPostFragment = new ViewPublicPostFragment();
            android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            viewPublicPostFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, viewPublicPostFragment )
                    .addToBackStack(null)
                    .commit();
        }
    }
}
