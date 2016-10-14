package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.activity.DashBoardActivity;
import com.healthyfish.healthyfish.fragments.Best_Seller_Fragment;
import com.healthyfish.healthyfish.fragments.Home_Details_Fragment;
import com.healthyfish.healthyfish.fragments.RecipeFragment;
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.model.HomeImagesItem;
import com.healthyfish.healthyfish.model.HomeListBestSellerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 28-07-2016.
 */
public class HomeImagesAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<HomeImagesItem> dashlist;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 4;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context con;


    public HomeImagesAdapter(List<HomeImagesItem> list, RecyclerView recyclerView, Context context) {
        dashlist = list;
        con = context;
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
                    R.layout.home_cardview_images, parent, false);

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

            HomeImagesItem singleDashboard = (HomeImagesItem) dashlist.get(position);
            ((StudentViewHolder) holder).ivImageLoader.setImageResource(singleDashboard.getDrawable_image());
            ((StudentViewHolder) holder).dashboardListItem = singleDashboard;

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
        public ImageView ivImageLoader;
        public HomeImagesItem dashboardListItem;

        public StudentViewHolder(View v) {
            super(v);

            ivImageLoader = (ImageView) v.findViewById(R.id.iv_home_cardview_image);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition()==2)
                    {
                        DashBoardActivity dashBoardActivity = (DashBoardActivity) v.getContext();
                        Fragment fragment = new Best_Seller_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "Recipe");
                        fragment.setArguments(bundle);
//                   String backStateName = fragment.getClass().getName();
//
//                    FragmentManager manager = (dashBoardActivity).getSupportFragmentManager();
//                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
//
//                    if (!fragmentPopped){ //fragment not in back stack, create it.
//                        FragmentTransaction ft = manager.beginTransaction();
//                        ft.replace(R.id.container_body, fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//                    }
                        FragmentTransaction fragmentTransaction = (dashBoardActivity).getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.replace(R.id.main_fragment_container, fragment);

                        fragmentTransaction.commit();
                    }
                    if (getAdapterPosition()==3)
                    {
                        DashBoardActivity dashBoardActivity = (DashBoardActivity) v.getContext();
                    Fragment fragment = new RecipeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "Recipe");
                        fragment.setArguments(bundle);
//                   String backStateName = fragment.getClass().getName();
//
//                    FragmentManager manager = (dashBoardActivity).getSupportFragmentManager();
//                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
//
//                    if (!fragmentPopped){ //fragment not in back stack, create it.
//                        FragmentTransaction ft = manager.beginTransaction();
//                        ft.replace(R.id.container_body, fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//                    }
                    FragmentTransaction fragmentTransaction = (dashBoardActivity).getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.main_fragment_container, fragment);

                    fragmentTransaction.commit();
                    }
//                    DashBoardActivity dashBoardActivity = (DashBoardActivity) v.getContext();
//                    Fragment fragment = new Home_Details_Fragment();
////                    Bundle bundle = new Bundle();
////                    bundle.putString("title", dashboardListItem.getTitle());
////                    bundle.putString("description", dashboardListItem.getDescription());
////                    bundle.putString("thumb_images", dashboardListItem.getOrginal_images());
////                    fragment.setArguments(bundle);
////                   String backStateName = fragment.getClass().getName();
////
////                    FragmentManager manager = (dashBoardActivity).getSupportFragmentManager();
////                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
////
////                    if (!fragmentPopped){ //fragment not in back stack, create it.
////                        FragmentTransaction ft = manager.beginTransaction();
////                        ft.replace(R.id.container_body, fragment);
////                        ft.addToBackStack(null);
////                        ft.commit();
////                    }
//                    FragmentTransaction fragmentTransaction = (dashBoardActivity).getSupportFragmentManager().beginTransaction();
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
