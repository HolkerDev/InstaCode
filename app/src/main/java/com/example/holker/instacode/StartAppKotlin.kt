package com.example.holker.instacode

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL

public class StartAppKotlin : Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)

        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId("e4d056208a3089076fd88a0d21c9fe23b2dfd4d7")
                .clientKey("85d4d1347b01eb18504e649b905a50f6009abf73")
                .server("http://18.223.168.114:80/parse/")
                .build()
        )

        var parseACL: ParseACL = ParseACL()
        parseACL.publicReadAccess = true
        parseACL.publicWriteAccess = true
        ParseACL.setDefaultACL(parseACL, true)


    }
}