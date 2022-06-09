package com.example.myapplication.models;

public class Home_RecommendedModel {
    String img_url;
    String name;
    String deliveryTime;
    String deliveryType;
    String price;
    String store_ID;
    String product_ID;

    public Home_RecommendedModel() { }

    public String getStore_ID() {
        return store_ID;
    }

    public void setStore_ID(String store_ID) {
        this.store_ID = store_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public Home_RecommendedModel(String img_url, String name, String deliveryTime, String deliveryType, String price, String store_ID, String product_ID){
        this.img_url = img_url;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.price = price;
        this.store_ID = store_ID;
        this.product_ID = product_ID;
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
