package com.example.myapplication.customer.address;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CustomerAddressActivity extends AppCompatActivity {

    private ImageView ivBtnBack;
    private void initUi()
    {
        ivBtnBack = (ImageView) findViewById(R.id.activity_customer_address_iconBack);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        initUi();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}