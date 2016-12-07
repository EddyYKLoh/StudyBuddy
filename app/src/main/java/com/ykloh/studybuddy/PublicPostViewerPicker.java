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
    private String helperIDList;
    private String suggestedIDList;
    private Context context;
    private boolean currentUserIsAHelper = false;
    private boolean currentUserIsASuggestedHelper = false;

    public PublicPostViewerPicker(PublicPost publicPost, Context context, String helperIDList, String suggestedIDList) {
        this.profilePictureUrl = publicPost.getProfilePictureUrl();
        this.userName = publicPost.getUserName();
        this.publicPostTitle = publicPost.getPublicPostTitle();
        this.details = publicPost.getDetails();
        this.ownerID = publicPost.getOwnerID();
        this.postID = publicPost.getPostID();
        this.context = context;
        this.helperIDList = helperIDList;
        this.suggestedIDList = suggestedIDList;
        this.selectUI();
    }

    private void selectUI() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        Bundle bundle = new Bundle();
        bundle.putString("picture", this.profilePictureUrl);
        bundle.putString("name", this.userName);
        bundle.putString("title", this.publicPostTitle);
        bundle.putString("detail", this.details);
        bundle.putString("ownerID", this.ownerID);
        bundle.putString("postID", this.postID);

        if(this.helperIDList.equals("")){

        }else{
            String[] helperIDArray = this.helperIDList.split("<>");
            for(int i=0; i<helperIDArray.length ; i++){
                if(userID.equals(helperIDArray[i])){
                    this.currentUserIsAHelper = true;
                }
            }
        }


        if(this.suggestedIDList.equals("")){

        }else{
            String[] suggestedIDArray = this.suggestedIDList.split("<>");
            for(int i=0; i<suggestedIDArray.length ; i++){
                if(userID.equals(suggestedIDArray[i])){
                    this.currentUserIsASuggestedHelper = true;
                }
            }
        }

        if(this.currentUserIsAHelper){
            ViewHelpingPostFragment viewHelpingPostFragment = new ViewHelpingPostFragment();
            android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();

            viewHelpingPostFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, viewHelpingPostFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else if(this.currentUserIsASuggestedHelper){
            ViewSuggestedPostFragment viewSuggestedPostFragment = new ViewSuggestedPostFragment();
            android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();

            viewSuggestedPostFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, viewSuggestedPostFragment)
                    .addToBackStack(null)
                    .commit();
        }

        else if(this.ownerID.equals(userID)){

            ViewOwnPostFragment viewOwnPostFragment = new ViewOwnPostFragment();
            android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            viewOwnPostFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContentFrame, viewOwnPostFragment )
                    .addToBackStack(null)
                    .commit();
        }
        else {

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
