package com.ykloh.studybuddy;

/**
 * Created by LYK on 10/14/2016.
 */

public class PublicPost {
    private String publicPostTitle;
    private String userName;

    public PublicPost(String postTitle, String userName){
        this.publicPostTitle = postTitle;
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }

    public String getPublicPostTitle(){
        return publicPostTitle;
    }


}
