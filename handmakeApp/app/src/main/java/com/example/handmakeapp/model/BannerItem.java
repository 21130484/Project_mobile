package com.example.handmakeapp.model;

public class BannerItem {
    private String title;
    private String description;
    private String img_path;

    public BannerItem() {
    }

    public BannerItem(String title, String description, String img_path) {
        this.title = title;
        this.description = description;
        this.img_path = img_path;
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

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}