package com.healthyfish.healthyfish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.ShopingCartAdapter;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.dialogbox.CustomProgressDialog;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.manager.ConnectionDetector;
import com.healthyfish.healthyfish.manager.PrefManager;
import com.healthyfish.healthyfish.model.DashboardListItem;
import com.healthyfish.healthyfish.model.HomeListItem;
import com.healthyfish.healthyfish.model.ShopingCartListItem;

import org.json.JSONArray;
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
 * Created by User on 22-09-2016.
 */
public class ShopingCartActivity extends AppCompatActivity {
Toolbar tolToolbar;
    private RecyclerView mRecyclerView;
    private ShopingCartAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ShopingCartListItem> dashboardListItems;
    Handler handler;
    RelativeLayout conformOrder,scr_rel_disp,scr_rel_nodata;
    ConnectionDetector cd;
    int  page_num=0,totalpages;
    String sum_amount;
    CustomProgressDialog customProgressDialog;
    TextView scr_tv_total,scr_tv_no;
    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopingcart_recyclerview);
        tolToolbar=(Toolbar)findViewById(R.id.toolbar_shoping);
        tolToolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(tolToolbar);
        scr_rel_disp=(RelativeLayout)findViewById(R.id.scr_rel_disp);
        conformOrder=(RelativeLayout)findViewById(R.id.scr_rel_conform_order);
        scr_rel_nodata=(RelativeLayout)findViewById(R.id.scr_rel_nodata);
        scr_tv_total=(TextView)findViewById(R.id.scr_tv_total);
        scr_tv_no=(TextView)findViewById(R.id.scr_tv_no);
        getSupportActionBar().setTitle("Shopping Cart");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dashboardListItems = new ArrayList<ShopingCartListItem>();
        mRecyclerView = (RecyclerView) findViewById(R.id.scr_recylerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ShopingCartActivity.this);
        handler= new Handler();
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new ShopingCartAdapter(dashboardListItems, mRecyclerView,ShopingCartActivity.this);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

       // getFirstData();
        cd= new ConnectionDetector(ShopingCartActivity.this);
        if (cd.isConnectingToInternet()) {
            scr_rel_nodata.setVisibility(View.GONE);
            scr_rel_disp.setVisibility(View.VISIBLE);
            customProgressDialog= new CustomProgressDialog(ShopingCartActivity.this);
           customProgressDialog.showDialog();
            page_num=1;
            getShoppingCartData(page_num);
        }
        else
        {
            scr_rel_disp.setVisibility(View.GONE);
            scr_rel_nodata.setVisibility(View.VISIBLE);
            scr_tv_no.setText("No Internet Connection");
        }

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                dashboardListItems.add(null);
                mAdapter.notifyItemInserted(dashboardListItems.size() - 1);
                page_num = page_num + 1;
                if(totalpages>page_num) {
                    getShoppingCartData(page_num);
                }
                else {
                    dashboardListItems.remove(dashboardListItems.size() - 1);
                    mAdapter.notifyItemRemoved(dashboardListItems.size());
                }

            }
        });
        conformOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                ShopingCartActivity.this.finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
