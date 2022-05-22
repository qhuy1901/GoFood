package com.example.myapplication.models;

import java.util.List;

public class ProductWithProductGrouping
{
    private String productGrouping;
    private List<Product> productList;

    public String getProductGrouping() {
        return productGrouping;
    }

    public void setProductGrouping(String productGrouping) {
        this.productGrouping = productGrouping;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
