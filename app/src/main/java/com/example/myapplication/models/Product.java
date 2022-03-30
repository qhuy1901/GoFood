package com.example.myapplication.models;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String productName;
    private int price;
    private String productDescription;
    private String storeId;
    private boolean isAvailable;

    public Product() {
    }

    public Product(String productName, int price, String productDescription, String storeId, boolean isAvailable) {
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.storeId = storeId;
        this.isAvailable = isAvailable;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productName", productName);
        result.put("price", price);
        result.put("productDescription", productDescription);
        result.put("isAvailable", isAvailable);
        return result;
    }
}
