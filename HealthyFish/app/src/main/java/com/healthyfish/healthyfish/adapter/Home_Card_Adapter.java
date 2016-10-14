package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.model.HomeImagesItem;
import com.healthyfish.healthyfish.model.HomeListBestSellerItem;
import com.healthyfish.healthyfish.model.HomeListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 31-08-2016.
 */
public class Home_Card_Adapter extends RecyclerView.Adapter<Home_Card_Adapter.ViewHolder> {
    private static final String TAG = "Home_Card_Adapter";

    private String[] mDataSet;
    private int[] mDataSetTypes;
    private List<HomeImagesItem> dashlist;
    private List<HomeListItem> newarrivallist;
    private List<HomeListBestSellerItem>  bestsellerlist;
    public static final int newarrival = 0;
    public static final int bestseller = 1;
    public static final int bannerimages = 2;
List<Object> list;
    Context con;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class NewArrivalViewHolder extends ViewHolder {
        public TextView tvTitle;
        public TextView dasboard_offrer,dashboard_rate;
        public ImageView ivImageLoader;
        public HomeListItem dashboardListItem;
        public NewArrivalViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.dasboard_title);

            dasboard_offrer = (TextView) v.findViewById(R.id.dasboard_offrer);
            ivImageLoader=(ImageView)v.findViewById(R.id.thumb_img);
            dashboard_rate=(TextView)v.findViewById(R.id.dashboard_rate);
        }
    }

    public class BestSellerViewHolder extends ViewHolder {
        public TextView dasboard_offer,dashboard_rate;
        public ImageView ivImageLoader;
        public TextView tvTitle;



        public BestSellerViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.home_best_seller_title);

            dasboard_offer = (TextView) v.findViewById(R.id.home_best_seller_offer);
            ivImageLoader=(ImageView)v.findViewById(R.id.home_best_seller_img);
            dashboard_rate=(TextView)v.findViewById(R.id.home_best_seller_rate);
        }
    }

    public class BannerViewHolder extends ViewHolder {
        public ImageView ivImageLoader;
        public HomeImagesItem dashboardListItem;
        public BannerViewHolder(View v) {
            super(v);
            ivImageLoader = (ImageView) v.findViewById(R.id.iv_home_cardview_image);
        }
    }


    public Home_Card_Adapter(List<Object> list, Context context) {
//        mDataSet = dataSet;
      //mDataSetTypes = dataSetTypes;
        list=list;

        con=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == newarrival) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_cardview, viewGroup, false);

            return new NewArrivalViewHolder(v);
        } else if (viewType == bestseller) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_card_seller, viewGroup, false);
            return new BestSellerViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_cardview_images, viewGroup, false);
            return new BannerViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == newarrival) {
            NewArrivalViewHolder holder = (NewArrivalViewHolder) viewHolder;
            HomeListItem singleDashboard= (HomeListItem) newarrivallist.get(position);
            String new_arrival_product_id=newarrivallist.get(position).getProduct_id();
            ((NewArrivalViewHolder) holder).tvTitle.setText(newarrivallist.get(position).getNew_arrival_product_name());
            System.out.println("Dash board value title" + singleDashboard.getNew_arrival_product_name());
            String offer=newarrivallist.get(position).getNew_arrival_product_offer_percentage();
            String cost=newarrivallist.get(position).getNew_arrival_product_cost();

            ((NewArrivalViewHolder) holder).dasboard_offrer.setText(offer);
            ((NewArrivalViewHolder) holder). dashboard_rate.setText(cost);
            Picasso.with(con)
                    .load(new Url().home_img_url+singleDashboard.getNew_arrival_product_product_image())
                    .placeholder(R.drawable.product_one)   // optional
                    .error(R.drawable.product_one)      // optional
                    // optional
                    .into(((NewArrivalViewHolder) holder).ivImageLoader);
            ((NewArrivalViewHolder) holder).dashboardListItem= singleDashboard;

        }
        else if (viewHolder.getItemViewType() == bestseller) {
            BestSellerViewHolder holder = (BestSellerViewHolder) viewHolder;
            HomeListBestSellerItem singleDashboard= (HomeListBestSellerItem) bestsellerlist.get(position);
            System.out.println("Best seller board value best_seller_ length: "+bestsellerlist.size());
            String best_seller_product_id=bestsellerlist.get(position).getBest_seller_prod_id();
            System.out.println("Best seller board value best_seller_product_id" +best_seller_product_id);
            String title=bestsellerlist.get(position).getBest_seller_product_name();
            ((BestSellerViewHolder) holder).tvTitle.setText(title);
            System.out.println("Best seller board value title" +title);
            String offer=bestsellerlist.get(position).getBest_seller_product_offer_percentage();
            String cost=bestsellerlist.get(position).getBest_seller_product_cost();

            ((BestSellerViewHolder) holder).dasboard_offer.setText(offer);
            ((BestSellerViewHolder) holder). dashboard_rate.setText(cost);
            Picasso.with(con)
                    .load(new Url().home_img_url+singleDashboard.getBest_seller_product_image())
                    .placeholder(R.drawable.product_four)   // optional
                    .error(R.drawable.product_four)      // optional
                    // optional
                    .into(((BestSellerViewHolder) holder).ivImageLoader);
           // ((BestSellerViewHolder) holder).dashboardListItem= singleDashboard;
        }
        else {
            BannerViewHolder holder = (BannerViewHolder) viewHolder;
            HomeImagesItem singleDashboard = (HomeImagesItem) dashlist.get(position);
            ((BannerViewHolder) holder).ivImageLoader.setImageResource(singleDashboard.getDrawable_image());
            ((BannerViewHolder) holder).dashboardListItem = singleDashboard;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }
}