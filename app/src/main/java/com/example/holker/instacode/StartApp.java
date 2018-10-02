package com.example.holker.instacode;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StartApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //Local DB
        Parse.enableLocalDatastore(this);


        //Initialize connection to server
        //You can test app. Just open in your browser http://18.223.168.114:80/apps
        //Login: user, password : QIc9Zo2JRgwb


        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("e4d056208a3089076fd88a0d21c9fe23b2dfd4d7")
                .clientKey("85d4d1347b01eb18504e649b905a50f6009abf73")
                .server("http://18.223.168.114:80/parse/")
                .build());

        ParseUser.enableAutomaticUser();

        ParseACL parseACL = new ParseACL();
        parseACL.setPublicReadAccess(true);
        parseACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(parseACL, true);

    }
}
