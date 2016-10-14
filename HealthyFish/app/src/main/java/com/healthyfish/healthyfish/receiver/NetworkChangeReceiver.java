package com.healthyfish.healthyfish.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.healthyfish.healthyfish.dialogbox.AlertBox;

import java.util.List;

/**
 * Created by User on 09-10-2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        boolean b=isOnline(context);
        if (!b)
        {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(context. ACTIVITY_SERVICE );
            List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            for(int i = 0; i < procInfos.size(); i++)
            {
                if(procInfos.get(i).processName.equals("com.healthyfish.healthyfish"))
                {
                   new AlertBox(context).alertBox_Nonet(context);
                }
            }
        }
//        final ConnectivityManager connMgr = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        final android.net.NetworkInfo wifi = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        final android.net.NetworkInfo mobile = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//        if (wifi.isAvailable() || mobile.isAvailable()) {
//            // Do something
//
//            Log.d("Network Available ", "Flag No 1");
//        }
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
