package com.example.myapplication.models;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Topping
{
    private String toppingId;
    private String toppingName;
    private int toppingPrice;
    private int toppingStatus;
    private List<String> productApplyList;

    public Topping() {
    }

    public String getToppingId() {
        return toppingId;
    }

    public void setToppingId(String toppingId) {
        this.toppingId = toppingId;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public int getToppingPrice() {
        return toppingPrice;
    }

    public void setToppingPrice(int toppingPrice) {
        this.toppingPrice = toppingPrice;
    }

    public int getToppingStatus() {
        return toppingStatus;
    }

    public void setToppingStatus(int toppingStatus) {
        this.toppingStatus = toppingStatus;
    }

    public List<String> getProductApplyList() {
        return productApplyList;
    }

    public void setProductApplyList(List<String> productApplyList) {
        this.productApplyList = productApplyList;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("toppingId", toppingId);
        result.put("toppingName", toppingName);
        result.put("toppingStatus", toppingStatus);
        result.put("toppingPrice", toppingPrice);
        result.put("productApplyList", productApplyList);
        return result;
    }
}
