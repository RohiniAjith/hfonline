package com.healthyfish.healthyfish.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.ReciepeAdapter;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.dialogbox.CustomProgressDialog;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.manager.ConnectionDetector;
import com.healthyfish.healthyfish.model.RecipeListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 13-10-2016.
 */
public class RecipeFragment  extends Fragment {
    private RecyclerView mRecyclerView;
    private ReciepeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static final String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    private List<RecipeListItem> recipeListItems;
    Handler handler;
    ConnectionDetector cd;
    int page_num,totalpages,sam;
    ProgressDialog loading;
    TextView iv_NoNet;
    SwipeRefreshLayout swipeRefreshLayout;
    CustomProgressDialog customProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_recylerview, container, false);
       String cat_title= getArguments().getString("title");
        getActivity().setTitle(cat_title);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        iv_NoNet=(TextView)rootView.findViewById(R.id.iv_nonet);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        recipeListItems = new ArrayList<RecipeListItem>();
        handler = new Handler();
        page_num=1;
        cd= new ConnectionDetector(getActivity());
        customProgressDialog= new CustomProgressDialog(getActivity());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();

            }
        });


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new ReciepeAdapter(recipeListItems, mRecyclerView,getActivity());

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        if(cd.isConnectingToInternet())
        {
            swipeRefreshLayout.setEnabled(false);
            iv_NoNet.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            //While the app fetched data we are displaying a progress dialog
            customProgressDialog.showDialog();
            getFirstData();

        }
        else
        {
            System.out.println("No net");
            mRecyclerView.setVisibility(View.GONE);
            iv_NoNet.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setEnabled(true);
        }
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                recipeListItems.add(null);
                mAdapter.notifyItemInserted(recipeListItems.size() - 1);
                page_num = page_num + 1;
                System.out.println("Inside b4 if total pages: "+totalpages+"current page:"+page_num);

                System.out.println("Inside if Total pages:"+totalpages);
                if(totalpages>=page_num){
                    getDashBorard(page_num);}
                else {
                    recipeListItems.remove(recipeListItems.size() - 1);
                    mAdapter.notifyItemRemoved(recipeListItems.size());
                }
            }
        });


        return rootView;
    }

    void refreshItems() {
        // Load items
        // ...
        if(cd.isConnectingToInternet()) {

            swipeRefreshLayout.setEnabled(false);
            iv_NoNet.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            getDashBorard(page_num);
            // Load complete
        }
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation

        swipeRefreshLayout.setRefreshing(false);
    }
    public void  getFirstData(){

        getDashBorard(page_num);
    }

    public void getDashBorard(final int pagenumber) {

        sam=pagenumber;



        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        HealthyFishApi api = adapter.create(HealthyFishApi.class);

        //Defining the method
        if (getActivity()!=null) {
            api.recipeRequest(String.valueOf(pagenumber), "12","12",  //Creating an anonymous callback
                    new Callback<Response>() {
                        @Override
                        public void success(Response result, Response response) {
                            System.out.print("Response got from server::" + response);
                            //On success we will read the server's output using bufferedreader
                            //Creating a bufferedreader object
                            BufferedReader reader = null;
                            customProgressDialog.dismissDialog();
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
                                String response_code = jobj.getString("response_code");
                                String response_msg = jobj.getString("response_string");
                                if (response_code.equals("200") && response_msg.equals("success")) {
                                    totalpages = jobj.getInt("total_pages");
                                    System.out.println("totalpages:: " + totalpages);

                                    JSONArray jsonArray = jobj.getJSONArray("response");
                                    System.out.println("Json array is:: " + jsonArray);
                                    if (jsonArray.length() > 0) {
                                        if (pagenumber > 1) {
                                            recipeListItems.remove(recipeListItems.size() - 1);
                                            mAdapter.notifyItemRemoved(recipeListItems.size());
                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String thumb_images = jsonObject.getString("thumb_images");
                                            System.out.println("thumb_images " + thumb_images);
                                            String orginal_images = jsonObject.getString("images");
                                            System.out.println("orginal_images " + orginal_images);
                                            String name = jsonObject.getString("name");
                                            System.out.println("name " + name);
                                            String description = jsonObject.getString("content");
                                            System.out.println("description " + description);
                                            String id = jsonObject.getString("id");
                                            System.out.println("id " + id);//"currentpage: "+sam+"total pages: "+totalpages+"ID: "+
                                            RecipeListItem st = new RecipeListItem(id, name, description, thumb_images, orginal_images, getResources().getString(R.string.arrow_circle_right));
                                            recipeListItems.add(st);
                                            System.out.println("recipeListItems::: " + recipeListItems);

                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mAdapter.notifyItemInserted(recipeListItems.size());


                                                }
                                            });

                                            mAdapter.setLoaded();

                                        }
                                    }
                                } else if (response_code.equals("201") && response_msg.equals("failed")) {
                                    swipeRefreshLayout.setEnabled(true);
                                } else if (response_code.equals("202") && response_msg.equals("not_activated"))

                                {
                                    Toast.makeText(getActivity(), "Sorry, You are not an activated user", Toast.LENGTH_SHORT).show();

                                } else if (response_code.equals("202") && response_msg.equals("not_suppport_version")) {
                                    Toast.makeText(getActivity(), "please update Jaivakarshakan app", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (response_msg.equals(null))
                                        swipeRefreshLayout.setEnabled(true);
                                    else
                                        Toast.makeText(getActivity(), "error occurred " + response_msg, Toast.LENGTH_SHORT).show();

                                }


                                // mRecyclerView.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                System.out.print("Result Exp iss" + e);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            customProgressDialog.dismissDialog();
                            //   System.out.println("Retrofit error2 Hm::: " + error.getResponse().getStatus());
                            //  System.out.println("Retrofit error Hm::: " + error);
                            swipeRefreshLayout.setEnabled(true);
                        }
                    }
            );
        }
    }

}

