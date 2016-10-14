//package com.healthyfish.healthyfish.fragments;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.Toast;
//
//import com.healthyfish.healthyfish.R;
//import com.healthyfish.healthyfish.adapter.CustomGrid;
//
//
///**
// * Created by RohiniAjith on 6/6/2016.
// */
//public class ShopbyCategory extends Fragment {
//GridView gridview;
//    String[] web = {
//            "SHRIPS",
//            "SEA FISHES",
//            "RIVER FISHES",
//            "LAKE WATER FISHES",
//            "OTHERS",
//            "SPECIAL OFFERS",
//            "COMBO",
//
//    } ;
//    int[] imageId = {
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//            R.drawable.ic_fish,
//
//    };
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView =inflater.inflate(R.layout.shopbycategory, container, false);
//        gridview=(GridView)rootView.findViewById(R.id.shpbyctgry_grdvw);
//        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
//        gridview.setAdapter(adapter);
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        return rootView;
//    }
//
//
//}
