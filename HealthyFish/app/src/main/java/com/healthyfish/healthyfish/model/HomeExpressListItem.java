package com.healthyfish.healthyfish.model;

import java.io.Serializable;

/**
 * Created by User on 10-10-2016.
 */
public class HomeExpressListItem implements Serializable {
    String express_product_name,express_product_malayalam_name, express_product_offer_percentage, express_product_cost, product_id,express_product_product_image;

    public HomeExpressListItem(String product_id,String express_product_name,String express_product_malayalam_name,String express_product_cost, String express_product_offer_percentage,  String express_product_product_image) {
        this.express_product_name = express_product_name;
        this.express_product_offer_percentage = express_product_offer_percentage;
        this.express_product_cost = express_product_cost;
        this.product_id = product_id;
        this.express_product_product_image = express_product_product_image;
        this.express_product_malayalam_name=express_product_malayalam_name;
    }

    public String getExpress_product_name() {
        return express_product_name;
    }

    public void setExpress_product_name(String express_product_name) {
        this.express_product_name = express_product_name;
    }

    public String getExpress_product_offer_percentage() {
        return express_product_offer_percentage;
    }

    public void setExpress_product_offer_percentage(String express_product_offer_percentage) {
        this.express_product_offer_percentage = express_product_offer_percentage;
    }

    public String getExpress_product_cost() {
        return express_product_cost;
    }

    public void setExpress_product_cost(String express_product_cost) {
        this.express_product_cost = express_product_cost;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getExpress_product_product_image() {
        return express_product_product_image;
    }

    public void setExpress_product_product_image(String express_product_product_image) {
        this.express_product_product_image = express_product_product_image;
    }

    public String getExpress_product_malayalam_name() {
        return express_product_malayalam_name;
    }

    public void setExpress_product_malayalam_name(String express_product_malayalam_name) {
        this.express_product_malayalam_name = express_product_malayalam_name;
    }
}
