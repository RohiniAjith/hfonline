package com.healthyfish.healthyfish.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.manager.CheckString;
import com.healthyfish.healthyfish.manager.PrefManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RohiniAjith on 6/12/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText ed_emailid_login,ed_pawword_login;
    Button b_login;
   /* b_facebook,b_twitter;*/
CheckString checkString;
    String s_emaild_login,s_password_login;
    Typeface font;
    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    private PrefManager pref;
    public Toolbar tolToolbar;
    private TextInputLayout inputLayout_Email;
    LinearLayout lnr_login_forget,lnr_login_newregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tolToolbar=(Toolbar)findViewById(R.id.toolbar_login);
        tolToolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(tolToolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_emailid_login=(EditText)findViewById(R.id.ed_login_email);
        inputLayout_Email = (TextInputLayout) findViewById(R.id.ti_login_email);
       // inputLayout_Email.setErrorEnabled(true);
      //  inputLayout_Email.setError("Enter your email id");
        ed_pawword_login=(EditText)findViewById(R.id.ed_login_password);
        b_login=(Button)findViewById(R.id.b_login_submit);
        lnr_login_newregister=(LinearLayout) findViewById(R.id.lnr_login_newregister);
//        b_facebook = (Button)findViewById( R.id.b_login_facebook);
//        b_twitter = (Button)findViewById( R.id.b_login_twitter);
        lnr_login_forget=(LinearLayout) findViewById(R.id.lnr_login_forget);
        font = Typeface.createFromAsset( getAssets(), "fonts/fontawesome-webfont.ttf" );
        pref= new PrefManager(LoginActivity.this);
//        b_twitter.setTypeface(font);
//        b_facebook.setTypeface(font);
        checkString= new CheckString();
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_emaild_login=ed_emailid_login.getText().toString();
                s_password_login=ed_pawword_login.getText().toString();
                if(checkString.isemptyString(s_emaild_login)||checkString.isNull(s_emaild_login)){
                    ed_emailid_login.setError("Enter email id");
                }
                else if(!checkString.isEmail(s_emaild_login))
                {
                    ed_emailid_login.setError("Enter a valid maild");
                }
                else if(checkString.isemptyString(s_password_login)||checkString.isNull(s_password_login))
                {
                    ed_pawword_login.setError("Enter Passord");
                }
                else
                {
                 doLogin(s_emaild_login,s_password_login);
                }
            }
        });
        lnr_login_newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
        lnr_login_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(i);
            }
        });
    }
    public void doLogin(String email,String password)
    {
        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        HealthyFishApi api = adapter.create(HealthyFishApi.class);
        api.login(email,password,  //Creating an anonymous callback
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
                                String session_id=jobj.getString("reg_id");

                                pref.createSession(session_id);

                                Intent in= new Intent(LoginActivity.this,DashBoardActivity.class);
                                startActivity(in);
                                LoginActivity.this.finish();

                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                String reason=jobj.getString("reason");
                                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();

                            }




                        } catch (Exception e) {
                            System.out.print("Result Exp iss" + e);

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
        );

    }


    private class LoginTextWatcher implements TextWatcher {

        private View view;

        private LoginTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.input_name:
//                    validateName();
//                    break;
//                case R.id.input_email:
//                    validateEmail();
//                    break;
//                case R.id.input_password:
//                    validatePassword();
//                    break;
//            }
        }
    }
}
