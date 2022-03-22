package com.example.myapplication.models;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Store implements Serializable
{
    private String storeId;
    private String storeName;
    private String storeCategory;
    private String description;
    private String owner;
    private String avatar;

    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Store(String storeId, String storeName, String storeCategory, String description, String owner, String avatar) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.description = description;
        this.owner = owner;
        this.avatar = avatar;
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

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("storeId", storeId);
        result.put("storeName", storeName);
        result.put("storeCategory", storeCategory);
        result.put("description", description);
        result.put("owner", owner);
        result.put("avatar", avatar);
        return result;
    }
}
