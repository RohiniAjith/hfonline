package com.healthyfish.healthyfish.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.manager.ConnectionDetector;
import com.healthyfish.healthyfish.manager.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
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
public class Cancellation extends Fragment {
    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    ProgressDialog loading;
    ConnectionDetector cd;
    TextView textView;
    String toolbar_title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =inflater.inflate(R.layout.fragment_cancelation, container, false);
        textView=(TextView)rootView.findViewById(R.id.fc_description);
        toolbar_title=getArguments().getString("category");
        getActivity().setTitle(toolbar_title);
        cd= new ConnectionDetector(getActivity());
        if(cd.isConnectingToInternet())
        {
            //swipeRefreshLayout.setEnabled(false);
//            iv_NoNet.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//            mRecyclerView1.setVisibility(View.VISIBLE);
//            mRecyclerView2.setVisibility(View.VISIBLE);
//            //While the app fetched data we are displaying a progress dialog
            loading  = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
//            mainlayout.setVisibility(View.GONE);
            getHome();
            //getDesignHome();

        }

        return rootView;
    }



    public void getHome()
    {
//    loading.dismiss();
        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        HealthyFishApi api = adapter.create(HealthyFishApi.class);

        //Defining the method
        if (getActivity()!=null) {
            api.otherRequest(new PrefManager(getActivity()).receivedSession(),"7",  //Creating an anonymous callback
                    new Callback<Response>() {
                        @Override
                        public void success(Response result, Response response) {
                            System.out.print("Response got from server::" + response);
                            //On success we will read the server's output using bufferedreader
                            //Creating a bufferedreader object
                            BufferedReader reader = null;
                            //An string to store output from the server
                            String output = "";
                            loading.dismiss();
                            //mainlayout.setVisibility(View.VISIBLE);
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
                                JSONArray jsonArraybanner=jobj.getJSONArray("response");
                                JSONObject jsonObject=  jsonArraybanner.getJSONObject(0);
                                String str=jsonObject.getString("description");
                                textView.setText(str);




                            } catch (JSONException e) {
                                System.out.print("Result Exp iss" + e);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    }
            );
        }

    }
}
