package com.healthyfish.healthyfish.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by RohiniAjith on 6/8/2016.
 */
public class HomeListItem implements Serializable {
    String new_arrival_product_name,new_arrival_malayalam, new_arrival_product_offer_percentage, new_arrival_product_cost, product_id,new_arrival_product_product_image;


    public HomeListItem(String product_id,String new_arrival_product_name,String new_arrival_malayalam, String new_arrival_product_cost, String new_arrival_product_offer_percentage, String new_arrival_product_product_image)
    {
        this.product_id=product_id;
        this.new_arrival_product_name=new_arrival_product_name;
        this.new_arrival_product_cost=new_arrival_product_cost;
        this.new_arrival_product_offer_percentage=new_arrival_product_offer_percentage;
        this.new_arrival_product_product_image=new_arrival_product_product_image;
        this.new_arrival_malayalam=new_arrival_malayalam;
    }

    public String getNew_arrival_product_name() {
        return new_arrival_product_name;
    }


    public void setNew_arrival_product_name(String new_arrival_product_name) {
        this.new_arrival_product_name = new_arrival_product_name;
    }

    public String getNew_arrival_product_offer_percentage() {
        return new_arrival_product_offer_percentage;
    }

    public void setNew_arrival_product_offer_percentage(String new_arrival_product_offer_percentage) {
        this.new_arrival_product_offer_percentage = new_arrival_product_offer_percentage;
    }

    public String getNew_arrival_product_cost() {
        return new_arrival_product_cost;
    }

    public void setNew_arrival_product_cost(String new_arrival_product_cost) {
        this.new_arrival_product_cost = new_arrival_product_cost;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNew_arrival_product_product_image() {
        return new_arrival_product_product_image;
    }

    public void setNew_arrival_product_product_image(String new_arrival_product_product_image) {
        this.new_arrival_product_product_image = new_arrival_product_product_image;
    }

    public String getNew_arrival_malayalam() {
        return new_arrival_malayalam;
    }

    public void setNew_arrival_malayalam(String new_arrival_malayalam) {
        this.new_arrival_malayalam = new_arrival_malayalam;
    }
}

