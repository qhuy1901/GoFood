package com.example.myapplication.merchant.order_detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MerchantOrderDetailActivity extends AppCompatActivity {

    private Order order;
    private TextView tvCustomerName, tvTotal, tvCountProduct, tvOrderId;
    private ImageView ivBtnBack;
    private Button btnAccept, btnReject;
    private RecyclerView rcvProductList;
    private ProductForMerchantOrderDetailAdapter adapter;
    private boolean[] selectedReason;
    private List<Integer> selectedList = new ArrayList<>();
    private String[] cancelReason = {"Quán hết món", "Quán tạm nghỉ, không nhận đơn hàng này", "Tài xế từ chối vận chuyển đơn hàng"};
    private GoFoodDatabase goFoodDatabase;
    private void initUi()
    {
        tvCustomerName = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_customer_name);
        rcvProductList = (RecyclerView) findViewById(R.id.activity_merchant_order_detail_rcvOrder) ;
        tvTotal = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_total);
        tvCountProduct = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_count_product);
        tvOrderId = (TextView) findViewById(R.id.activity_merchant_order_detail_tv_order_id);
        ivBtnBack = (ImageView) findViewById(R.id.activity_merchant_order_detail_iv_btn_back);
        btnAccept = (Button)findViewById(R.id.activity_merchant_order_detail_btn_accept) ;
        btnReject  = (Button) findViewById(R.id.activity_merchant_order_detail_btn_reject);

        if(order.getOrderStatus().equals("Đặt hàng thành công") || order.getOrderStatus().contains("Đã hủy"))
        {
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        }

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
        goFoodDatabase = new GoFoodDatabase();
        receiveOrderInfo();
        initUi();
        loadDataToForm();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MerchantOrderDetailActivity.this);
                builder.setTitle("Chọn nguyên nhân hủy đơn hàng");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(cancelReason, selectedReason, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // Check condition
                        if(b){
                            // When checkbox selected
                            // Add position in day list
                            selectedList.add(i);
                            Collections.sort(selectedList);
                        }
                        else{
                            selectedList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        order.setOrderStatus("Đã hủy bởi quán");
                        order.setFinishTime(new Date());
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j = 0; j < selectedList.size(); j++)
                        {
                                // Concat array value
                               stringBuilder.append(cancelReason[selectedList.get(j)]);
                               if(j != selectedList.size() -1)
                               {
                                   stringBuilder.append(", ");
                               }
                        }
                        order.setCancelReason(stringBuilder.toString());
                        goFoodDatabase.updateOrder(order);
                        finish();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
}