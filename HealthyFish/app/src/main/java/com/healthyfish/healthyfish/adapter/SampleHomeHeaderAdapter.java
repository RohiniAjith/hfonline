//package com.healthyfish.healthyfish.adapter;
//
//import android.content.Context;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.view.ViewGroup.LayoutParams;
//import com.healthyfish.healthyfish.R;
//import com.healthyfish.healthyfish.activity.DashBoardActivity;
//import com.healthyfish.healthyfish.activity.HomeHeaderActivity;
//import com.healthyfish.healthyfish.api.Url;
//import com.healthyfish.healthyfish.iterface.HealthyFishApi;
//import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
//import com.healthyfish.healthyfish.manager.PrefManager;
//import com.healthyfish.healthyfish.manager.SetBagdeCount;
//import com.healthyfish.healthyfish.model.BadgeDrawable;
//import com.healthyfish.healthyfish.model.DashboardListItem;
//import com.healthyfish.healthyfish.model.HomeHeaderListItem;
//import com.healthyfish.healthyfish.model.MenuModel;
//import com.healthyfish.healthyfish.model.SpinnerModel;
//import com.squareup.picasso.Picasso;
//import android.view.LayoutInflater;
//import android.widget.Toast;
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
//public class SampleHomeHeaderAdapter extends RecyclerView.Adapter {
//        private final int VIEW_ITEM = 1;
//        private final int VIEW_PROG = 0;
//
//        private List<HomeHeaderListItem> dashlist;
//
//        // The minimum amount of items to have below your current scroll position
//        // before loading more.
//        private int visibleThreshold = 4;
//        private int lastVisibleItem, totalItemCount;
//        private boolean loading;
//        private OnLoadMoreListener onLoadMoreListener;
//        Context con;
//
//
//        public SampleHomeHeaderAdapter(List<HomeHeaderListItem> list, RecyclerView recyclerView,Context context) {
//            dashlist = list;
//            con=context;
//            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//
//                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
//                        .getLayoutManager();
//
//
//                recyclerView
//                        .addOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrolled(RecyclerView recyclerView,
//                                                   int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
//
//                                totalItemCount = linearLayoutManager.getItemCount();
//                                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                                    // End has been reached
//                                    // Do something
//                                    if (onLoadMoreListener != null) {
//                                        onLoadMoreListener.onLoadMore();
//                                    }
//                                    loading = true;
//                                }
//
//                            }
//                        });
//
//
//            }
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return dashlist.get(position) != null ? VIEW_ITEM : VIEW_PROG;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                          int viewType) {
//            RecyclerView.ViewHolder vh;
//            if (viewType == VIEW_ITEM) {
//                View v = LayoutInflater.from(parent.getContext()).inflate(
//                        R.layout.dashboard_cardview1, parent, false);
//
//                vh = new StudentViewHolder(v);
//            } else {
//                View v = LayoutInflater.from(parent.getContext()).inflate(
//                        R.layout.progressbar_item, parent, false);
//
//                vh = new ProgressViewHolder(v);
//            }
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//
//            if (holder instanceof StudentViewHolder) {
//                HomeHeaderListItem singleDashboard= (HomeHeaderListItem) dashlist.get(position);
//            if (holder instanceof StudentViewHolder) {
//                ((StudentViewHolder) holder).tvTitle.setText(dashlist.get(position).getMobile_title());
//                System.out.println("Dash board value title" + singleDashboard.getMalayalam_title());
//                System.out.println("Dash board value description" + singleDashboard.getMalayalam_title());
//                ((StudentViewHolder) holder).tv_Malayalam_title.setText(dashlist.get(position).getMobile_title());
//                ((StudentViewHolder) holder).tv_Malayalam_title.setText(dashlist.get(position).getMobile_title());
//                ((StudentViewHolder) holder).offer_price.setText(dashlist.get(position).getOffer_price());
//                ((StudentViewHolder) holder).original_price.setText(dashlist.get(position).getActual_price());
//
//                Picasso.with(con)
//                        .load(new Url().home_img_url+singleDashboard.getProduct_image())
//                        .placeholder(R.drawable.logo)   // optional
//                        .error(R.drawable.logo)      // optional
//                        .resize(95,90)                        // optional
//                        .into(((StudentViewHolder) holder).ivImageLoader);
//                ((StudentViewHolder) holder).dashboardListItem= singleDashboard;
//
//            } else {
//                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
//            }
//            }
//        }
//
//    public void setLoaded() {
//        loading = false;
//    }
//
//    @Override
//    public int getItemCount() {
//        return dashlist.size();
//    }
//
//    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
//        this.onLoadMoreListener = onLoadMoreListener;
//    }
//
//
//
//
//    //
//    public static class StudentViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvTitle,tv_Malayalam_title,offer_price,original_price,tv_increase,tv_decrease,tv_quantity,tv_add;
//        public ImageView ivImageLoader;
//        Spinner spinner;
//        public HomeHeaderListItem dashboardListItem;
//        int increase_i=1;
//        static int count =1;
//        String type_gram;
//        public StudentViewHolder(final View v) {
//            super(v);
//            tvTitle = (TextView) v.findViewById(R.id.home_header_adapter_title);
//            tv_Malayalam_title = (TextView) v.findViewById(R.id.home_header_adapter_title_malayalam);
//            offer_price=(TextView)v.findViewById(R.id.home_header_adapter_item_offer_price);
//            original_price=(TextView)v.findViewById(R.id.home_header_adapter_item_original_price);
//            tv_increase = (TextView) v.findViewById(R.id.home_header_adapter_tv_no_increase);
//            tv_decrease = (TextView) v.findViewById(R.id.home_header_adapter_tv_no_decrease);
//            tv_quantity = (TextView) v.findViewById(R.id.home_header_adapter_tv_no_value);
//            tv_add = (TextView) v.findViewById(R.id.home_header_adapter_add);
//
//            spinner=(Spinner)v.findViewById(R.id.home_header_recycler_view_list_itm_spnr_type_dropdwn);
//            ivImageLoader=(ImageView)v.findViewById(R.id.home_header_adapter_iv);
//
//// Listener called when spinner item selected
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
//                    // your code here
//
//                    // Get selected row data to show on screen
//                    //String item    = ((TextView) v.findViewById(R.id.sp_text)).getText().toString();
//
//                    type_gram= (String) spinner.getItemAtPosition(position);
//                    System.out.println("Spinner Value: "+type_gram);
//
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parentView) {
//                    // your code here
//                }
//
//            });
//            tv_increase.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    increase_i++;
//                    tv_quantity.setText(String.valueOf(increase_i));
//
//                }
//            });
//            tv_decrease.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (increase_i>0)
//                        increase_i--;
//                    tv_quantity.setText(String.valueOf(increase_i));
//
//                }
//            });
//            tv_add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    RestAdapter adapter = new RestAdapter.Builder()
//                            .setEndpoint(new Url().base_link)
//                            .build();
//
//                    //Creating an object of our api interface
//                    HealthyFishApi api = adapter.create(HealthyFishApi.class);
//
//
//                    //Defining the method
//                    api.sendShopCart("2",dashboardListItem.getProd_id(),String.valueOf(increase_i) ,"500",dashboardListItem.getActual_price(),  //Creating an anonymous callback
//                            new Callback<Response>() {
//                                @Override
//                                public void success(Response result, Response response) {
//                                    //On success we will read the server's output using bufferedreader
//                                    //Creating a bufferedreader object
//                                    BufferedReader reader = null;
//
//                                    //An string to store output from the server
//                                    String output = "";
//
//                                    try {
//                                        //Initializing buffered reader
//                                        reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
//
//                                        //Reading the output in the string
//                                        output = reader.readLine();
//                                    } catch (IOException e) {
//                                        System.out.print("Result Exp is IO ::" + e);
//                                    }
//                                    try {
//                                        System.out.println("Result is:: " + output);
//                                        JSONObject jobj = new JSONObject(output);
//                                        System.out.print("Result JOBJ IS::: " + jobj);
//                                        String response_code = jobj.getString("response_code");
//                                        String response_msg = jobj.getString("response_string");
//                                        if(response_code.equals("200")&&response_msg.equals("success")) {
//                                            new SetBagdeCount().setBadgeCount(String.valueOf(count));
//                                            count=count+1;
//                                        }
//                                        else if(response_code.equals("201")&& response_msg.equals("failed"))
//                                        {
//
//                                        }
//                                        else if(response_code.equals("202")&& response_msg.equals("not_activated"))
//
//                                        {
//
//
//
//                                        }
//                                        else if(response_code.equals("202")&& response_msg.equals("not_suppport_version"))
//                                        {
//
//
//
//                                        }
//                                        else
//                                        {
//
//
//                                        }
//
//                                    } catch (JSONException e) {
//                                        System.out.print("Result Exp iss" + e);
//                                    }
//                                }
//
//                                @Override
//                                public void failure(RetrofitError error) {
//
//                                }
//                            }
//                    );
//
//
//
//
//
//                }
//            });
//        }
//
//    }
//
//    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//
//        public ProgressViewHolder(View v) {
//            super(v);
//            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
//        }
//    }
//    /****** Function to set data in ArrayList *************/
//
//}