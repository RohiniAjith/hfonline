//package com.healthyfish.healthyfish.service;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.view.View;
//import android.widget.Toast;
//
//import com.healthyfish.healthyfish.activity.RegistrationActivity;
//import com.healthyfish.healthyfish.activity.SmsRegistrationActivity;
//import com.healthyfish.healthyfish.api.Url;
//import com.healthyfish.healthyfish.iterface.HealthyFishApi;
//import com.healthyfish.healthyfish.manager.PrefManager;
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
///**
// * Created by User on 12-09-2016.
// */
//public class SmsSendService extends IntentService {
//    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
//
//    private static String TAG = SmsSendService.class.getSimpleName();
//
//    public SmsSendService() {
//        super(SmsSendService.class.getSimpleName());
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            String otp = intent.getStringExtra("otp");
//            String id = intent.getStringExtra("id");
//            verifyOtp(otp,id);
//        }
//    }
//
//    /**
//     * Posting the OTP to server and activating the user
//     *
//     * @param otp otp received in the SMS
//     */
//    private void verifyOtp(final String otp,final String id) {
//        //Creating a rest adapter
//        RestAdapter adapter = new RestAdapter.Builder()
//                .setEndpoint(ROOT_URL)
//                .build();
//
//        //Creating an object of our api interface
//        HealthyFishApi api = adapter.create(HealthyFishApi.class);
//        api.otpSend(otp,id,  //Creating an anonymous callback
//                new Callback<Response>() {
//                    @Override
//                    public void success(Response result, Response response) {
//                        System.out.print("Response got from server::" + response);
//                        //On success we will read the server's output using bufferedreader
//                        //Creating a bufferedreader object
//                        BufferedReader reader = null;
//                        //An string to store output from the server
//                        String output = "";
//
//                        try {
//                            //Initializing buffered reader
//                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
//
//                            //Reading the output in the string
//                            output = reader.readLine();
//                        } catch (IOException e) {
//                            System.out.print("Result Exp is IO ::" + e);
//                        }
//                        try {
//                            System.out.println("Result is:: " + output);
//                            JSONObject jobj = new JSONObject(output);
//                            System.out.print("Result from Home JOBJ IS::: " + jobj);
//                            String response_code=jobj.getString("response_code");
//                            String response_string=jobj.getString("response_string");
//                            if (response_code.equals("200")&&response_string.equals("sucess"))
//                            {
//                                String session_id=jobj.getString("session_id");
//                                PrefManager pref = new PrefManager(getApplicationContext());
//                                pref.createLogin(session_id);
//
//                                Intent intent = new Intent(SmsSendService.this, RegistrationActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//
//                            }
//                            else
//                            {
//                                String reason=jobj.getString("reason");
//                                Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//
//
//
//                        } catch (Exception e) {
//                            System.out.print("Result Exp iss" + e);
//
//                        }
//
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//                }
//        );
//
////        StringRequest strReq = new StringRequest(Request.Method.POST,
////                Config.URL_VERIFY_OTP, new Response.Listener<String>() {
////
////            @Override
////            public void onResponse(String response) {
////                Log.d(TAG, response.toString());
////
////                try {
////
////                    JSONObject responseObj = new JSONObject(response);
////
////                    // Parsing json object response
////                    // response will be a json object
////                    boolean error = responseObj.getBoolean("error");
////                    String message = responseObj.getString("message");
////
////                    if (!error) {
////                        // parsing the user profile information
////                        JSONObject profileObj = responseObj.getJSONObject("profile");
////
////                        String name = profileObj.getString("name");
////                        String email = profileObj.getString("email");
////                        String mobile = profileObj.getString("mobile");
////
////                        PrefManager pref = new PrefManager(getApplicationContext());
////                        pref.createLogin(name, email, mobile);
////
////                        Intent intent = new Intent(HttpService.this, MainActivity.class);
////                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        startActivity(intent);
////
////                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
////
////                    } else {
////                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
////                    }
////
////                } catch (JSONException e) {
////                    Toast.makeText(getApplicationContext(),
////                            "Error: " + e.getMessage(),
////                            Toast.LENGTH_LONG).show();
////                }
////
////            }
////        }, new Response.ErrorListener() {
////
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Error: " + error.getMessage());
////                Toast.makeText(getApplicationContext(),
////                        error.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        }) {
////
////            @Override
////            protected Map<String, String> getParams() {
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("otp", otp);
////
////                Log.e(TAG, "Posting params: " + params.toString());
////                return params;
////            }
////
////        };
////
////        // Adding request to request queue
////        MyApplication.getInstance().addToRequestQueue(strReq);
//    }
//
//
//}
