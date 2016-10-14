package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.dialogbox.AlertBox;
import com.healthyfish.healthyfish.dialogbox.CustomProgressDialog;
import com.healthyfish.healthyfish.iterface.HealthyFishApi;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.manager.PrefManager;
import com.healthyfish.healthyfish.manager.SetBagdeCount;
import com.healthyfish.healthyfish.model.HomeHeaderListItem;
import com.healthyfish.healthyfish.model.ViewMoreListItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 10-10-2016.
 */
public class ViewMorerAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ViewMoreListItem> dashlist;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 4;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context con;


    public ViewMorerAdapter(List<ViewMoreListItem> list, RecyclerView recyclerView,Context context) {
        dashlist = list;
        con=context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }

                        }
                    });


        }
    }

    @Override
    public int getItemViewType(int position) {
        return dashlist.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.viewmore_cardview, parent, false);

            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof StudentViewHolder) {
            ViewMoreListItem singleDashboard= (ViewMoreListItem) dashlist.get(position);
            if (holder instanceof StudentViewHolder) {
                ((StudentViewHolder) holder).tvTitle.setText(dashlist.get(position).getMobile_title());
                System.out.println("Dash board value title" + singleDashboard.getMalayalam_title());
                System.out.println("Dash board value description" + singleDashboard.getMalayalam_title());
                ((StudentViewHolder) holder).tv_Malayalam_title.setText(dashlist.get(position).getMalayalam_title());
                ((StudentViewHolder) holder).offer_price.setText("Rs"+dashlist.get(position).getOffer_price());
                ((StudentViewHolder) holder).original_price.setText("Rs"+dashlist.get(position).getActual_price());
                ((StudentViewHolder) holder).spinner.setAdapter(dashlist.get(position).getAdapter());



                Picasso.with(con)
                        .load(new Url().home_img_url+singleDashboard.getProduct_image())
                        .placeholder(R.drawable.logo)   // optional
                        .error(R.drawable.logo)      // optional
                        .resize(95,90)                        // optional
                        .into(((StudentViewHolder) holder).ivImageLoader);
                ((StudentViewHolder) holder).dashboardListItem= singleDashboard;

            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return dashlist.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }




    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,tv_Malayalam_title,offer_price,original_price,tv_increase,tv_decrease,tv_quantity,tv_add;
        public ImageView ivImageLoader;
        Spinner spinner;
        public ViewMoreListItem dashboardListItem;
        int increase_i=1;
        String type_gram_id="0";

        String type_gram;
        public StudentViewHolder(final View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.view_more_adapter_title);
            tv_Malayalam_title = (TextView) v.findViewById(R.id.view_more_adapter_title_malayalam);
            offer_price=(TextView)v.findViewById(R.id.view_more_adapter_item_offer_price);
            original_price=(TextView)v.findViewById(R.id.view_more_adapter_item_original_price);
            tv_increase = (TextView) v.findViewById(R.id.view_more_adapter_tv_no_increase);
            tv_decrease = (TextView) v.findViewById(R.id.view_more_adapter_tv_no_decrease);
            tv_quantity = (TextView) v.findViewById(R.id.view_more_adapter_tv_no_value);
            tv_add = (TextView) v.findViewById(R.id.view_more_adapter_add);

            spinner=(Spinner)v.findViewById(R.id.view_more_recycler_view_list_itm_spnr_type_dropdwn);
            ivImageLoader=(ImageView)v.findViewById(R.id.view_more_adapter_iv);

// Listener called when spinner item selected
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                    // your code here

                    // Get selected row data to show on screen
                    type_gram_id    = ((TextView) v.findViewById(R.id.text_price_cat_id)).getText().toString();
                    System.out.println("Type_gram_id"+type_gram_id);
//                    type_gram= (String) spinner.getItemAtPosition(position);
//                    System.out.println("Spinner Value: "+type_gram);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            tv_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increase_i++;
                    tv_quantity.setText(String.valueOf(increase_i));

                }
            });
            tv_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (increase_i>0)
                        increase_i--;
                    tv_quantity.setText(String.valueOf(increase_i));

                }
            });
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (type_gram_id.equals("0"))
                        Toast.makeText(view.getContext(), "Please select the quantity", Toast.LENGTH_SHORT).show();
                    else {
                        String cost=original_price.getText().toString().substring(2);
                        System.out.println("Cost: "+cost);
                        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(view.getContext());
                        customProgressDialog.showCartDialog();
                        System.out.println("add");
                        RestAdapter adapter = new RestAdapter.Builder()
                                .setEndpoint(new Url().base_link)
                                .build();

                        //Creating an object of our api interface
                        HealthyFishApi api = adapter.create(HealthyFishApi.class);

                        PrefManager pref = new PrefManager(v.getContext());
                        ;
                        //Defining the method
                        api.sendShopCart(pref.receivedSession(), pref.receivedSession(), dashboardListItem.getProd_id(), String.valueOf(increase_i), "1", dashboardListItem.getActual_price(),  //Creating an anonymous callback
                                new Callback<Response>() {
                                    @Override
                                    public void success(Response result, Response response) {
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
                                            System.out.println("Result JOBJ IS::: " + jobj);
                                            String response_code = jobj.getString("response_code");
                                            String response_msg = jobj.getString("response_string");
                                            customProgressDialog.dismissDialog();
                                            if (response_code.equals("200") && response_msg.equals("success")) {
                                                System.out.println("200");
                                                new AlertBox(view.getContext()).alertSuccessShopCart(view.getContext());
                                                String cart = jobj.getString("cart");
                                                new SetBagdeCount().setBadgeCount(cart);
                                            } else if (response_code.equals("201") && response_msg.equals("failed")) {
                                                System.out.println("failed");
                                            } else if (response_code.equals("202") && response_msg.equals("not_activated"))

                                            {

                                                System.out.println("not_activated");

                                            } else if (response_code.equals("202") && response_msg.equals("not_suppport_version")) {


                                                System.out.println("not_suppport_version");
                                            } else {

                                                System.out.println("else");
                                            }

                                        } catch (JSONException e) {
                                            System.out.print("Result Exp iss" + e);
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        customProgressDialog.dismissDialog();
                                        System.out.print("Result Exp iss" + error);
                                    }
                                }
                        );


                    }
                }
            });
        }

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
    /****** Function to set data in ArrayList *************/
}
