@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.holker.instacode

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.parse.ParseFile
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main_kt.*
import java.io.ByteArrayOutputStream

class MainActivityKt : AppCompatActivity() {
    lateinit var file:ParseFile

    private var mLoginMode: Boolean = true


    private fun pushUser() {

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.white)
        val stream: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        file = ParseFile("default_image.png", bytes)
        try {
            file.save()
            signUp()
        } catch (e: Exception) {
            toast(e.message.toString())
        }

    }

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun logIn() {
        try {
            ParseUser.logIn(username.text.toString(), password.text.toString())
            val i = Intent(this, MainList::class.java)
            startActivity(i)
        } catch (e: Exception) {
            toast(e.message.toString())
        }
    }

    fun signUp() {
        val user: ParseUser = ParseUser()
        user.username = username.text.toString()
        user.setPassword(password.text.toString())
        user.put("followers", 0)
        user.put("background", file)
        user.signUpInBackground {
            if (it == null) {
                toast("Successful")
            } else {
                toast(it.message.toString())
            }
        }
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
            val animationS: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
            btn_center.startAnimation(animationS)
            if (mLoginMode) {
                logIn()
            } else {
                pushUser()
            }

        }

        //hide keyboard
        rl_main.setOnClickListener {
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        }
    }
}
