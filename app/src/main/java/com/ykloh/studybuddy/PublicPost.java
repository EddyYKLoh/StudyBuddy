package com.ykloh.studybuddy;

/**
 * Created by LYK on 10/14/2016.
 */

public class PublicPost {
    private String publicPostTitle;
    private String userName;
    private String profilePictureUrl;

    public PublicPost(String postTitle, String userName, String profilePictureUrl){
        this.publicPostTitle = postTitle;
        this.userName = userName;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUserName(){
        return userName;
    }

    public String getPublicPostTitle(){
        return publicPostTitle;
    }

    public String getProfilePictureUrl(){
        return profilePictureUrl;
    }




}
