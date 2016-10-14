//package com.healthyfish.healthyfish.fragments;
//
//import android.app.ProgressDialog;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.healthyfish.healthyfish.R;
//import com.healthyfish.healthyfish.adapter.CustomAdapter;
//import com.healthyfish.healthyfish.adapter.CustomSpinnerAdapter;
//
//import com.healthyfish.healthyfish.adapter.SampleHomeHeaderAdapter;
//import com.healthyfish.healthyfish.api.Url;
//import com.healthyfish.healthyfish.dialogbox.CustomProgressDialog;
//import com.healthyfish.healthyfish.iterface.HealthyFishApi;
//import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
//import com.healthyfish.healthyfish.manager.ConnectionDetector;
//import com.healthyfish.healthyfish.model.DashboardListItem;
//import com.healthyfish.healthyfish.model.HomeHeaderListItem;
//import com.healthyfish.healthyfish.model.SpinnerModel;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
///**
// * Created by User on 18-09-2016.
// */
//public class  Sample_HomeHonder_Fragment extends Fragment {
//    private RecyclerView mRecyclerView;
//    private SampleHomeHeaderAdapter mAdapter;
//    private LinearLayoutManager mLayoutManager;
//    public static final String ROOT_URL = new Url().base_link;
//    private List<HomeHeaderListItem> dashboardListItems;
//    Handler handler;
//    ConnectionDetector cd;
//    int page_num,totalpages,sam;
//    ProgressDialog loading;
//    TextView iv_NoNet;
//    SwipeRefreshLayout swipeRefreshLayout;
//    String cat_id;
//    String title;
//    CustomSpinnerAdapter customspadapter;
//    public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();
//    CustomProgressDialog customProgressDialog;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//
//        cat_id= getArguments().getString("id");
//        title=getArguments().getString("title");
//        // getActivity().setTitle(title);
//        dashboardListItems = new ArrayList<HomeHeaderListItem>();
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//        iv_NoNet=(TextView)rootView.findViewById(R.id.iv_nonet);
//        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
//        handler = new Handler();
//        page_num=1;
//        getActivity().setTitle(title);
//        cd= new ConnectionDetector(getActivity());
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Refresh items
//                refreshItems();
//
//            }
//        });
//
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(getActivity());
//
//        // use a linear layout manager
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // create an Object for Adapter
//        mAdapter = new SampleHomeHeaderAdapter(dashboardListItems, mRecyclerView,getActivity());
//
//        // set the adapter object to the Recyclerview
//        mRecyclerView.setAdapter(mAdapter);
//
////        if (dashboardListItems.isEmpty()) {
////
////            mRecyclerView.setVisibility(View.GONE);
////
////        } else {
////            mRecyclerView.setVisibility(View.VISIBLE);
////
////        }
//
//
//        if(cd.isConnectingToInternet())
//        {
//            swipeRefreshLayout.setEnabled(false);
//            iv_NoNet.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//
//            //While the app fetched data we are displaying a progress dialog
//            customProgressDialog= new CustomProgressDialog(getActivity());
//            customProgressDialog.showDialog();
//            getFirstData();
//
//        }
//        else
//        {
//            mRecyclerView.setVisibility(View.GONE);
//            iv_NoNet.setVisibility(View.VISIBLE);
//            swipeRefreshLayout.setEnabled(true);
//        }
//        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //add null , so the adapter will check view_type and show progress bar at bottom
//                dashboardListItems.add(null);
//                mAdapter.notifyItemInserted(dashboardListItems.size() - 1);
//                page_num = page_num + 1;
//                if(totalpages>page_num){
//                    getDashBorard(page_num);}
//                else {
//                    dashboardListItems.remove(dashboardListItems.size() - 1);
//                    mAdapter.notifyItemRemoved(dashboardListItems.size());
//                }
//
//            }
//        });
//
//
//        return rootView;
//    }
//
//    void refreshItems() {
//        // Load items
//        // ...
//        if(cd.isConnectingToInternet()) {
//
//            swipeRefreshLayout.setEnabled(false);
//            iv_NoNet.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//            getDashBorard(page_num);
//            // Load complete
//        }
//        onItemsLoadComplete();
//    }
//
//    void onItemsLoadComplete() {
//        // Update the adapter and notify data set changed
//        // ...
//
//        // Stop refresh animation
//
//        swipeRefreshLayout.setRefreshing(false);
//    }
//    public void  getFirstData(){
//        getDashBorard(page_num);
//    }
//
//    public void getDashBorard(final int pagenumber) {
//
//        sam=pagenumber;
//
//
//
//        //Creating a rest adapter
//        RestAdapter adapter = new RestAdapter.Builder()
//                .setEndpoint(new Url().base_link)
//                .build();
//
//        //Creating an object of our api interface
//        HealthyFishApi api = adapter.create(HealthyFishApi.class);
//
//
//        //Defining the method
//        api.homeheader(String.valueOf(pagenumber),"12", "1",  //Creating an anonymous callback
//                new Callback<Response>() {
//                    @Override
//                    public void success(Response result, Response response) {
//                        customProgressDialog.dismissDialog();
//                        //On success we will read the server's output using bufferedreader
//                        //Creating a bufferedreader object
//                        BufferedReader reader = null;
//
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
//                            System.out.print("Result JOBJ IS::: " + jobj);
//                            String response_code = jobj.getString("response_code");
//                            String response_msg = jobj.getString("response_string");
//                            if(response_code.equals("200")&&response_msg.equals("success")) {
//                                totalpages = jobj.getInt("total_pages");
//                                System.out.println("totalpages:: " + totalpages);
//                                JSONArray jsonArray = jobj.getJSONArray("response");
//                                System.out.println("Json array is:: " + jsonArray);
//                                if (jsonArray.length() > 0) {
//                                    if (pagenumber > 1) {
//                                        dashboardListItems.remove(dashboardListItems.size() - 1);
//                                        mAdapter.notifyItemRemoved(dashboardListItems.size());
//                                    }
//                                    setListData();
//                                    Resources res = getActivity().getResources();
//
//                                    // Create custom adapter object ( see below CustomAdapter.java )
//                                    customspadapter = new CustomSpinnerAdapter(getActivity(), R.layout.popupspinner, CustomListViewValuesArr,res);
//
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                        //String thumb_images = jsonObject.getString("thumb_images");
//                                      //  System.out.println("thumb_images " + thumb_images);
//                                      //  String orginal_images = jsonObject.getString("images");
//                                        String product_image = jsonObject.getString("product_image");
//                                        String mobile_title = jsonObject.getString("mobile_title");
//                                        String malayalam_title = jsonObject.getString("malayalam_title");
//                                        System.out.println("name " + mobile_title);
//                                        String offer_price = jsonObject.getString("offer_price");
//                                        String actual_price = jsonObject.getString("actual_price");
//                                        String prod_id = jsonObject.getString("prod_id");
//                                        System.out.println("id " + prod_id);//"currentpage: "+sam+"total pages: "+totalpages+"ID: "+
//                                        HomeHeaderListItem st = new HomeHeaderListItem(prod_id, mobile_title, malayalam_title,offer_price, actual_price,product_image);
//                                        dashboardListItems.add(st);
//                                        System.out.println("dashboardListItems::: " + dashboardListItems);
//                                        //    mAdapter.notifyItemInserted(dashboardListItems.size());
//
//
//                                        handler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mAdapter.notifyItemInserted(dashboardListItems.size());
//
//
//                                            }
//                                        });
//
//                                        mAdapter.setLoaded();
//
//                                    }
//                                }
//                            }
//                            else if(response_code.equals("201")&& response_msg.equals("failed"))
//                            {
//
//                                System.out.println("No data to display");
//                                iv_NoNet.setText("No Data To Display.\n Kindly help us to improve. \n mail to billbionbeatsinfotech@gmail.com");
//                                iv_NoNet.setVisibility(View.VISIBLE);
//                                //  swipeRefreshLayout.setEnabled(true);
//                            }
//                            else if(response_code.equals("202")&& response_msg.equals("not_activated"))
//
//                            {
//
//                                Toast.makeText(getActivity(),"Sorry, You are not an activated user",Toast.LENGTH_SHORT).show();
//
//                            }
//                            else if(response_code.equals("202")&& response_msg.equals("not_suppport_version"))
//                            {
//
//                                Toast.makeText(getActivity(),"please update Jaivakarshakan app",Toast.LENGTH_SHORT).show();
//
//                            }
//                            else
//                            {
//                                if(response_msg.equals(null))
//                                    swipeRefreshLayout.setEnabled(true);
//                                else
//                                    Toast.makeText(getActivity(),response_msg,Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            // mRecyclerView.setVisibility(View.VISIBLE);
//                        } catch (JSONException e) {
//                            System.out.print("Result Exp iss" + e);
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        customProgressDialog.dismissDialog();
//                        System.out.println("Retrofit error::: " + error);
//                        //If any error occured displaying the error as toast
////                        mRecyclerView.setVisibility(View.GONE);
////                        iv_NoNet.setVisibility(View.VISIBLE);
//                        swipeRefreshLayout.setEnabled(true);
//                        //  Toast.makeText(getActivity(), ""+error, Toast.LENGTH_LONG).show();
//
//                    }
//                }
//        );
//
//
//
//    }
//    public void setListData()
//    {
//
//        // Now i have taken static values by loop.
//        // For further inhancement we can take data by webservice / json / xml;
//
//        for (int i = 0; i < 11; i++) {
//
//            final SpinnerModel sched = new SpinnerModel();
//
//            /******* Firstly take data in model object ******/
//            sched.setTv_text("Skinless  "+i);
//
//            /******** Take Model Object in ArrayList **********/
//            CustomListViewValuesArr.add(sched);
//        }
//
//    }
//}
