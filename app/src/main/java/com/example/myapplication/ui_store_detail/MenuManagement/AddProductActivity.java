package com.example.myapplication.ui_store_detail.MenuManagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
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
import com.example.myapplication.models.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etPrice, etDescription;
    private SwitchCompat swIsAvailable;
    private Button btnAddProduct;
    private GoFoodDatabase goFoodDatabase;
    private ImageView ivProductAvatar;

    private void initUi()
    {
        etPrice = (EditText) findViewById(R.id.et_price);
        etProductName = (EditText) findViewById(R.id.et_product_name);
        etDescription = (EditText) findViewById(R.id.et_product_description);
        btnAddProduct = (Button) findViewById(R.id.btn_add_product);
        swIsAvailable = (SwitchCompat) findViewById(R.id.sw_is_available);
        ivProductAvatar = (ImageView) findViewById(R.id.iv_product_avatar);
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
    }



}