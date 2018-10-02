package com.example.holker.instacode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    TextView mTextViewSwitch;
    boolean mLoginMode = true;
    Button mButtonCenter;
    EditText mUsername;
    EditText mPassword;

    public void changeLog(View view) {
        if (mLoginMode) {
            mButtonCenter.setText("Sign up");
            mLoginMode = !mLoginMode;
            mTextViewSwitch.setText("Or Log In");
        } else {
            mButtonCenter.setText("Log In");
            mLoginMode = !mLoginMode;
            mTextViewSwitch.setText("Or Sign Up");
        }
    }

    public void centerClicked(View view) {
        if (!mUsername.getText().toString().equals("") || !mPassword.getText().toString().equals("")) {
            if (mLoginMode) {
                LogIn();
            } else {
                SignUp();
            }
        } else {
            Toast.makeText(getApplicationContext(), "username/password is empty",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void LogIn() {
        ParseUser.logInInBackground(mUsername.getText().toString(), mPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    GoToUserList();
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GoToUserList() {
        Intent i = new Intent(getApplicationContext(), MainList.class);
        startActivity(i);
    }

    public void SignUp() {
        ParseUser user = new ParseUser();
        user.setUsername(mUsername.getText().toString());
        user.setPassword(mPassword.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void hideKey(View view) {
        InputMethodManager inputMethod = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            centerClicked(view);
            return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find
        mTextViewSwitch = (TextView) findViewById(R.id.tv_switch);
        mButtonCenter = (Button) findViewById(R.id.btn_center);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);


        if (ParseUser.getCurrentSessionToken() != null) {
            GoToUserList();
        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }


}
