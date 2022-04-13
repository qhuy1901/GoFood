package com.example.myapplication.models;

public class CartItem {
    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product product;
    public int quantity;
}
