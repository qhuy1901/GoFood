package com.example.myapplication.ui_store_detail.MenuManagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText etProductName, etPrice, etDescription;
    private SwitchCompat swIsAvailable;
    private Button btnSave, btnDeleteProduct;
    private GoFoodDatabase goFoodDatabase;
    private Product productInfo;

    private void initUi()
    {
        etPrice = (EditText) findViewById(R.id.activity_update_product_et_price);
        etProductName = (EditText) findViewById(R.id.activity_update_product_et_product_name);
        etDescription = (EditText) findViewById(R.id.activity_update_product_et_product_description);
        btnSave= (Button) findViewById(R.id.activity_update_product_btn_save);
        swIsAvailable = (SwitchCompat) findViewById(R.id.activity_update_product_sw_is_available);
        btnDeleteProduct = (Button) findViewById(R.id.activity_update_product_btn_delete_product);
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
        if(productInfo.isAvailable())
        {
            swIsAvailable.setChecked(true);
        }
        else
        {
            swIsAvailable.setChecked(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        initUi();
        receiveProductInfo();
        loadData();
        goFoodDatabase = new GoFoodDatabase();

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UpdateProductActivity.this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn xóa món này ra khỏi thực đơn không?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                goFoodDatabase.deleteProduct(productInfo, getApplicationContext());
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}