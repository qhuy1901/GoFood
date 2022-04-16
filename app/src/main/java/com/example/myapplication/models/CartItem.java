package com.example.myapplication.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    public Product product;
    public int quantity;
    private  String note;
    private String topping;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Product product, int quantity, String topping) {
        this.product = product;
        this.quantity = quantity;
        this.topping = topping;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
}
