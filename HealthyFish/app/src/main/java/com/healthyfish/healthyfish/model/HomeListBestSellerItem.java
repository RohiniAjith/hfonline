package com.healthyfish.healthyfish.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by User on 28-07-2016.
 */
public class HomeListBestSellerItem implements Serializable {
    public String best_seller_prod_id, best_seller_product_name,best_seller_product__malayalam, best_seller_product_cost, best_seller_product_offer_percentage, best_seller_product_image;

    public HomeListBestSellerItem(String best_seller_prod_id, String best_seller_product_name,String best_seller_product__malayalam, String best_seller_product_cost, String best_seller_product_offer_percentage, String best_seller_product_image) {
        this.best_seller_prod_id = best_seller_prod_id;
        this.best_seller_product_name = best_seller_product_name;
        this.best_seller_product_cost = best_seller_product_cost;
        this.best_seller_product_offer_percentage = best_seller_product_offer_percentage;
        this.best_seller_product_image = best_seller_product_image;
    this.best_seller_product__malayalam=best_seller_product__malayalam;
    }

    public String getBest_seller_prod_id() {
        return best_seller_prod_id;
    }

    public void setBest_seller_prod_id(String best_seller_prod_id) {
        this.best_seller_prod_id = best_seller_prod_id;
    }

    public String getBest_seller_product_name() {
        return best_seller_product_name;
    }

    public void setBest_seller_product_name(String best_seller_product_name) {
        this.best_seller_product_name = best_seller_product_name;
    }

    public String getBest_seller_product_cost() {
        return best_seller_product_cost;
    }

    public void setBest_seller_product_cost(String best_seller_product_cost) {
        this.best_seller_product_cost = best_seller_product_cost;
    }

    public String getBest_seller_product_offer_percentage() {
        return best_seller_product_offer_percentage;
    }

    public void setBest_seller_product_offer_percentage(String best_seller_product_offer_percentage) {
        this.best_seller_product_offer_percentage = best_seller_product_offer_percentage;
    }

    public String getBest_seller_product_image() {
        return best_seller_product_image;
    }

    public void setBest_seller_product_image(String best_seller_product_image) {
        this.best_seller_product_image = best_seller_product_image;
    }

    public String getBest_seller_product__malayalam() {
        return best_seller_product__malayalam;
    }

    public void setBest_seller_product__malayalam(String best_seller_product__malayalam) {
        this.best_seller_product__malayalam = best_seller_product__malayalam;
    }
}