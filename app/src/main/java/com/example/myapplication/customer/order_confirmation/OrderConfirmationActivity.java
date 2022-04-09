package com.example.myapplication.customer.order_confirmation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.CartSession;

import java.util.List;

public class OrderConfirmationActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private CartSession cartSession;
    private List<CartItem> cart;
    private CartItemForOrderConfirmationAdapter adapter;

    private void initUi()
    {
        rcvProduct = (RecyclerView) findViewById(R.id.activity_order_confirmation_rcv_cart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(dividerItemDecoration);

        cartSession = new CartSession(OrderConfirmationActivity.this);
        cart = cartSession.getCart();
        adapter = new CartItemForOrderConfirmationAdapter( cart, this);
        rcvProduct.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        initUi();
    }
}