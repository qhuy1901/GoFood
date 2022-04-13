package com.example.myapplication.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product product;
    public int quantity;
}
