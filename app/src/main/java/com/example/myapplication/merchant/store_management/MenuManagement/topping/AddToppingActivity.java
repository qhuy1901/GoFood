package com.example.myapplication.merchant.store_management.MenuManagement.topping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Topping;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddToppingActivity extends AppCompatActivity {

    private EditText etName, etPrice;
    private SwitchCompat scIsAvailable;
    private RecyclerView rcvProduct;
    private Button btnAdd;
    private ImageView ivBtnBack;
    private List<Product> productList;
    private ProductForAddToppingAdapter adapter;
    private List<String> applyProductList;
    private String storeId;

    private void initUi()
    {
        etName = (EditText) findViewById(R.id.activity_add_topping_et_name);
        etPrice = (EditText) findViewById(R.id.activity_add_topping_et_price);
        scIsAvailable = (SwitchCompat)  findViewById(R.id.activity_add_topping_sc_is_available);
        rcvProduct = (RecyclerView) findViewById(R.id.activity_add_topping_rcv_product);
        btnAdd = (Button) findViewById(R.id.activity_add_topping_btn_add);
        ivBtnBack = (ImageView) findViewById(R.id.activity_add_topping_ib_back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddToppingActivity.this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productList = new ArrayList<>();
        adapter = new ProductForAddToppingAdapter (productList, AddToppingActivity.this);
        rcvProduct.setAdapter(adapter);
    }

    private void getProductListFromRealtimeDatabase()
    {
        // Lấy mã cửa hàng
        SharedPreferences prefs = this.getSharedPreferences("Session", MODE_PRIVATE);
        storeId = prefs.getString("storeId", "No name defined");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("menu").child("products");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddToppingActivity.this, "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topping);

        initUi();
        getProductListFromRealtimeDatabase();
        applyProductList = new ArrayList<>();

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toppingName = etName.getText().toString();
                String toppingPrice = etPrice.getText().toString();
                int toppingStatus = 0;
                if(scIsAvailable.isChecked())
                    toppingStatus = 1;
                if(toppingName.isEmpty() ||  toppingPrice.isEmpty())
                {
                    new SweetAlertDialog(AddToppingActivity.this)
                            .setTitleText("Vui lòng điền đầy đủ thông tin!")
                            .show();
                }
                else
                {
                    GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
                    Topping topping = new Topping();
                    topping.setToppingName(toppingName);
                    topping.setToppingPrice(Integer.parseInt(toppingPrice));
                    topping.setToppingStatus(toppingStatus);
                    topping.setProductApplyList(applyProductList);
                    goFoodDatabase.insertTopping(topping, storeId);

                    Toast.makeText(AddToppingActivity.this, "Thêm topping thành công!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void addItemToApplyProductToList(String productName)
    {
        applyProductList.add(productName);
    }

    public void removeItemToApplyProductToList(String productName)
    {
        applyProductList.remove(productName);
    }
}