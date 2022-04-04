package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapter.CartItemAdapter;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCart;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cart;
    private CartSession cartModel;

    private void initUi()
    {
        rcvCart = (RecyclerView) findViewById(R.id.rcv_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        rcvCart.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CartActivity.this,DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(dividerItemDecoration);

        cartModel = new CartSession(CartActivity.this);
        cart = cartModel.getCart();
        cartItemAdapter  = new CartItemAdapter(cart, CartActivity.this);
        rcvCart.setAdapter(cartItemAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initUi();

    }
}