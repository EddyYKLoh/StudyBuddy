package com.ykloh.studybuddy;

/**
 * Created by LYK on 12/9/2016.
 */

public class Notification {

    private String notification;
    private String toUser;
    private String fromUser;

    public Notification( String notification,String toUser, String fromUser){
        this.notification = notification;
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

    public String getNotification(){return this.notification;}

    public String getToUser(){return  this.toUser;}

    public String getFromUser(){return this.fromUser;}




}
