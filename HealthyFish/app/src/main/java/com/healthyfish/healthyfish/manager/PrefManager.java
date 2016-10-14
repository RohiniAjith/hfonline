package com.healthyfish.healthyfish.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by RohiniAjith on 6/11/2016.
 */
public class PrefManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "HEALTYFISH";

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_SESSION_ID = "session";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_OTP = "otp";
    private static final String KEY_SHOP_CART_COUNT = "shop_cart_count";
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_MOBILE_NUMBER, null);
    }

    public void createLogin() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
    public void createOTP(String otp) {
        editor.putString(KEY_OTP, otp);
        editor.commit();
    }
    public String receivedOtp(){
    return pref.getString(KEY_OTP,"");
    }
    public void createSession(String id) {
        editor.putString(KEY_SESSION_ID, id);
        editor.commit();
    }
    public String receivedSession(){
        return pref.getString(KEY_SESSION_ID,"0");
    }
    public String getScreenSize()
    {
        // If value for key not exist then return second param value - In this case null

        return pref.getString(QuickstartPreferences.STORE_SCREEN_SIZE,null);         // getting string

    }
    public String getDisplaySize()
    {
        // If value for key not exist then return second param value - In this case null

        return pref.getString(QuickstartPreferences.STORE_DISPLAY_SIZE,null);         // getting string

    }
    public int getShopCartCount(){
        return pref.getInt(KEY_OTP,1);
    }
    public void createShopCartCount(int count) {
        editor.putInt(KEY_SHOP_CART_COUNT, count);
        editor.commit();
    }
    public void logout(){

        editor.clear();
        editor.commit();
    }

}
