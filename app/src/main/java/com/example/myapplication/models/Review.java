package com.example.myapplication.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Review implements Serializable {
    private String user_cmt_name;
    private String cmt;
    private String cmt_date;

    public Review(){}

    public Review(String user_cmt_name, String cmt, String cmt_date) {
        this.user_cmt_name = user_cmt_name;
        this.cmt = cmt;
        this.cmt_date = cmt_date;
    }

    public String getUser_cmt_name() {
        return user_cmt_name;
    }

    public void setUser_cmt_name(String user_cmt_name) {
        this.user_cmt_name = user_cmt_name;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getCmt_date() {
        return cmt_date;
    }

    public void setCmt_date(String cmt_date) {
        this.cmt_date = cmt_date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_cmt_name", user_cmt_name);
        result.put("cmt", cmt);
        result.put("cmt_date", cmt_date);
        return result;
    }
}
