package com.healthyfish.healthyfish.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.model.ItemObject;

import java.util.List;



public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<ItemObject> listStorage;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        System.out.println("Image array"+listStorage);
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        Typeface font;
        if (convertView == null) {

            listViewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.nav_drawer_row, parent, false);
            font = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/fontawesome-webfont.ttf" );

            listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.textViewv);

            listViewHolder.txtimageInListView = (TextView) convertView.findViewById(R.id.txtimageInListView);
            listViewHolder.txtimageInListView.setTypeface(font);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getName());

       System.out.println("Image name::"+listStorage.get(position).getImageName());
        listViewHolder.txtimageInListView.setText(listStorage.get(position).getImageName());


        return convertView;
    }

    static class ViewHolder {

        TextView textInListView,txtimageInListView;
    }
}