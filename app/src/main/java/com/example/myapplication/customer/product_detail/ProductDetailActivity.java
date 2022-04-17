package com.example.myapplication.customer.product_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.customer.store_detail.ToppingBottomSheetDialog;
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private Product productInfo;
    private TextView tvPrice, tvProductName, tvDescription;
    private ImageView ivBtnback, tvProductImage;
    private ImageButton btnAdd;

    private void initUi()
    {
        ivBtnback = (ImageView) findViewById(R.id.activity_product_detail_iv_btn_back);
        tvProductImage = (ImageView) findViewById(R.id.activity_product_detail_tv_product_image);
        tvProductName = (TextView) findViewById(R.id.activity_product_detail_tv_product_name);
        tvDescription = (TextView) findViewById(R.id.activity_product_detail_tv_description);
        tvPrice = (TextView) findViewById(R.id.activity_product_detail_tv_price);
        btnAdd = (ImageButton) findViewById(R.id.activity_product_detail_item_plus);

        tvProductName.setText(productInfo.getProductName());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(productInfo.getPrice()).replace("₫", "")+ " ₫";
        tvPrice.setText(priceAfterFormat);

        tvDescription.setText(productInfo.getProductDescription());
        if(productInfo.getProductDescription().isEmpty())
            tvDescription.setVisibility(View.GONE);
        Glide.with(ProductDetailActivity.this).load(productInfo.getProductImage()).into(tvProductImage);
    }

    private void receiveProductInfo()
    {
        Intent intent = getIntent();
        productInfo = (Product) intent.getSerializableExtra("product");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        receiveProductInfo();
        initUi();
        ivBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToppingBottomSheetDialog toppingBottomSheetDialog = new ToppingBottomSheetDialog(ProductDetailActivity.this, productInfo);
                toppingBottomSheetDialog.show((ProductDetailActivity.this).getSupportFragmentManager(), "chooseTopping");
            }
        });
    }
}