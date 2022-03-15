package com.example.myapplication.models;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Store 
{
    public String storeId;
    public String storeName;
    public String storeCategory;
    public String description;
    public String owner;

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

    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Store(String storeId, String storeName, String storeCategory, String description, String owner) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.description = description;
        this.owner = owner;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("storeId", storeId);
        result.put("storeName", storeName);
        result.put("storeCategory", storeCategory);
        result.put("description", description);
        result.put("owner", owner);
        return result;
    }
}
