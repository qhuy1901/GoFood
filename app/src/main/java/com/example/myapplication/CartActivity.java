package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.CartItemAdapter;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.ui_store_management.MenuManagement.UpdateProductActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCart;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cart;
    private CartSession cartSession;
    private TextView tvDeleteAllItem, tvTotal;
    private ImageView ivEmptyCart;

    private void initUi()
    {
        rcvCart = (RecyclerView) findViewById(R.id.rcv_cart);
        tvDeleteAllItem = (TextView) findViewById(R.id.tv_delete_all_cart_item);
        ivEmptyCart = (ImageView) findViewById(R.id.iv_empty_cart);
        tvTotal = (TextView) findViewById(R.id.tv_total) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        rcvCart.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(CartActivity.this,DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(dividerItemDecoration);

        cartSession = new CartSession(CartActivity.this);
        cart = cartSession.getCart();
        cartItemAdapter  = new CartItemAdapter(cart, CartActivity.this);
        rcvCart.setAdapter(cartItemAdapter);
        checkEmptyCartImageView();
    }

    public void checkEmptyCartImageView()
    {
        cart = cartSession.getCart();
        if(cart.size() == 0)
        {
            ivEmptyCart.setVisibility(View.VISIBLE);
            rcvCart.setVisibility(View.GONE);
        }
        else
        {
            ivEmptyCart.setVisibility(View.GONE);
            rcvCart.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTotalPrice()
    {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(cartSession.getTotal());
        tvTotal.setText(total);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initUi();
        updateTotalPrice();
        tvDeleteAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(CartActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xóa tất cả món")
                        .setContentText("Bạn có muốn xóa tất cả món trong giỏ hàng?")
                        .setConfirmText("Xóa tất cả")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                cartSession.removeAllItem();
                                checkEmptyCartImageView();
//                                finish();
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }
}