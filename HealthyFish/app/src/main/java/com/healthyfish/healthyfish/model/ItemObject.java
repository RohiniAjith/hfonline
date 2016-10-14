package com.healthyfish.healthyfish.model;

public class ItemObject {

    private String name,imageName;
    private int imageId;

    public ItemObject(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public ItemObject(String name, String  imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

