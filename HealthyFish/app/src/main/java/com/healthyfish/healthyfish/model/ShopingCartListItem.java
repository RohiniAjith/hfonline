package com.healthyfish.healthyfish.model;

/**
 * Created by User on 22-09-2016.
 */
public class ShopingCartListItem {
    String title,malayalam_title,price,quantity,image;
    String prod_id;

    public ShopingCartListItem(String prod_id,String title, String malayalam_title, String price, String quantity, String image) {
        this.title = title;
        this.malayalam_title = malayalam_title;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.prod_id=prod_id;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMalayalam_title() {
        return malayalam_title;
    }

    public void setMalayalam_title(String malayalam_title) {
        this.malayalam_title = malayalam_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
