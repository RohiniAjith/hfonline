package com.healthyfish.healthyfish.dialogbox;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by RohiniAjith on 7/25/2016.
 */
public class CustomProgressDialog {
    ProgressDialog progress;
    Context context;
    public CustomProgressDialog(Context context) {
        progress = new ProgressDialog(context);
        this.context=context;
    }

    public void showDialog()
    {

        progress.setTitle("Loading");
        progress.setCanceledOnTouchOutside(true);
        progress.show();
    }
    public void showCartDialog()
    {
        progress.setCanceledOnTouchOutside(true);
        progress.show();
    }

    public void dismissDialog()
    {
        progress.dismiss();
    }
}
