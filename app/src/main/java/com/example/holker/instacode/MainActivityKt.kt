@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.holker.instacode

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.example.holker.instacode.R.id.*
import com.parse.LogInCallback
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main_kt.*

class MainActivityKt : AppCompatActivity() {

    private var mLoginMode: Boolean = true

    fun logIn() {
        ParseUser.logInInBackground(username.text.toString(), password.text.toString(),
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kt)

        val animation = AnimationDrawable()
        animation.setEnterFadeDuration(3500)
        animation.setExitFadeDuration(3500)
        animation.start()

        //change type of logging
        tv_switch.setOnClickListener {
            if (mLoginMode) {
                btn_center.text = getString(R.string.signUp)
                mLoginMode = !mLoginMode
                tv_switch.text = getString(R.string.orLogIn)
            } else {
                btn_center.text = getString(R.string.logIn)
                mLoginMode = !mLoginMode
                tv_switch.text = getString(R.string.orSignUp)
            }
        }

        //LogIn
        btn_center.setOnClickListener {
            val animation: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
            btn_center.startAnimation(animation)
        }

        //hide keyboard
        rl_main.setOnClickListener {
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        }
    }
}