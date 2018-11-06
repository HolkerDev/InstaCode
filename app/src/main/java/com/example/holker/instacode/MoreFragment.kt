package com.example.holker.instacode

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.more_fragment.*
import java.io.ByteArrayOutputStream

class MoreFragment : Fragment() {

    lateinit var file: ParseFile

    fun toast(string: String?) {
        Toast.makeText(activity!!.applicationContext, string, Toast.LENGTH_LONG).show()
    }


    fun updateBackground() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, 2)
    }

    override fun onStart() {
        super.onStart()
        btn_fragment_update.setOnClickListener {
            val animationButton = AnimationUtils.loadAnimation(context, R.anim.bounce)
            btn_fragment_update.animation = animationButton
            if (checkSelfPermission(activity!!.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                updateBackground()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val img: Uri = data.data as Uri

            val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, img)

            val stream = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val bytes = stream.toByteArray()

            file = ParseFile("somenewimage.png", bytes)

            file.saveInBackground(SaveCallback {
                if (it == null) {
                    val user = ParseUser.getCurrentUser()
                    user.put("background", file)
                    user.saveInBackground()
                    toast("File saved successfully")
                } else {
                    toast(it.message.toString())
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.more_fragment, null)
    }
}