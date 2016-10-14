package com.healthyfish.healthyfish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.healthyfish.healthyfish.activity.DashBoardActivity;
import com.healthyfish.healthyfish.activity.LoginActivity;
import com.healthyfish.healthyfish.activity.RegistrationActivity;
import com.healthyfish.healthyfish.activity.SmsRegistrationActivity;
import com.healthyfish.healthyfish.manager.PrefManager;

public class SplashScreen extends AppCompatActivity {
    ProgressBar pb;
    int progressStatus = 0;
    Activity activity;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        activity=SplashScreen.this;
        pb.getProgressDrawable().setColorFilter(Color.rgb(23, 170, 159), PorterDuff.Mode.SRC_IN);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100)
                {
                    progressStatus += 5;
                    handler.post(new Runnable()
                    {
                        public void run()
                        {
                            pb.setProgress(progressStatus);

                        }
                    });
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (progressStatus==100)
                {
//                    ConnectionDetector cd= new ConnectionDetector(SplashScreen.this);
//                    if(cd.isConnectingToInternet())
//                    {
                    if (new PrefManager(SplashScreen.this).receivedSession().equals("0"))
                    {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        SplashScreen.this.finish();
                    }
                    else
                    {
                        Intent i = new Intent(SplashScreen.this, DashBoardActivity.class);
                        startActivity(i);
                        SplashScreen.this.finish();
                    }

                    //      }
//                    else
//                    {
//                       cd. showAlertDialog(SplashScreen.this, "No Internet Connection",
//                                "You don't have internet connection.",activity);
//                    }
                }
            }
        }).start();

    }
    }
