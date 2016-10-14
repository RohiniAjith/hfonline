package com.healthyfish.healthyfish.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by User on 28-07-2016.
 */
public class HomeImagesItem implements Serializable{


public Integer drawable_image;

    public HomeImagesItem(Integer drawable_image) {
        this.drawable_image = drawable_image;
    }

    public Integer getDrawable_image() {
        return drawable_image;
    }

    public void setDrawable_image(Integer drawable_image) {
        this.drawable_image = drawable_image;
    }


}
