package com.example.myapplication.customer.home.myorderpage.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Order;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomerOrderDetailActivity extends AppCompatActivity {
    private Order order;
    private TextView tvTotal, tvNumProduct, tvOrderId;
    private ImageView ivBtnBack;
    private RecyclerView rcvProductList;
    private ProductForCustomerOrderDetailAdapter adapter;
    private Button btnCancel;
    private void initUI(){
        tvTotal = (TextView) findViewById(R.id.customer_order_detail_total);
        tvNumProduct = (TextView) findViewById(R.id.customer_order_detail_num_product);
        tvOrderId = (TextView) findViewById(R.id.customer_order_detail_order_id);
        ivBtnBack = (ImageView) findViewById(R.id.customer_order_detail_btn_back);
        rcvProductList = (RecyclerView) findViewById(R.id.customer_order_detail_rcv);
        btnCancel = (Button)  findViewById(R.id.customer_order_detail_btn_cancel);

        btnCancel.setVisibility(View.GONE);
        if(order.getOrderStatus().equals("Đặt hàng thành công"))
            btnCancel.setVisibility(View.VISIBLE);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        tvTotal.setText(total);
        tvNumProduct.setText(order.getOrderDetail().size() + " Món");
        tvOrderId.setText(order.getOrderId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProductList.setLayoutManager(linearLayoutManager);

        adapter = new ProductForCustomerOrderDetailAdapter(order.getOrderDetail(), this);
        rcvProductList.setAdapter(adapter);
    }
    private void receiveOrderInfo()
    {
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail);
        receiveOrderInfo();
        initUI();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
