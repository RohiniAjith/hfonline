package com.healthyfish.healthyfish.model;

import com.healthyfish.healthyfish.adapter.CustomSpinnerAdapter;

/**
 * Created by User on 10-10-2016.
 */
public class ViewMoreListItem {
    String prod_id, mobile_title, malayalam_title,offer_price, actual_price,product_image;
    CustomSpinnerAdapter adapter;

    public ViewMoreListItem(String prod_id, String mobile_title, String malayalam_title, String offer_price, String actual_price, String product_image, CustomSpinnerAdapter adapter) {
        this.prod_id = prod_id;
        this.mobile_title = mobile_title;
        this.malayalam_title = malayalam_title;
        this.offer_price = offer_price;
        this.actual_price = actual_price;
        this.product_image = product_image;
        this.adapter = adapter;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getMobile_title() {
        return mobile_title;
    }

    public void setMobile_title(String mobile_title) {
        this.mobile_title = mobile_title;
    }

    public String getMalayalam_title() {
        return malayalam_title;
    }

    public void setMalayalam_title(String malayalam_title) {
        this.malayalam_title = malayalam_title;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public CustomSpinnerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomSpinnerAdapter adapter) {
        this.adapter = adapter;
    }
}
