package com.example.myapplication.merchant.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;

import java.text.NumberFormat;
import java.util.Locale;

public class MerchantOrderDetailActivity extends AppCompatActivity {

    private Order order;
    private TextView tvCustomerName, tvTotal, tvCountProduct, tvOrderId;
    private ImageView ivBtnBack;
    private RecyclerView rcvProductList;
    private ProductForMerchantOrderDetailAdapter adapter;

    private void initUi()
    {
        tvCustomerName = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_customer_name);
        rcvProductList = (RecyclerView) findViewById(R.id.activity_merchant_order_detail_rcvOrder) ;
        tvTotal = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_total);
        tvCountProduct = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_count_product);
        tvOrderId = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_order_id);
        ivBtnBack = (ImageView) findViewById(R.id.activity_merchant_order_detail_iv_btn_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MerchantOrderDetailActivity.this);
        rcvProductList.setLayoutManager(linearLayoutManager);

        adapter = new ProductForMerchantOrderDetailAdapter( order.getOrderDetail(), MerchantOrderDetailActivity.this);
        rcvProductList.setAdapter(adapter);
    }

    private void receiveOrderInfo()
    {
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
    }

    private void loadDataToForm()
    {
        GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
        goFoodDatabase.loadUserFullnameToTextView(order.getUserId(), tvCustomerName);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        tvTotal.setText(total);
        tvCountProduct.setText(order.getOrderDetail().size() + " Món");
        tvOrderId.setText("Mã đơn hàng: " + order.getOrderId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_order_detail);
        receiveOrderInfo();
        initUi();
        loadDataToForm();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}