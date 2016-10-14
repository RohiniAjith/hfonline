package com.healthyfish.healthyfish.dialogbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.healthyfish.healthyfish.manager.ConnectionDetector;


/**
 * Created by RohiniAjith on 4/27/2016.
 */
public class AlertBox {
public Context context;
ConnectionDetector cd;

    public  AlertBox(Context context)
    {
        this.context=context;
        cd= new ConnectionDetector(context);

    }

    public void alertBox_rateus(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle("Rate HealthyFish");


        builder.setMessage("Like using HealthyFish,please take a moment to rate it. Thanks for your support");


        //Button One : Yes
        builder.setPositiveButton("Rate It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(cd.isConnectingToInternet()) {
                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                    }
                }
                else
                {
                    Toast.makeText(context,"Sorry you have no internet connect. please connect your intertnet", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            }
        });


        //Button Two : No
        builder.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        //Button Three : Neutral
        builder.setNeutralButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog diag = builder.create();
        diag.show();
    }

    public void alertBox_Nonet(final Activity activity){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle("No InternetConnection");


        builder.setMessage("Do you want to connect your internet???");


        //Button One : Yes
        builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                activity.finish();
            }
        });




        //Button Three : Neutral
        builder.setNeutralButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();
            }
        });


        AlertDialog diag = builder.create();
        diag.show();
    }
    public void alertBox_Nonet(Context cont){

        AlertDialog.Builder builder = new AlertDialog.Builder(cont);


        builder.setTitle("No InternetConnection");


        builder.setMessage("Do you want to connect your internet???");


        //Button One : Yes
        builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

            }
        });




        //Button Three : Neutral
        builder.setNeutralButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
               System.exit(0);
            }
        });


        AlertDialog diag = builder.create();
        diag.show();
    }

    public void alertSuccessShopCart(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Successfully added to cart")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
