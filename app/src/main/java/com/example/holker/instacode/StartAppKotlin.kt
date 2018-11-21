package com.example.holker.instacode

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL

class StartAppKotlin : Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)

        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId("f5f11f8268c9a944e7927a67983ebaeac2433dd0")
                .clientKey("131fe7f87d4fff6c04bab6603c66346a191ac9cb")
                .server("http://18.217.57.179:80/parse")
                .build()
        )

        val parseACL = ParseACL()
        parseACL.publicReadAccess = true
        parseACL.publicWriteAccess = true
        ParseACL.setDefaultACL(parseACL, true)
    }
}