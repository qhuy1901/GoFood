package com.example.myapplication.models;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Store implements Serializable
{
    private String storeId;
    private String storeName;
    private String storeCategory;
    private String owner;
    private String avatar;
    private Integer rating;
    private String deliveryTime;
    private String storeAddress;

    public Store(String storeId, String storeName, String storeCategory, String owner, String avatar, Integer rating, String deliveryTime, String storeAddress) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.owner = owner;
        this.avatar = avatar;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.storeAddress = storeAddress;
    }

    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    public String getAvatar() {
        return avatar;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public String getOwner() {
        return owner;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("storeId", storeId);
        result.put("storeName", storeName);
        result.put("storeCategory", storeCategory);
        result.put("storeAddress", storeAddress);
        result.put("owner", owner);
        result.put("avatar", avatar);
        result.put("rating", rating);
        result.put("deliveryTime", deliveryTime);
        return result;
    }
}
