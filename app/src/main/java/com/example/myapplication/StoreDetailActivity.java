package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.adapter.ProductForStoreDetailAdapter;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StoreDetailActivity extends AppCompatActivity {

    private ImageView ivStoreAvatar, ivShowCart;
    private TextView tvStoreName, tvTotal;
    private Store storeInfo;
    private GoFoodDatabase goFoodDatabase;
    private List<Product> productList;
    private RecyclerView rcvProduct;
    private CartSession cart;
    private ProductForStoreDetailAdapter productForStoreDetailAdapter;

    private void initUi()
    {
        ivStoreAvatar = (ImageView) findViewById(R.id.iv_store_avatar);
        tvStoreName = (TextView) findViewById(R.id.tv_store_name);
        rcvProduct = (RecyclerView) findViewById(R.id.rcv_product_list);
        ivShowCart = (ImageView) findViewById(R.id.iv_show_cart);
        tvTotal = (TextView) findViewById(R.id.tv_total);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(dividerItemDecoration);

        cart = new CartSession(StoreDetailActivity.this);
        productList = new ArrayList<>();
        productForStoreDetailAdapter = new ProductForStoreDetailAdapter(productList, this);
        rcvProduct.setAdapter(productForStoreDetailAdapter);
    }

    private void receiveStoreInfo()
    {
        Intent intent = getIntent();
        storeInfo = (Store) intent.getSerializableExtra("store");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateTotalPrice()
    {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(cart.getTotal());
        tvTotal.setText(total);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        initUi();
        receiveStoreInfo();
        getProductListFromRealtimeDatabase();
        updateTotalPrice();

        goFoodDatabase = new GoFoodDatabase();

        goFoodDatabase.loadImageToImageView(ivStoreAvatar, storeInfo.getAvatar());
        tvStoreName.setText(storeInfo.getStoreName());
        ivShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(StoreDetailActivity.this, CartActivity.class);
                startActivity(switchActivityIntent);
            }
        });

    }


    private void getProductListFromRealtimeDatabase()
    {
        // Lấy mã cửa hàng
        String storeId = storeInfo.getStoreId();

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
                productForStoreDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StoreDetailActivity.this, "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });

    }
}