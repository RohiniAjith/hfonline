package com.healthyfish.healthyfish.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.healthyfish.healthyfish.activity.RegistrationActivity;
import com.healthyfish.healthyfish.activity.SmsRegistrationActivity;
import com.healthyfish.healthyfish.manager.Config;


/**
 * Created by RohiniAjith on 6/11/2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    private String TAG = SmsReceiver.class.getSimpleName();

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent
        final Bundle bundle = intent.getExtras();
//        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(Config.SMS_ORIGIN.toLowerCase())) {
                        System.out.println("rchd otp if");
                        return;
                    }

                    // verification code from sms
                   String verificationCode = getVerificationCode(message);
                    Toast.makeText(context,"Your verification code is:"+verificationCode, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "OTP received: " + message);
                    try {
                        SmsRegistrationActivity .getInstace().updateTheTextView(verificationCode);
                    } catch (Exception e) {

                    }
                    //   registrationActivity.recivedSms(verificationCode);
                    System.out.println("rchd otp ls");
                }
            }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
      // String str= message.replaceAll("[^0-9]", "");
         code=message.substring(0,6);
    // int index = str.indexOf(Config.OTP_DELIMITER);
//        int index=Integer.parseInt(str);
//        if (index != -1) {
//            int start = index + 2;
//            int length = 6;
//            code = message.substring(start, start + length);
//            return code;
//        }

        return code;
    }

}

