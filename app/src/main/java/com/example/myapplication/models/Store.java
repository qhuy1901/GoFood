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
    public Map<String, Boolean> stars = new HashMap<>();

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
