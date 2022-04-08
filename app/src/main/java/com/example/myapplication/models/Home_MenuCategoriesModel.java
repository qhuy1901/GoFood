package com.example.myapplication.models;

public class Home_MenuCategoriesModel {
    String img_url;
    String name;
    String deliveryTime;
    String deliveryType;
    String price;
    String rating;
    String note;

    public Home_MenuCategoriesModel() { }
    public Home_MenuCategoriesModel(String img_url, String name, String deliveryTime, String deliveryType, String price, String note, String rating){
        this.img_url = img_url;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.price = price;
        this.rating = rating;
        this.note = note;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
