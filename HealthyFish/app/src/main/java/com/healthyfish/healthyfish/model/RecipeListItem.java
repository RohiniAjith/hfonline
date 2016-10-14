package com.healthyfish.healthyfish.model;

import java.io.Serializable;

/**
 * Created by User on 13-10-2016.
 */
public class RecipeListItem implements Serializable {
    String title, description, thumb_images, id,orginal_images,icons;

    public RecipeListItem(String id, String title, String description, String thumb_images,String orginal_images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumb_images = thumb_images;
        this.orginal_images=orginal_images;
    }
    public RecipeListItem(String id, String title, String description, String thumb_images,String orginal_images,String icons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumb_images = thumb_images;
        this.orginal_images=orginal_images;
        this.icons=icons;
    }

    public RecipeListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getOrginal_images() {
        return orginal_images;
    }

    public void setOrginal_images(String orginal_images) {
        this.orginal_images = orginal_images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb_images() {

        return thumb_images;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public void setThumb_images(String thumb_images) {
        this.thumb_images = thumb_images;
    }
}
