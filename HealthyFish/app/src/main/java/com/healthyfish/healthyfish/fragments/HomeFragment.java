package com.healthyfish.healthyfish.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.billionbeatsinfotech.mylibrary.Animations.DescriptionAnimation;
import com.billionbeatsinfotech.mylibrary.SliderLayout;
import com.billionbeatsinfotech.mylibrary.SliderTypes.BaseSliderView;
import com.billionbeatsinfotech.mylibrary.SliderTypes.TextSliderView;
import com.billionbeatsinfotech.mylibrary.Tricks.ViewPagerEx;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.HomeAdapter;
import com.healthyfish.healthyfish.adapter.HomeBestSellerAdapter;
import com.healthyfish.healthyfish.adapter.HomeExpressAdapter;
import com.healthyfish.healthyfish.adapter.HomeImagesAdapter;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.dialogbox.AlertBox;
import com.healthyfish.healthyfish.dialogbox.CustomProgressDialog;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.manager.ConnectionDetector;
import com.healthyfish.healthyfish.manager.PrefManager;
import com.healthyfish.healthyfish.manager.SetBagdeCount;
import com.healthyfish.healthyfish.model.HomeExpressListItem;
import com.healthyfish.healthyfish.model.HomeImagesItem;
import com.healthyfish.healthyfish.model.HomeListBestSellerItem;
import com.healthyfish.healthyfish.model.HomeListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

;


