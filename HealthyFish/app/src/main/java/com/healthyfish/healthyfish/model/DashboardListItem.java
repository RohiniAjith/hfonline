package com.healthyfish.healthyfish.model;

import com.healthyfish.healthyfish.adapter.CustomSpinnerAdapter;

import java.io.Serializable;

/**
 * Created by RohiniAjith on 3/26/2016.
 */
public class DashboardListItem implements Serializable {
    String title, description, thumb_images, id,orginal_images,Malayalam_title;
    CustomSpinnerAdapter spinnerAdapter;
    public DashboardListItem(String id, String title, String description, String thumb_images, String orginal_images,CustomSpinnerAdapter spinnerAdapter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumb_images = thumb_images;
        this.orginal_images=orginal_images;
    }
    public DashboardListItem(String id, String title, String description, String thumb_images, String orginal_images, String Malayalam_title) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumb_images = thumb_images;
        this.orginal_images=orginal_images;
        this.Malayalam_title=Malayalam_title;
        this.spinnerAdapter=spinnerAdapter;
    }

    public DashboardListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CustomSpinnerAdapter getSpinnerAdapter() {
        return spinnerAdapter;
    }

    public void setSpinnerAdapter(CustomSpinnerAdapter spinnerAdapter) {
        this.spinnerAdapter = spinnerAdapter;
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

    public String getMalayalam_title() {
        return Malayalam_title;
    }

    public void setMalayalam_title(String malayalam_title) {
        Malayalam_title = malayalam_title;
    }

    public void setThumb_images(String thumb_images) {
        this.thumb_images = thumb_images;
    }
}
