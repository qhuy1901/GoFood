package com.example.myapplication.customer.store_detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.LoginTabFragment;
import com.example.myapplication.R;
import com.example.myapplication.customer.cart.CartActivity;
import com.example.myapplication.customer.home.HomeActivity;
import com.example.myapplication.customer.home.myorderpage.order_confirmation.OrderConfirmationActivity;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.Review;
import com.example.myapplication.models.Store;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StorePageDetailActivity extends AppCompatActivity {

    private ImageView ivStoreAvatar, ivShowCart, ivButtonBack, ivStoreWishList;
    private TextView tvStoreName, tvTotal, tvRating, tvDeliveryTime;
    private Button btnDelivery;
    private TabLayout tablayout;
    private ViewPager2 viewPager;
    private ToppingBottomSheetDialog toppingBottomSheetDialog;
    private RatingBar storeRatingBar;


    private Store storeInfo;
    private GoFoodDatabase goFoodDatabase;
    private TextView tvStoreStatus;
    private CartSession cartSession;
    DatabaseReference realtimedbRef;


    private void initUi()
    {
        ivStoreAvatar = (ImageView) findViewById(R.id.store_img);
        ivShowCart = (ImageView) findViewById(R.id.store_show_cart);
        ivStoreWishList = (ImageView) findViewById(R.id.store_wishlist);
        tvStoreName = (TextView) findViewById(R.id.store_name);
        tvTotal = (TextView) findViewById(R.id.store_total);
        btnDelivery = (Button) findViewById(R.id.store_btn_delivery);
        tvRating = (TextView) findViewById(R.id.store_rating);
        tvDeliveryTime = (TextView) findViewById(R.id.store_delivery_time);
        tablayout = findViewById(R.id.store_tbl);
        viewPager = findViewById(R.id.store_vp);
        ivButtonBack = (ImageView) findViewById(R.id.activity_store_detail_iv_btn_back) ;
        tvStoreStatus = (TextView)  findViewById(R.id.store_delivery_time);
        storeRatingBar = (RatingBar) findViewById(R.id.store_ratingbar);

        cartSession = new CartSession(StorePageDetailActivity.this);

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
        String total = currencyVN.format(cartSession.getTotal()).replace("₫", "")+ " ₫";
        tvTotal.setText(total);
    }

    public void setToppingBottomSheetDialog(ToppingBottomSheetDialog toppingBottomSheetDialog) {
        this.toppingBottomSheetDialog = toppingBottomSheetDialog;
    }

    public ToppingBottomSheetDialog getToppingBottomSheetDialog() {
        return toppingBottomSheetDialog;
    }

    public void getRatingTotal(){
        realtimedbRef = FirebaseDatabase.getInstance().getReference();
        realtimedbRef.child("stores").child(storeInfo.getStoreId()).child("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float total = 0;
                int num_review = 0;
                for (DataSnapshot querysnapshot: snapshot.getChildren()) {
                    Review review = querysnapshot.getValue(Review.class);
                    total += review.getRating();
                    num_review += 1;
                }
                storeRatingBar.setRating(total / num_review);
                tvRating.setText(String.valueOf(total / num_review));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        receiveStoreInfo();
        initUi();
        getRatingTotal();

        final StorePageViewPagerAdapter adapter = new StorePageViewPagerAdapter(this, storeInfo);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tablayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đặt đơn");
                    break;
                case 1:
                    tab.setText("Đánh giá");
                    break;
                case 2:
                    tab.setText("Thông tin");
                    break;
            }
        }).attach();
        tablayout.setTranslationY(0);
        tablayout.setAlpha(1);

        updateTotalPrice();

        goFoodDatabase = new GoFoodDatabase();
        Glide.with(StorePageDetailActivity.this).load(storeInfo.getAvatar()).into(ivStoreAvatar);
        tvStoreName.setText(storeInfo.getStoreName());
        tvRating.setText(String.valueOf(storeInfo.getRating()));
        tvDeliveryTime.setText(storeInfo.getDeliveryTime());
        ivShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(StorePageDetailActivity.this, CartActivity.class);
                switchActivityIntent.putExtra("store", storeInfo);
                startActivity(switchActivityIntent);
            }
        });
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartSession.count() > 0)
                {
                    Intent switchActivityIntent = new Intent(StorePageDetailActivity.this, OrderConfirmationActivity.class);
                    switchActivityIntent.putExtra("store", storeInfo);
                    startActivity(switchActivityIntent);
                }
                else
                {
                    new SweetAlertDialog(StorePageDetailActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setContentText("Chưa có sản phẩm nào trong giỏ hàng")
                            .setCustomImage(R.drawable.empty_cart_icon)
                            .show();
                }
            }
        });

        ivStoreWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("Session", MODE_PRIVATE);
                String userId = prefs.getString("userId", "No name defined");

                goFoodDatabase.insertLoveStore(storeInfo, userId);
                // ivStoreWishList.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
                ivStoreWishList.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.red_heart));
            }
        });

        ivButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(StorePageDetailActivity.this, HomeActivity.class);
                switchActivityIntent.putExtra("store", storeInfo);
                startActivity(switchActivityIntent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        updateTotalPrice();
    }
}