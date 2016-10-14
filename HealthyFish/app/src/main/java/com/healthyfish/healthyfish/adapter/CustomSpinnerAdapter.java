package com.healthyfish.healthyfish.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.model.SpinnerModel;

import java.util.ArrayList;

/**
 * Created by User on 22-09-2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    String cat_ids[];
    String[] cat_names;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, String[] cat_ids, String[] cat_names) {
        this.context = applicationContext;
        this.cat_ids = cat_ids;
        this.cat_names = cat_names;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return cat_ids.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView text_price_cat_id = (TextView) view.findViewById(R.id.text_price_cat_id);
        TextView text_price_cat_name = (TextView) view.findViewById(R.id.text_price_cat_name);
        text_price_cat_name.setText(cat_names[i]);
        text_price_cat_id.setText(cat_ids[i]);
        return view;
    }
}