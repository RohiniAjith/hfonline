package com.healthyfish.healthyfish.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.healthyfish.healthyfish.R;

/**
 * Created by User on 25-09-2016.
 */
public class SetBagdeCount {
    static  LayerDrawable icon_m;
    static BadgeDrawable badge;
    static  String str_count="0";
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {



        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        if (str_count.equals("0"))
        badge.setCount(count);
        else
        badge.setCount(str_count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
        icon_m=icon;
    }
    public static void setBadgeCount( String count) {

//        BadgeDrawable badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon_m.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgeDrawable) {
//            badge = (BadgeDrawable) reuse;
//        } else {
//            badge = new BadgeDrawable(context);
//        }
//
        str_count=count;
        System.out.println("BadgeCount:"+str_count);
        if (str_count.equals("0"))
            str_count="";
        badge.setCount(str_count);
        icon_m.mutate();
        icon_m.setDrawableByLayerId(R.id.ic_badge, badge);

    }

}
