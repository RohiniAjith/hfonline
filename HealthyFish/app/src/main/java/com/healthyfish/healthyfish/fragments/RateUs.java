//package com.healthyfish.healthyfish.fragments;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.healthyfish.healthyfish.R;
//import com.healthyfish.healthyfish.dialogbox.AlertBox;
//
///**
// * Created by RohiniAjith on 6/12/2016.
// */
//public class RateUs extends Fragment {
//    String toolbar_title;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // return super.onCreateView(inflater, container, savedInstanceState);
//        View rootView = inflater.inflate(R.layout.fragment_common, container, false);
//        toolbar_title = getArguments().getString("category");
//        getActivity().setTitle(toolbar_title);
//
//        new AlertBox(getActivity()).alertBox_rateus();
//        return rootView;
//    }
//}
