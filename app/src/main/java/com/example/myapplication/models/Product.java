package com.example.myapplication.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private int price;
    private String productDescription;
    private String storeId;
    private int available;
    private String productImage;

    public Product() {
    }

    public Product(String productId, String productName, int price, String productDescription, String storeId, int available, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.storeId = storeId;
        this.available = available;
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productId", productId);
        result.put("storeId", storeId);
        result.put("productName", productName);
        result.put("price", price);
        result.put("productDescription", productDescription);
        result.put("available", available);
        result.put("productImage", productImage);
        return result;
    }
}
