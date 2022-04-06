package com.example.myapplication.models;

public class Home_RecommendedOrderModel {
    String img_url;
    String name;
    String deliveryTime;
    String deliveryType;
    String price;

    public Home_RecommendedOrderModel() { }
    public Home_RecommendedOrderModel(String img_url, String name, String deliveryTime, String deliveryType, String price){
        this.img_url = img_url;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}
