package com.example.myapplication.merchant.store_management.MenuManagement.product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText etProductName, etPrice, etDescription;
    private SwitchCompat swIsAvailable;
    private Button btnSave, btnDeleteProduct;
    private GoFoodDatabase goFoodDatabase;
    private Product productInfo;
    private ImageView ivProductImage, ibBack;
    private TextView tvProductGrouping;
    private boolean[] selectedReason;
    private List<Integer> selectedList = new ArrayList<>();
    private List<String> productGroupingList;
    private String[] orderStatus ={"Đã tiếp nhận đơn hàng", "Đang vận chuyển", "Giao hàng thành công"};

    private void initUi()
    {
        etPrice = (EditText) findViewById(R.id.activity_update_product_et_price);
        etProductName = (EditText) findViewById(R.id.activity_update_product_et_product_name);
        etDescription = (EditText) findViewById(R.id.activity_update_product_et_product_description);
        btnSave= (Button) findViewById(R.id.activity_update_product_btn_save);
        swIsAvailable = (SwitchCompat) findViewById(R.id.activity_update_product_sw_is_available);
        btnDeleteProduct = (Button) findViewById(R.id.activity_update_product_btn_delete_product);
        ivProductImage =(ImageView) findViewById(R.id.activity_update_product_iv_product_image);
        ibBack = (ImageView) findViewById(R.id.activity_update_product_ib_back);
        tvProductGrouping = (TextView) findViewById(R.id.activity_update_product_tv_product_grouping);
        productGroupingList = new ArrayList<>();
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
        tvProductGrouping.setText(productInfo.getProductGrouping());
        Glide.with(this).load(productInfo.getProductImage()).into(ivProductImage);
        if(!productInfo.getProductDescription().equals("") || productInfo.getProductDescription().isEmpty())
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
        getProductGroupingListFromRealtimeDatabase();

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
                String productGrouping = tvProductGrouping.getText().toString();
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("Session", MODE_PRIVATE);
                String storeId = prefs.getString("storeId", "No name defined");
                int isAvailable = 0;
                if(swIsAvailable.isChecked())
                    isAvailable = 1;
                Product product= new Product(productInfo.getProductId(), productName, price, description, storeId, isAvailable, productInfo.getProductImage());
                product.setProductGrouping(productGrouping);
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

        tvProductGrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedItemIndex = 0;
                for(int i = 0; i < productGroupingList.size(); i++)
                {
                    if(productGroupingList.get(i).equals(productInfo.getProductGrouping()))
                    {
                        checkedItemIndex = i;
                        break;
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProductActivity.this);
                builder.setTitle("Chọn nhóm món");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(productGroupingList.toArray(new String[productGroupingList.size()]), checkedItemIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedList.add(i);
                        Collections.sort(selectedList);
                    }
                });

                builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newProductGrouping = productGroupingList.get(selectedList.get(0));
                        tvProductGrouping.setText(newProductGrouping);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    private void getProductGroupingListFromRealtimeDatabase()
    {
        SharedPreferences prefs = this.getSharedPreferences("Session", MODE_PRIVATE);
        String storeId = prefs.getString("storeId", "No name defined");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("productGrouping");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String productGrouping = postSnapshot.getValue(String.class);
                    productGroupingList.add(productGrouping);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProductActivity.this, "Không lấy được danh sách", Toast.LENGTH_SHORT).show();
            }
        });
    }


}