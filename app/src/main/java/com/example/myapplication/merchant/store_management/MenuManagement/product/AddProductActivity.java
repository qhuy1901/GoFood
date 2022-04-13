package com.example.myapplication.merchant.store_management.MenuManagement.product;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etPrice, etDescription;
    private SwitchCompat swIsAvailable;
    private Button btnAddProduct;
    private GoFoodDatabase goFoodDatabase;
    private ImageView ivProductAvatar;
    private AppCompatImageButton ibBack;

    private void initUi()
    {
        etPrice = (EditText) findViewById(R.id.et_price);
        etProductName = (EditText) findViewById(R.id.et_product_name);
        etDescription = (EditText) findViewById(R.id.et_product_description);
        btnAddProduct = (Button) findViewById(R.id.btn_add_product);
        swIsAvailable = (SwitchCompat) findViewById(R.id.sw_is_available);
        ivProductAvatar = (ImageView) findViewById(R.id.iv_product_avatar);
        ibBack = (AppCompatImageButton) findViewById(R.id.activity_add_product_ib_back);
    }

    private ActivityResultLauncher<Intent> checkPermission = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        ivProductAvatar.setImageURI(data.getData());
                    }
                }
            });

    private void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        checkPermission.launch(Intent.createChooser(intent, "Select Avatar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initUi();
        goFoodDatabase = new GoFoodDatabase();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
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

                Product product= new Product();
                product.setProductName(productName);
                product.setPrice(price);
                product.setProductDescription(description);
                product.setStoreId(storeId);
                product.setAvailable(isAvailable);
                goFoodDatabase.insertProduct(product, ivProductAvatar);
                Toast.makeText(getApplicationContext(), "Thêm món ăn mới thành công",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ivProductAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
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