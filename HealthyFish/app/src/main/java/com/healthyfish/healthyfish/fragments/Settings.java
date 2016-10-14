package com.healthyfish.healthyfish.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthyfish.healthyfish.R;


/**
 * Created by RohiniAjith on 6/6/2016.
 */
public class Settings extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.settings, container, false);
        return rootView;
    }
}
