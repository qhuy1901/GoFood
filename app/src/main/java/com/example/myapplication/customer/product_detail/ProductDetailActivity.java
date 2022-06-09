package com.example.myapplication.customer.product_detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.customer.cart.CartActivity;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.customer.store_detail.ToppingBottomSheetDialog;
import com.example.myapplication.customer.store_detail.review_tab.ReviewForStoreDetailAdapter;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Review;
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

public class ProductDetailActivity extends AppCompatActivity {
    private Product productInfo;
    private Store storeInfo;
    private TextView tvPrice, tvProductName, tvDescription;
    private ImageView ivBtnback, tvProductImage;
    private ImageButton btnAdd;


    private void initUi() {
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

        if(productInfo.getAvailable() == 0)
            btnAdd.setVisibility(View.GONE);
    }

    private void receiveInfo()
    {
        Intent intent = getIntent();
        productInfo = (Product) intent.getSerializableExtra("product");
        storeInfo = (Store) intent.getSerializableExtra("store");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        receiveInfo();
        initUi();
        ivBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storeInfo == null){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("stores").child(productInfo.getStoreId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Store new_storeInfo = dataSnapshot.getValue(Store.class);
                            Intent switchActivityIntent = new Intent(ProductDetailActivity.this, StorePageDetailActivity.class);
                            switchActivityIntent.putExtra("store", new_storeInfo);
                            startActivity(switchActivityIntent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ProductDetailActivity.this, "Không lấy được store info", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    finish();
                }
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