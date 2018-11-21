package com.example.holker.instacode.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.holker.instacode.R;
import com.example.holker.instacode.activities.MainActivityKt;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Settings extends AppCompatActivity {
    Button mButtonLogOut;
    Button mButtonUpdateBackground;
    AnimationDrawable mAnimationDrawable;
    ParseFile file;
    RelativeLayout mRelativeLayoutSettings;

    public void updateBackground() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);
    }

    public void animateLayout() {
        mAnimationDrawable = (AnimationDrawable) mRelativeLayoutSettings.getBackground();
        mAnimationDrawable.setEnterFadeDuration(3500);
        mAnimationDrawable.setExitFadeDuration(3500);
        mAnimationDrawable.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //find
        mButtonLogOut = (Button) findViewById(R.id.btn_logOut);
        mButtonUpdateBackground = (Button) findViewById(R.id.btn_update);
        mRelativeLayoutSettings = (RelativeLayout) findViewById(R.id.rv_settings);

        //Animation
        animateLayout();

        //Update background image
        mButtonUpdateBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                mButtonUpdateBackground.startAnimation(btnAnimation);

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    updateBackground();
                }
            }
        });

        //Log Out
        mButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                mButtonLogOut.startAnimation(btnAnimation);
                ParseUser.logOut();
                Intent toLogScreen = new Intent(getApplicationContext(), MainActivityKt.class);
                startActivity(toLogScreen);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] bytes = stream.toByteArray();

                file = new ParseFile("somenewimage.png", bytes);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        ParseUser user = ParseUser.getCurrentUser();
                        Toast.makeText(getApplicationContext(), user.getUsername(), Toast.LENGTH_LONG).show();
                        user.put("background", file);

                        user.saveInBackground();
                    }
                });

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
