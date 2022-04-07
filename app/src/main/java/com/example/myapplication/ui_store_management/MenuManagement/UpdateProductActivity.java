package com.example.myapplication.ui_store_management.MenuManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText etProductName, etPrice, etDescription;
    private SwitchCompat swIsAvailable;
    private Button btnSave, btnDeleteProduct;
    private GoFoodDatabase goFoodDatabase;
    private Product productInfo;
    private ImageView ivProductImage;
    private AppCompatImageButton ibBack;

    private void initUi()
    {
        etPrice = (EditText) findViewById(R.id.activity_update_product_et_price);
        etProductName = (EditText) findViewById(R.id.activity_update_product_et_product_name);
        etDescription = (EditText) findViewById(R.id.activity_update_product_et_product_description);
        btnSave= (Button) findViewById(R.id.activity_update_product_btn_save);
        swIsAvailable = (SwitchCompat) findViewById(R.id.activity_update_product_sw_is_available);
        btnDeleteProduct = (Button) findViewById(R.id.activity_update_product_btn_delete_product);
        ivProductImage =(ImageView) findViewById(R.id.activity_update_product_iv_product_image);
        ibBack = (AppCompatImageButton) findViewById(R.id.activity_update_product_ib_back);
    }

    private void receiveProductInfo()
    {
        Intent intent = getIntent();
        productInfo = (Product) intent.getSerializableExtra("product");
    }

    private void loadData()
    {
        etPrice.setText(Integer.toString(productInfo.getPrice()));
        etProductName.setText(productInfo.getProductName());
        etDescription.setText(productInfo.getProductDescription());
        if(productInfo.getAvailable() == 0)
        {
            swIsAvailable.setChecked(false);
        }
        else
        {
            swIsAvailable.setChecked(true);
        }
        if(productInfo.getProductImage().isEmpty() == false)
            goFoodDatabase.loadImageToImageView(ivProductImage, "product_image", productInfo.getProductImage());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        goFoodDatabase = new GoFoodDatabase();
        initUi();
        receiveProductInfo();
        loadData();

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(UpdateProductActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn có chắc muốn xóa món này ra khỏi thực đơn không?")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
//                                new SweetAlertDialog(UpdateProductActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Thành công")
//                                        .setContentText("Đã xóa món ra khỏi thực đơn!")
//                                        .show();
                                Toast.makeText(UpdateProductActivity.this, "Đã xóa món ra khỏi thực đơn!", Toast.LENGTH_SHORT).show();
                                goFoodDatabase.deleteProduct(productInfo, UpdateProductActivity.this);
                                finish();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = etProductName.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString());
                String description = etDescription.getText().toString();
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("Session", MODE_PRIVATE);
                String storeId = prefs.getString("storeId", "No name defined");
                int isAvailable = 0;
                if(swIsAvailable.isChecked())
                    isAvailable = 1;
                Product product= new Product(productInfo.getProductId(), productName, price, description, storeId, isAvailable, productInfo.getProductImage());
                goFoodDatabase.updateProduct(product);
                finish();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}