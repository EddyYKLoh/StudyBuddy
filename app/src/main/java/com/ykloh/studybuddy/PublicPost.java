package com.ykloh.studybuddy;

/**
 * Created by LYK on 10/14/2016.
 */

public class PublicPost {
    private String publicPostTitle;
    private String userName;
    private String profilePictureUrl;
    private String ownerID;
    private String details;
    private String postID;

    public PublicPost( String userName,String postTitle, String profilePictureUrl, String ownerID, String details, String postID){
        this.publicPostTitle = postTitle;
        this.userName = userName;
        this.profilePictureUrl = profilePictureUrl;
        this.ownerID = ownerID;
        this.details =  details;
        this.postID = postID;
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

    public String getOwnerID(){
        return ownerID;
    }

    public String getDetails(){
        return details;
    }

    public String getPostID(){
        return postID;
    }




}
