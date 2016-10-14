package com.healthyfish.healthyfish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.manager.CheckString;
import com.healthyfish.healthyfish.manager.PrefManager;

import java.util.HashMap;


/**
 * Created by RohiniAjith on 6/11/2016.
 */
public class RegistrationActivity extends AppCompatActivity {
    private PrefManager pref;
    private TextView name, email, mobile;
String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_one);


        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);



        pref = new PrefManager(getApplicationContext());


        // Checking if user session
        // if not logged in, take user to sms screen
        if (!pref.isLoggedIn()) {
            logout();
        }

        // Displaying user information from shared preferences
        /*HashMap<String, String> profile = pref.getUserDetails();
        name.setText("Name: " + profile.get("name"));
        email.setText("Email: " + profile.get("email"));
        mobile.setText("Mobile: " + profile.get("mobile"));*/
    }

    /**
     * Logging out user
     * will clear all user shared preferences and navigate to
     * sms activation screen
     */
    private void logout() {
        pref.clearSession();

        Intent intent = new Intent(RegistrationActivity.this, SmsRegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }


}