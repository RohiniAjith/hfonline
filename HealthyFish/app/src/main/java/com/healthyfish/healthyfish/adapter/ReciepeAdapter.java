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
import com.healthyfish.healthyfish.iterface.OnLoadMoreListener;
import com.healthyfish.healthyfish.model.RecipeListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 13-10-2016.
 */
public class ReciepeAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<RecipeListItem> dashlist;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 4;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context con;


    public ReciepeAdapter(List<RecipeListItem> list, RecyclerView recyclerView,Context context) {
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
                    R.layout.fragment_recipe_cardview, parent, false);

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

            RecipeListItem singleDashboard= (RecipeListItem) dashlist.get(position);
            System.out.println("Dash board value title" +dashlist.get(position).getTitle());
            ((StudentViewHolder) holder).tvTitle.setText(dashlist.get(position).getTitle());
            System.out.println("Dash board value title" + singleDashboard.getTitle());
            String des=singleDashboard.getDescription();
            System.out.println("Dash board value description" + singleDashboard.getDescription());
            String des1="";
            if(des.length()>93) {
                des1 = des.substring(0, 115);
                des1=des1.concat(" .....");
            }
            else{
                des1=des;
            }

            ((StudentViewHolder) holder).tvDescription.setText(des1);
            ((StudentViewHolder) holder).readmore_tv_arrow.setText(singleDashboard.getIcons());
            System.out.println("Dash board value thumb" + singleDashboard.getThumb_images());
            Picasso.with(con)
                    .load("http://jaivakarshakan.com/upload/thumb/"+singleDashboard.getThumb_images())
                    .placeholder(R.drawable.logo)   // optional
                    .error(R.drawable.logo)      // optional
                    .resize(95,90)                        // optional
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
        public TextView tvTitle;

        public TextView tvDescription,readmore_tv_arrow;
        public ImageView ivImageLoader;
        public TextView tvReadMore;
        public RecipeListItem dashboardListItem;
        Typeface font;
        public StudentViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.frc_title);

            tvDescription = (TextView) v.findViewById(R.id.frc_description);
            ivImageLoader=(ImageView)v.findViewById(R.id.thumb_img);
            tvReadMore=(TextView)v.findViewById(R.id.frc_readmore_tv_dashboard);
            readmore_tv_arrow=(TextView) v.findViewById(R.id.frc_readmore_tv_arrow);
            font = Typeface.createFromAsset( v.getContext().getAssets(), "fonts/fontawesome-webfont.ttf" );
            readmore_tv_arrow.setTypeface(font);
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
//                    DashBoardActivity dashBoardActivity = (DashBoardActivity)v.getContext();
//                    Fragment fragment = new Dashboard_Details_Fragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title", dashboardListItem.getTitle());
//                    bundle.putString("description", dashboardListItem.getDescription());
//                    bundle.putString("thumb_images", dashboardListItem.getOrginal_images());
//                    fragment.setArguments(bundle);
//                    String backStateName = fragment.getClass().getName();
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