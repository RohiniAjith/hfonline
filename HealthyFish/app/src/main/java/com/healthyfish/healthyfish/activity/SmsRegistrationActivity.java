package com.healthyfish.healthyfish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.manager.PrefManager;
import com.healthyfish.healthyfish.pager.OtpViewPager;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 11-09-2016.
 */
public class SmsRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = SmsRegistrationActivity.class.getSimpleName();
    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    private OtpViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    public EditText inputName, inputEmail, inputMobile, inputOtp,inputPassword,inputConfirmPassword,inputLocation;
    private ProgressBar progressBar;
    private PrefManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;
    private static SmsRegistrationActivity ins;
    Toolbar toolbar_registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ins = this;
        setContentView(R.layout.activity_registration_two);
        toolbar_registration=(Toolbar)findViewById(R.id.toolbar_registration);
        toolbar_registration.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(toolbar_registration);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (OtpViewPager) findViewById(R.id.agft_viewPagerVertical);
        inputName = (EditText) findViewById(R.id.agft_inputName);
        inputEmail = (EditText) findViewById(R.id.agft_inputEmail);
        inputMobile = (EditText) findViewById(R.id.agft_inputMobile);
        inputOtp = (EditText) findViewById(R.id.agft_inputOtp);
        inputPassword=(EditText) findViewById(R.id.agft_inputPassword);
//        inputConfirmPassword=(EditText)findViewById(R.id.agft_inputConfirmPassword);
//        inputLocation=(EditText) findViewById(R.id.agft_inputLocation);
        btnRequestSms = (Button) findViewById(R.id.agft_btn_request_sms);
        btnVerifyOtp = (Button) findViewById(R.id.agft_btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.agft_progressBar);
        btnEditMobile = (ImageButton) findViewById(R.id.agft_btn_edit_mobile);
        txtEditMobile = (TextView) findViewById(R.id.agft_txt_edit_mobile);
        layoutEditMobile = (LinearLayout) findViewById(R.id.agft_layout_edit_mobile);

        // view click listeners
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManager(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(SmsRegistrationActivity.this, RegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (pref.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            layoutEditMobile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.agft_btn_request_sms:
                validateForm();
                break;

            case R.id.agft_btn_verify_otp:
                verifyOtp();
                break;

            case R.id.agft_btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;
        }
    }
    public boolean checkPassWordAndConfirmPassword(String password,String confirmPassword)
    {
        boolean pstatus = false;
        if (confirmPassword != null && password != null)
        {
            if (password.equals(confirmPassword))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }
    /**
     * Validating user details form
     */
    private void validateForm() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String password= inputPassword.getText().toString().trim();
        //String confirmpassword= inputConfirmPassword.getText().toString().trim();
       // String location=inputLocation.getText().toString().trim();
        // validating empty name and email
        if (name.length() == 0 || email.length() == 0||password.length()==0) {//||confirmpassword.length()==0||location.length()==0
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(!checkPassWordAndConfirmPassword(password,confirmpassword)){
//            Toast.makeText(getApplicationContext(), "Password Should match", Toast.LENGTH_SHORT).show();
//
//        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar.setVisibility(View.VISIBLE);

//            // saving the mobile number in shared preferences
//            pref.setMobileNumber(mobile);
//            String otp= null;
//            try {
//                otp = otpGenerationRandom();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            // requesting for sms
            requestForSMS(name, email, mobile,password,"Kottayam");

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
    }
   public String otpGenerationRandom() throws Exception
    {
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());

        int num = generator.nextInt(99999) + 99999;
        if (num < 100000 || num > 999999) {
            num = generator.nextInt(99999) + 99999;
            if (num < 100000 || num > 999999) {
                throw new Exception("Unable to generate PIN at this time..");
            }
        }
       String val= String.valueOf(num);
        System.out.println("OTP: "+val);
        return val;
    }
    /**
     * Method initiates the SMS request on the server
     *
     * @param name   user name
     * @param email  user email address
     * @param mobile user valid mobile number
     */
    private void requestForSMS(final String name, final String email, final String mobile,final String password,final String location) {
        //    loading.dismiss();
        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        HealthyFishApi api = adapter.create(HealthyFishApi.class);
        api.registrationRequest(name,email,mobile,password,location,  //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        System.out.print("Response got from server::" + response);
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;
                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            System.out.print("Result Exp is IO ::" + e);
                        }
                        try {
                            System.out.println("Result is:: " + output);
                            JSONObject jobj = new JSONObject(output);
                            System.out.print("Result from Home JOBJ IS::: " + jobj);
                            String response_code=jobj.getString("response_code");
                            String response_string=jobj.getString("response_string");
                            if (response_code.equals("200")&&response_string.equals("success"))
                            {
                                String response_otp=jobj.getString("response_otp");
                                pref.setIsWaitingForSms(true);
                                // boolean flag saying device is waiting for sms
                                pref.setIsWaitingForSms(true);
                                pref.createOTP(response_otp);
                                // moving the screen to next pager item i.e otp screen
                                viewPager.setCurrentItem(1);
                                txtEditMobile.setText(mobile);
                                layoutEditMobile.setVisibility(View.VISIBLE);

                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                String reason=jobj.getString("reason");
                                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();

                            }


                    // hiding the progress bar
                    progressBar.setVisibility(View.GONE);


                        } catch (Exception e) {
                            System.out.print("Result Exp iss" + e);
                            progressBar.setVisibility(View.GONE);
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        Toast.makeText(getApplicationContext(), "valid", Toast.LENGTH_SHORT).show();
        String otp = inputOtp.getText().toString().trim();
        String received_otp=pref.receivedOtp();
        if (!otp.isEmpty()&&!received_otp.isEmpty()) {
            if(otp.equals(received_otp))
            {
              Intent in= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
                SmsRegistrationActivity.this.finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Otp is not matching", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    System.out.println("layout 0");
                    resId = R.id.agft_layout_sms;
                    break;
                case 1:
                    System.out.println("layout 1");
                    resId = R.id.agft_layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }

    public static SmsRegistrationActivity  getInstace(){
        return ins;
    }

    public void updateTheTextView(final String t) {
        SmsRegistrationActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                inputOtp.setText(t);
            }
        });
    }
}