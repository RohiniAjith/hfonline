package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.api.Url;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;

import com.healthyfish.healthyfish.model.ShopingCartListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 22-09-2016.
 */
public class ShopingCartAdapter  extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    ;
    private List<ShopingCartListItem> dashlist;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context con;

    public ShopingCartAdapter(List<ShopingCartListItem> list, RecyclerView recyclerView, Context context) {
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
                    R.layout.activity_shoping_cart, parent, false);

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
            ShopingCartListItem singleDashboard= (ShopingCartListItem) dashlist.get(position);
            // Set adapter to spinner
            System.out.println("HIT Title"+dashlist.get(position).getTitle());
            ((StudentViewHolder) holder).tvTitle.setText(dashlist.get(position).getTitle());
            System.out.println("HIT malayalam_title"+dashlist.get(position).getMalayalam_title());
            ((StudentViewHolder) holder).malayalam_title.setText(dashlist.get(position).getMalayalam_title());
            System.out.println("HIT price"+dashlist.get(position).getPrice());
            ((StudentViewHolder) holder).price.setText(dashlist.get(position).getPrice());
            System.out.println("HIT quantity"+dashlist.get(position).getQuantity());
            ((StudentViewHolder) holder).quantity.setText(dashlist.get(position).getQuantity());
            System.out.println("HIT Image"+dashlist.get(position).getImage());
            System.out.println("HIT Image url"+new Url().home_img_url+dashlist.get(position).getImage());

            Picasso.with(con)
                    .load(new Url().home_img_url+dashlist.get(position).getImage())
                    .placeholder(R.drawable.logo)   // optional
                    .error(R.drawable.logo)      // optional
                    .resize(95,90)                        // optional
                    .into(((StudentViewHolder) holder).iv);
            ((StudentViewHolder) holder).shopingCartListItem= singleDashboard;

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
        public TextView tvTitle,malayalam_title,price,quantity;
        ImageView iv;
        ShopingCartListItem shopingCartListItem;


        public StudentViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.asc_category_title);

            malayalam_title = (TextView) v.findViewById(R.id.asc_category_title_malayalam);
            price=(TextView)v.findViewById(R.id.asc_category_iten_original_price);
            iv=(ImageView)v.findViewById(R.id.asc_iv);
            quantity=(TextView)v.findViewById(R.id.asc_rel_hhrvi_tv_no_value);

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
