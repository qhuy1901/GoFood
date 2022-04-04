package com.example.myapplication.models;

public class Home_CategoriesOrderModel {
    String img_url;
    String name;
    String type;

    public Home_CategoriesOrderModel(){ }
    public Home_CategoriesOrderModel(String img_url, String name, String type){
        this.img_url = img_url;
        this.name = name;
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
