package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.activity.DashBoardActivity;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.fragments.Home_Details_Fragment;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.model.HomeListBestSellerItem;
import com.healthyfish.healthyfish.model.HomeListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 28-07-2016.
 */
public class HomeBestSellerAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<HomeListBestSellerItem> dashlist;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 4;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context con;


    public HomeBestSellerAdapter(List<HomeListBestSellerItem> list, RecyclerView recyclerView, Context context) {
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
                    R.layout.home_card_seller, parent, false);

            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            HomeListBestSellerItem singleDashboard= (HomeListBestSellerItem) dashlist.get(position);
            System.out.println("Best seller board value best_seller_ length: "+dashlist.size());
            String best_seller_product_id=dashlist.get(position).getBest_seller_prod_id();
            System.out.println("Best seller board value best_seller_product_id" +best_seller_product_id);
            String title=dashlist.get(position).getBest_seller_product_name();
            ((StudentViewHolder) holder).tvTitle.setText(title);
            ((StudentViewHolder) holder).tvMalayalamTitle.setText(dashlist.get(position).getBest_seller_product__malayalam());
            System.out.println("Best seller board value title" +title);
            String offer=dashlist.get(position).getBest_seller_product_offer_percentage();
            String cost=dashlist.get(position).getBest_seller_product_cost();

           ((StudentViewHolder) holder).dasboard_offer.setText(offer);
            ((StudentViewHolder) holder). dashboard_rate.setText(cost);
            ((StudentViewHolder) holder).id=dashlist.get(position).getBest_seller_prod_id();

            Picasso.with(con)
                    .load(new Url().home_img_url+singleDashboard.getBest_seller_product_image())
                    .placeholder(R.drawable.logo)   // optional
                    .error(R.drawable.logo)      // optional
                                           // optional
                    .into(((StudentViewHolder) holder).ivImageLoader);
            ((StudentViewHolder) holder).dashboardListItem= singleDashboard;

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
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
        public TextView dasboard_offer,dashboard_rate;
        public ImageView ivImageLoader;
        public TextView tvTitle,tvMalayalamTitle;
        public HomeListBestSellerItem dashboardListItem;
        public String id;
        public StudentViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.home_best_seller_title);
            tvMalayalamTitle = (TextView) v.findViewById(R.id.home_best_seller_malayalam_title);

            dasboard_offer = (TextView) v.findViewById(R.id.home_best_seller_offer);
            ivImageLoader=(ImageView)v.findViewById(R.id.home_best_seller_img);
            dashboard_rate=(TextView)v.findViewById(R.id.home_best_seller_rate);
                        //  tvReadMore=(TextView)v.findViewById(R.id.readmore_tv_dashboard);
//            tvReadMore.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(v.getContext(), Dashboard_Details_Activity.class);
//                    i.putExtra("title", dashboardListItem.getTitle());
//                    i.putExtra("description", dashboardListItem.getDescription());
//                    i.putExtra("thumb_images", dashboardListItem.getOrginal_images());
//                    v.getContext().startActivity(i);
//                }
//            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("HOME ADAPTER BEST");
                    System.out.println("Product_id"+id);
                    DashBoardActivity dashBoardActivity = (DashBoardActivity)v.getContext();
                    Fragment fragment = new Home_Details_Fragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("prod_id",id);
                    bundle.putString("title",tvTitle.getText().toString());
                    fragment.setArguments(bundle);
                    String backStateName = fragment.getClass().getName();

                    FragmentManager manager = (dashBoardActivity).getSupportFragmentManager();
                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                    if (!fragmentPopped){ //fragment not in back stack, create it.
                        FragmentTransaction ft = manager.beginTransaction();
                        ft.replace(R.id.main_fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
//                    FragmentTransaction fragmentTransaction=  (dashBoardActivity).getSupportFragmentManager().beginTransaction();
//
//                    fragmentTransaction.replace(R.id.main_fragment_container, fragment);
//
//                    fragmentTransaction.commit();

//                    Intent i = new Intent(v.getContext(), Dashboard_Details_Activity.class);
//                    i.putExtra("title", dashboardListItem.getTitle());
//                    i.putExtra("description", dashboardListItem.getDescription());
//                    i.putExtra("thumb_images", dashboardListItem.getOrginal_images());
//                    v.getContext().startActivity(i);

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
}