public void placeOrder(){
    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();

    //Creating an object of our api interface
    HealthyFishApi api = adapter.create(HealthyFishApi.class);

    //Defining the method

    api.placeorder(new PrefManager(getApplicationContext()).receivedSession(),  //Creating an anonymous callback
            new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {
                    System.out.println("Success");
                    System.out.println("Response got from server::" + response);
                    //On success we will read the server's output using bufferedreader
                    //Creating a bufferedreader object
                    BufferedReader reader = null;
                    //An string to store output from the server
                    String output = "";
                    //loading.dismiss();

                    try {
                        //Initializing buffered reader
                        reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                        //Reading the output in the string
                        output = reader.readLine();
                    } catch (IOException e) {
                        System.out.println("Result Exp is IO ::" + e);
                    }
                    try {
                        customProgressDialog.dismissDialog();
                        System.out.println("Result is:: " + output);
                        JSONObject jobj = new JSONObject(output);
                        System.out.println("Result from Home JOBJ IS::: " + jobj);
                        String response_code=jobj.getString("response_code");
                        String response_string=jobj.getString("response_string");
                        if (response_code.equals("200")&&response_string.equals("success"))
                        {
                            Intent in= new Intent(getApplicationContext(),BillingInformationActivity.class);
                            startActivity(in);
                        }
                        else if (response_code.equals("201")&&response_string.equals("empty"))
                        {
                            scr_rel_disp.setVisibility(View.GONE);
                            scr_rel_nodata.setVisibility(View.VISIBLE);
                            scr_tv_no.setText("No Cart to Display");
                        }
                        else
                        {
                            scr_rel_disp.setVisibility(View.GONE);
                            scr_rel_nodata.setVisibility(View.VISIBLE);
                            scr_tv_no.setText("Please try again");
                            Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        System.out.println("Exp:"+e);
                    }
                }
                @Override
                public void failure(RetrofitError error) {
                    System.out.println("Error"+error.getMessage());
                }
            }
    );
}
    public void getShoppingCartData(int page_num){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        HealthyFishApi api = adapter.create(HealthyFishApi.class);

        //Defining the method

            api.ReceiveShopCart(new PrefManager(getApplicationContext()).receivedSession(),String.valueOf(page_num),"5",  //Creating an anonymous callback
                    new Callback<Response>() {
                        @Override
                        public void success(Response result, Response response) {
                            System.out.println("Success");
                            System.out.println("Response got from server::" + response);
                            //On success we will read the server's output using bufferedreader
                            //Creating a bufferedreader object
                            BufferedReader reader = null;
                            //An string to store output from the server
                            String output = "";
                            //loading.dismiss();

                            try {
                                //Initializing buffered reader
                                reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                                //Reading the output in the string
                                output = reader.readLine();
                            } catch (IOException e) {
                                System.out.println("Result Exp is IO ::" + e);
                            }
                            try {
                                customProgressDialog.dismissDialog();
                                System.out.println("Result is:: " + output);
                                JSONObject jobj = new JSONObject(output);
                                System.out.println("Result from Home JOBJ IS::: " + jobj);
                                String response_code=jobj.getString("response_code");
                                String response_string=jobj.getString("response_string");
                                if (response_code.equals("200")&&response_string.equals("success"))
                                {
                                    totalpages=Integer.parseInt(jobj.getString("total_pages"));
                                    sum_amount=jobj.getString("sum_amount");
                                    JSONObject jobj_response=jobj.getJSONObject("response");
                                    scr_tv_total.setText("RS. "+sum_amount);
                                    JSONArray jsonArray_shoping = jobj_response.getJSONArray("cartdisplay");

                                    for (int i = 0; i < jsonArray_shoping.length(); i++) {
                                        System.out.println("Json array new arrival " + jsonArray_shoping.getString(i));
                                        JSONObject new_arrival_obj = jsonArray_shoping.getJSONObject(i);
                                        String prod_id = new_arrival_obj.getString("prod_id");
                                        String mobile_title = new_arrival_obj.getString("mobile_title");
                                        String malayalam_title = new_arrival_obj.getString("malayalam_title");
                                        String product_image = new_arrival_obj.getString("product_image");
                                        String offer_price = new_arrival_obj.getString("total_price");
                                        String quantity = new_arrival_obj.getString("quantity");

                                        ShopingCartListItem shopingCartListItem = new ShopingCartListItem(prod_id, mobile_title, malayalam_title, "Rs. " + offer_price, quantity, product_image);
                                        dashboardListItems.add(shopingCartListItem);


                                        System.out.println("dashboardListItems::: " + dashboardListItems);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.notifyItemInserted(dashboardListItems.size());


                                            }
                                        });

                                        mAdapter.setLoaded();

                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                                else if (response_code.equals("201")&&response_string.equals("empty"))
                                {
                                    scr_rel_disp.setVisibility(View.GONE);
                                    scr_rel_nodata.setVisibility(View.VISIBLE);
                                    scr_tv_no.setText("No Cart to Display");
                                }
                                else
                                {
                                    scr_rel_disp.setVisibility(View.GONE);
                                    scr_rel_nodata.setVisibility(View.VISIBLE);
                                    scr_tv_no.setText("Please try again");
                                    Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                System.out.println("Exp:"+e);
                            }
                        }
                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println("Error"+error.getMessage());
                            }
                        }
                        );
                    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }
}
