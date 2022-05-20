package com.example.myapplication.customer.store_detail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.cart.CartActivity;
import com.example.myapplication.customer.home.myorderpage.order_confirmation.OrderConfirmationActivity;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.Store;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StorePageDetailActivity extends AppCompatActivity {

    private ImageView ivStoreAvatar, ivShowCart, ivButtonBack;
    private TextView tvStoreName, tvTotal, tvRating, tvDeliveryTime;
    private Button btnDelivery;
    private TabLayout tablayout;
    private ViewPager2 viewPager;
    private ToppingBottomSheetDialog toppingBottomSheetDialog;


    private Store storeInfo;
    private GoFoodDatabase goFoodDatabase;
//    private List<Product> productList;
//    private RecyclerView rcvProduct;
    private CartSession cartSession;
//    private ProductForStoreDetailAdapter productForStoreDetailAdapter;


    private void initUi()
    {
        ivStoreAvatar = (ImageView) findViewById(R.id.store_img);
        ivShowCart = (ImageView) findViewById(R.id.store_show_cart);
        tvStoreName = (TextView) findViewById(R.id.store_name);
        tvTotal = (TextView) findViewById(R.id.store_total);
        btnDelivery = (Button) findViewById(R.id.store_btn_delivery);
        tvRating = (TextView) findViewById(R.id.store_rating);
        tvDeliveryTime = (TextView) findViewById(R.id.store_delivery_time);
        tablayout = findViewById(R.id.store_tbl);
        viewPager = findViewById(R.id.store_vp);
        ivButtonBack = (ImageView) findViewById(R.id.activity_store_detail_iv_btn_back) ;


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        initUi();
        receiveStoreInfo();
        final StorePageViewPagerAdapter adapter = new StorePageViewPagerAdapter(this, storeInfo);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tablayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đặt đơn");
                    break;
                case 1:
                    tab.setText("Bình luận");
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
        tvRating.setText(storeInfo.getRating().toString());
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
        ivButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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