/**
 * Created by RohiniAjith on 6/6/2016.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private RecyclerView mRecyclerView,mRecyclerView1,mRecyclerView2,exp_recyclerview;
    RelativeLayout rel_express,rel_new_arrival,rel_best_seller,rel_banner;
    private HomeAdapter homeAdapter;
    private HomeExpressAdapter homeExpressAdapter;
    HomeBestSellerAdapter homeAdapter1;
    HomeImagesAdapter homeAdapter2;
    private GridLayoutManager mLayoutManager,gridLayoutManager1,getGridLayoutManager2;
    private LinearLayoutManager linearLayoutManager;
    public  String ROOT_URL = new Url().base_link;//+new Url().contentlink;
    private List<HomeExpressListItem> dashboardListItems3;
    private List<HomeListItem> dashboardListItems;
    private List<HomeListBestSellerItem> dashboardListItems1;
    private List<HomeImagesItem> dashboardListItems2;
    List<Object>listobject;
    private PrefManager pref;
    Handler handler,handler1;
    ConnectionDetector cd;
    String shoping_count,toolbar_title;
    int page_num;
    CustomProgressDialog loading;
    TextView home_tv_best_seller_more_icon,home_tv_new_arrival_more_icon,home_tv_express_more_icon;
    RelativeLayout home_recycler_view_more,home_recycler_best_view_more,home_recycler_express_more;
    RelativeLayout relative_se,relative_se1,relative_se2,relative_se3;
   // SwipeRefreshLayout swipeRefreshLayout;
   private SliderLayout imageSlider;
    private static final String TAG = HomeFragment.class.getSimpleName();
    int footer_img_array[]={R.drawable.footer_one, R.drawable.footer_two, R.drawable.footer_three, R.drawable.footer_four};
    Typeface font;
    CoordinatorLayout mainlayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.homefragment, container, false);
        listobject=new ArrayList<>();
        dashboardListItems = new ArrayList<HomeListItem>();
        dashboardListItems1 = new ArrayList<HomeListBestSellerItem>();
        dashboardListItems2 = new ArrayList<HomeImagesItem>();
        dashboardListItems3=new ArrayList<HomeExpressListItem>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        mRecyclerView1 = (RecyclerView) rootView.findViewById(R.id.home_recycler_view_best_seller);
        mRecyclerView2 = (RecyclerView) rootView.findViewById(R.id.re_home_recycler_view_images);
        exp_recyclerview=(RecyclerView)rootView.findViewById(R.id.home_express_recycler_view);

        relative_se=(RelativeLayout)rootView.findViewById(R.id.relative_se);
        relative_se1=(RelativeLayout)rootView.findViewById(R.id.relative_se1);
        relative_se2=(RelativeLayout)rootView.findViewById(R.id.relative_se2);
        relative_se3=(RelativeLayout)rootView.findViewById(R.id.relative_se3);

        rel_best_seller=(RelativeLayout)rootView.findViewById(R.id.rel_home_recyclerview_best_seller);
        rel_express=(RelativeLayout)rootView.findViewById(R.id.hc_rel_exp);
        rel_new_arrival=(RelativeLayout) rootView.findViewById(R.id.hc_rel_new_arrival);
        rel_banner=(RelativeLayout)rootView.findViewById(R.id.rel_slider_iv);

        mainlayout=(CoordinatorLayout)rootView.findViewById(R.id.main_content);

        home_recycler_view_more=(RelativeLayout) rootView.findViewById(R.id.home_recycler_view_more);
        home_recycler_best_view_more=(RelativeLayout)rootView.findViewById(R.id.home_recycler_best_view_more);
        home_recycler_express_more=(RelativeLayout)rootView.findViewById(R.id.home_exp_recycler_view_more);

        home_tv_new_arrival_more_icon=(TextView)rootView.findViewById(R.id.home_tv_new_arrival_more_icon);
        home_tv_best_seller_more_icon=(TextView)rootView.findViewById(R.id.home_tv_best_seller_more_icon);
        home_tv_express_more_icon=(TextView)rootView.findViewById(R.id.home_exp_tv_new_arrival_more_icon);

        font = Typeface.createFromAsset( getActivity().getAssets(), "fonts/fontawesome-webfont.ttf" );
        home_tv_best_seller_more_icon.setTypeface(font);
        home_tv_new_arrival_more_icon.setTypeface(font);
        home_tv_express_more_icon.setTypeface(font);

        toolbar_title=getArguments().getString("category");
        getActivity().setTitle(toolbar_title);
//       // swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        handler = new Handler();
        handler1 = new Handler();
        pref= new PrefManager(getActivity());
        cd= new ConnectionDetector(getActivity());
        loading  = new CustomProgressDialog(getActivity());
        imageSlider = (SliderLayout)rootView.findViewById(R.id.slider);

        // for express recyclerview
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        exp_recyclerview.setHasFixedSize(false);
        exp_recyclerview.setNestedScrollingEnabled(false);
        getGridLayoutManager2 = new GridLayoutManager(getActivity(),2);
        // use a linear layout manager
        exp_recyclerview.setLayoutManager(getGridLayoutManager2);
        // create an Object for Adapter
        homeExpressAdapter = new HomeExpressAdapter(dashboardListItems3, exp_recyclerview,getActivity());
        // set the adapter object to the Recyclerview
        exp_recyclerview.setAdapter(homeExpressAdapter);



// for new arrival recyclerview
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // create an Object for Adapter
        homeAdapter = new HomeAdapter(dashboardListItems, mRecyclerView,getActivity());
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(homeAdapter);

// for best seller recyclerview
        // in content do not change the layout size of the RecyclerView
        mRecyclerView1.setHasFixedSize(false);
        mRecyclerView1.setNestedScrollingEnabled(false);
        gridLayoutManager1 = new GridLayoutManager(getActivity(),2);
        // use a linear layout manager
        mRecyclerView1.setLayoutManager(gridLayoutManager1);
        // create an Object for Adapter
        homeAdapter1 = new HomeBestSellerAdapter(dashboardListItems1, mRecyclerView1,getActivity());
        mRecyclerView1.setAdapter(homeAdapter1);


        linearLayoutManager= new LinearLayoutManager(getActivity());
        // use a linear layout manager
        mRecyclerView2.setLayoutManager(linearLayoutManager);
        mRecyclerView2.setNestedScrollingEnabled(false);
        // create an Object for Adapter
        homeAdapter2 = new HomeImagesAdapter(dashboardListItems2, mRecyclerView2,getActivity());

        // set the adapter object to the Recyclerview
        mRecyclerView2.setAdapter(homeAdapter2);

        if(cd.isConnectingToInternet())
        {
            mainlayout.setVisibility(View.GONE);
//            //While the app fetched data we are displaying a progress dialog
            loading.showDialog();

            getHome();

        }
        else
        {

            rel_new_arrival.setVisibility(View.GONE);
            rel_best_seller.setVisibility(View.GONE);
            rel_express.setVisibility(View.GONE);
            rel_banner.setVisibility(View.GONE);
            mainlayout.setVisibility(View.VISIBLE);
            AlertBox alertBox= new AlertBox(getActivity());
            alertBox.alertBox_Nonet(getActivity());
            //swipeRefreshLayout.setEnabled(true);
        }
        relative_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeHeader_Fragment homeHeader_fragment = new HomeHeader_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Crab");
                homeHeader_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, homeHeader_fragment).commit();

            }
        });
        relative_se1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeHeader_Fragment homeHeader_fragment = new HomeHeader_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Shrimps");
                homeHeader_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, homeHeader_fragment).commit();

            }
        });
        relative_se2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeHeader_Fragment homeHeader_fragment = new HomeHeader_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Fish");
                homeHeader_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, homeHeader_fragment).commit();

            }
        });
        relative_se3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeHeader_Fragment homeHeader_fragment = new HomeHeader_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Offer");
                homeHeader_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, homeHeader_fragment).commit();

            }
        });
        home_recycler_express_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                View_More_Fragment best_seller_fragment= new View_More_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Healthy Express");
                bundle.putString("listing_id", "3");
                best_seller_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container,best_seller_fragment).commit();
            }
        });
        home_recycler_best_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                View_More_Fragment best_seller_fragment= new View_More_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "Best Seller");
                bundle.putString("listing_id", "2");
                best_seller_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container,best_seller_fragment).commit();
            }
        });
        home_recycler_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                View_More_Fragment new_arrival_fragment= new View_More_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "New Arrival");
                bundle.putString("listing_id", "1");
                new_arrival_fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container,new_arrival_fragment).commit();
            }
        });
        return rootView;
    }


    public void getDashBorard2() {
        System.out.println("Footer footer_img_array_length: "+footer_img_array.length);
        for (int i = 0; i < footer_img_array.length; i++) {
            System.out.println("Footer Array I"+i);
            HomeImagesItem homeImagesItem= new HomeImagesItem(footer_img_array[i]);
            dashboardListItems2.add(homeImagesItem);
        }

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

public void getHome()
{
    //Creating a rest adapter
    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();

    //Creating an object of our api interface
    HealthyFishApi api = adapter.create(HealthyFishApi.class);

    //Defining the method
    if (getActivity()!=null) {
        api.dashboadRequest(pref.receivedSession(),  //Creating an anonymous callback
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
                        try
                        {
                            System.out.println("Result is:: " + output);
                            JSONObject jobj = new JSONObject(output);
                            String response_Code = jobj.getString("response_code");
                            String response_String = jobj.getString("response_string");

                            mainlayout.setVisibility(View.VISIBLE);
                            if (response_Code.equals("200") && response_String.equalsIgnoreCase("success"))
                            {
                                shoping_count=jobj.getString("cart");
                                new SetBagdeCount().setBadgeCount(shoping_count);
                                System.out.print("Result from Home JOBJ IS::: " + jobj);
                                JSONObject jobj_response=jobj.getJSONObject("response");
                                System.out.print("Result response from Home JOBJ IS::: " + jobj_response);
                                if (jobj_response.has("bannerimages"))
                                {
                                        JSONArray jsonArraybanner = jobj_response.getJSONArray("bannerimages");
                                        if (jsonArraybanner.length() == 0 || jsonArraybanner == null) {
                                            rel_banner.setVisibility(View.GONE);
                                        } else {
                                            rel_banner.setVisibility(View.VISIBLE);
                                            getBannerImage(jsonArraybanner);
                                        }
                                }
                                else
                                {
                                    rel_banner.setVisibility(View.GONE);

                                }
                                if (jobj_response.has("newarrival"))
                                {
                                        JSONArray jsonArray_new_arrival = jobj_response.getJSONArray("newarrival");
                                        if (jsonArray_new_arrival == null || jsonArray_new_arrival.length() == 0) {
                                            rel_new_arrival.setVisibility(View.GONE);

                                        } else {
                                            rel_new_arrival.setVisibility(View.VISIBLE);
                                            getNewArrival(jsonArray_new_arrival);
                                        }
                                }
                                else {
                                    rel_new_arrival.setVisibility(View.GONE);

                                }
                                if (jobj_response.has("bestseller"))
                                {
                                    JSONArray jsonArray_best_seller = jobj_response.getJSONArray("bestseller");
                                    if (jsonArray_best_seller.length() == 0 || jsonArray_best_seller == null) {
                                        rel_best_seller.setVisibility(View.GONE);

                                    } else {
                                        rel_best_seller.setVisibility(View.VISIBLE);
                                        getDashboard1(jsonArray_best_seller);
                                    }
                                }
                                else
                                {
                                    rel_best_seller.setVisibility(View.GONE);
                                }
                                if (jobj_response.has("express")) {
                                    JSONArray jsonArray_express = jobj_response.getJSONArray("express");
                                    if (jsonArray_express.length() == 0 || jsonArray_express == null) {
                                        rel_express.setVisibility(View.GONE);

                                    } else {

                                        getDashboard2(jsonArray_express);
                                    }
                                }
                                else
                                {
                                    rel_express.setVisibility(View.GONE);
                                }
                                System.out.println("Best Array size: "+dashboardListItems3.size());

                            }
                            else
                            {
                                rel_new_arrival.setVisibility(View.GONE);
                                rel_best_seller.setVisibility(View.GONE);
                                rel_express.setVisibility(View.GONE);
                                rel_banner.setVisibility(View.GONE);
                                System.out.println("Hi");
                                String reason=jobj.getString("reason");
                                if (reason.equals("Empty"))
                                    Toast.makeText(getActivity(),"No data to display",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(),reason,Toast.LENGTH_SHORT).show();
                            }
                            loading.dismissDialog();


                        } catch (JSONException e) {
                            loading.dismissDialog();
                            rel_new_arrival.setVisibility(View.GONE);
                            rel_best_seller.setVisibility(View.GONE);
                            rel_express.setVisibility(View.GONE);
                            rel_banner.setVisibility(View.GONE);
                            System.out.print("Result Exp iss" + e);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        loading.dismissDialog();
                        rel_new_arrival.setVisibility(View.GONE);
                        rel_best_seller.setVisibility(View.GONE);
                        rel_express.setVisibility(View.GONE);
                        rel_banner.setVisibility(View.GONE);
                        System.out.print("Result Exp iss" + error);

}
                }
        );
        getDashBorard2();
    }

}
    public void getBannerImage(JSONArray jsonArraybanner) throws JSONException {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        HashMap<String, String> url_maps_text = new HashMap<String, String>();

        for (int i = 0; i < jsonArraybanner.length(); i++) {
            System.out.println("Json array " + jsonArraybanner.getString(i));
            JSONObject bannerobj = jsonArraybanner.getJSONObject(i);
            String banner_image_id = bannerobj.getString("banner_id");
            String banner_imagepath = bannerobj.getString("banner_imagepath");
            String banner_imagetext = bannerobj.getString("banner_imagetext");
            System.out.println("bnr_val: " + banner_image_id);
            url_maps.put(banner_image_id, new Url().banner_img_url + banner_imagepath);
            url_maps_text.put(banner_image_id, banner_imagetext);

        }

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(url_maps_text.get(name))
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(HomeFragment.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(2000);
        imageSlider.addOnPageChangeListener(HomeFragment.this);
    }
    public void getNewArrival(JSONArray jsonArray_new_arrival) throws JSONException {
        int length=jsonArray_new_arrival.length();
        if (length>4)
            length=4;
        for (int i = 0; i < length; i++) {

            System.out.println("Json array new arrival " + jsonArray_new_arrival.getString(i));
            JSONObject new_arrival_obj = jsonArray_new_arrival.getJSONObject(i);
            String new_arrival_prod_id = new_arrival_obj.getString("prod_id");
            String new_arrival_product_name = new_arrival_obj.getString("product_name");

            String new_arrival_malayalam = new_arrival_obj.getString("malayalam_title");
            String new_arrival_product_cost = new_arrival_obj.getString("product_cost");
            String new_arrival_product_offer_percentage = new_arrival_obj.getString("product_offer_percentage");
            String new_arrival_product_product_image = new_arrival_obj.getString("product_image");
            System.out.println("arrival: " + new_arrival_product_name);
            HomeListItem homeListItem = new HomeListItem(new_arrival_prod_id, new_arrival_product_name,new_arrival_malayalam, new_arrival_product_cost, new_arrival_product_offer_percentage, new_arrival_product_product_image);
            dashboardListItems.add(homeListItem);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    homeAdapter.notifyItemInserted(dashboardListItems.size());


                }
            });

            homeAdapter.setLoaded();

        }
        homeAdapter.notifyDataSetChanged();
    }
    public void getDashboard1(JSONArray jsonArray_best_sel){
        JSONArray jsonArray_best_seller=jsonArray_best_sel;
        System.out.println("Json Array best seller:: "+jsonArray_best_seller);
        int length=jsonArray_best_seller.length();
        if (length>4)
            length=4;
        for (int i=0;i<length;i++)
        {

            try {
              //  System.out.println("Best Json array  " + jsonArray_best_seller.getString(i));

                JSONObject best_seller_obj = jsonArray_best_seller.getJSONObject(i);
                String best_seller_prod_id = best_seller_obj.getString("prod_id");
                String best_seller_product_name = best_seller_obj.getString("product_name");
                String best_seller_product__malayalam = best_seller_obj.getString("malayalam_title");

                String best_seller_product_cost = best_seller_obj.getString("product_cost");
                String best_seller_product_offer_percentage = best_seller_obj.getString("product_offer_percentage");
                String best_seller_product_image = best_seller_obj.getString("product_image");
                System.out.println("Best:: " + best_seller_prod_id + " " + best_seller_product_name + " " + best_seller_product_cost + " " + best_seller_product_offer_percentage + " " + best_seller_product_image);
                HomeListBestSellerItem homeListBestSellerItem = new HomeListBestSellerItem(best_seller_prod_id,best_seller_product_name,best_seller_product__malayalam, best_seller_product_cost, best_seller_product_offer_percentage, best_seller_product_image);
                dashboardListItems1.add(homeListBestSellerItem);



                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        homeAdapter1.notifyItemInserted(dashboardListItems1.size());


                    }
                });


                homeAdapter1.setLoaded();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            homeAdapter1.notifyDataSetChanged();
        }

    }
    public void getDashboard2(JSONArray jsonArray_express){
        JSONArray jsonArray_healthy_express=jsonArray_express;
        System.out.println("Json Array express :: "+jsonArray_healthy_express);
        int length=jsonArray_healthy_express.length();
        if (length>4)
            length=4;
        for (int i=0;i<length;i++)
        {

            try {
                //System.out.println("Best Json array  " + jsonArray_healthy_express.getString(i));

                JSONObject express_obj = jsonArray_healthy_express.getJSONObject(i);
                String express_prod_id = express_obj.getString("prod_id");
                String express_product_name = express_obj.getString("product_name");
                String express_product_malayalam_name = express_obj.getString("malayalam_title");
                String express_product_cost = express_obj.getString("product_cost");
                String express_product_offer_percentage = express_obj.getString("product_offer_percentage");
                String express_product_image = express_obj.getString("product_image");
                System.out.println("Best:: " + express_prod_id + " " +express_product_name + " " + express_product_cost + " " +express_product_offer_percentage + " " + express_product_image);
                HomeExpressListItem homeExpressListItem = new HomeExpressListItem(express_prod_id,express_product_name,express_product_malayalam_name, express_product_cost, express_product_offer_percentage, express_product_image);
                dashboardListItems3.add(homeExpressListItem);



                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        homeExpressAdapter.notifyItemInserted(dashboardListItems3.size());


                    }
                });


                homeExpressAdapter.setLoaded();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            homeExpressAdapter.notifyDataSetChanged();
        }


    }
}
