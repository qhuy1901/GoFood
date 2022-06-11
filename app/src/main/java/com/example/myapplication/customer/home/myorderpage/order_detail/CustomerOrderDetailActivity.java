package com.example.myapplication.customer.home.myorderpage.order_detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerOrderDetailActivity extends AppCompatActivity {
    private Order order;
    private ImageView ivBtnBack;
    private RecyclerView rcvProductList;
    private ProductForCustomerOrderDetailAdapter adapter;
    private Button btnCancel;

    private TextView tvCustomerName, tvTotal, tvCountProduct, tvOrderId, tvChangeOrderStatus, tvDistance;
    private CheckBox ckbDoorDelivery, ckbTakeEatingUtensils;
    private Button btnAccept, btnReject;
    private TextView tvOrderDate, tvOrderStatus, tvSubTotal, tvShipper, tv16, tvShipperName, tvShipperPhone, tvReceiver, tvApplyFee, tvShipFee, tvReceiverPhone, tvShippingAddress, tvStoreName, tvStoreAddress;
    
    private void initUI(){
//        tvTotal = (TextView) findViewById(R.id.activity_customer_order_detail_total);
//        tvNumProduct = (TextView) findViewById(R.id.activity_customer_order_detail_num_product);
//        tvOrderId = (TextView) findViewById(R.id.activity_customer_order_detail_order_id);
//        ivBtnBack = (ImageView) findViewById(R.id.activity_customer_order_detail_btn_back);
        rcvProductList = (RecyclerView) findViewById(R.id.activity_customer_order_detail_rcvOrder);
        btnCancel = (Button)  findViewById(R.id.activity_customer_order_detail_btn_cancel);

        tvCustomerName = (TextView) findViewById(R.id.activity_customer_order_detail_tv_customer_name);
        rcvProductList = (RecyclerView) findViewById(R.id.activity_customer_order_detail_rcvOrder) ;
        tvTotal = (TextView) findViewById(R.id.activity_customer_order_detail_tv_total);
        tvCountProduct = (TextView) findViewById(R.id.activity_customer_order_detail_tv_count_product);
        tvOrderId = (TextView) findViewById(R.id.activity_customer_order_detail_tv_order_id);
        ivBtnBack = (ImageView) findViewById(R.id.activity_customer_order_detail_iv_btn_back);
        btnAccept = (Button)findViewById(R.id.activity_customer_order_detail_btn_accept) ;
        btnReject  = (Button) findViewById(R.id.activity_customer_order_detail_btn_reject);

        tvOrderDate =  (TextView) findViewById(R.id.activity_customer_order_detail_tv_order_date);
        tvOrderStatus = (TextView) findViewById(R.id.activity_customer_order_detail_tv_oder_status);
        tvReceiver = (TextView) findViewById(R.id.activity_customer_order_detail_receiver);
        tvReceiverPhone = (TextView) findViewById(R.id.activity_customer_order_detail_tv_phone);
        tvShippingAddress = (TextView) findViewById(R.id.activity_customer_order_detail_tv_shipping_address);
        tvStoreName = (TextView) findViewById(R.id.activity_customer_order_detail_tv_store_name);
        tvStoreAddress = (TextView) findViewById(R.id.activity_customer_order_detail_tv_store_address);
        ckbDoorDelivery = (CheckBox) findViewById(R.id.activity_customer_order_detail_ckb_door_delivery);
        ckbTakeEatingUtensils = (CheckBox) findViewById(R.id.activity_customer_order_detail_ckb_take_eating_utensils);
        tvShipFee =  (TextView) findViewById(R.id.activity_customer_order_detail_tv_shipping_fee);
        tvShipperName = (TextView) findViewById(R.id.activity_customer_order_detail_tv_shipper_name);
        tvShipperPhone = (TextView) findViewById(R.id.activity_customer_order_detail_tv_shipper_phone);
        tvApplyFee = (TextView) findViewById(R.id.activity_customer_order_detail_tv_apply_fee);
        tvShipper  = (TextView) findViewById(R.id.activity_customer_order_detail_tv_shipper);
        tv16 =  (TextView) findViewById(R.id.activity_customer_order_detail_tv_16);
        tvSubTotal =  (TextView) findViewById(R.id.activity_customer_order_detail_tv_sub_total);
        tvDistance = (TextView) findViewById(R.id.activity_customer_order_detail_tv_distance);
        
        btnCancel.setVisibility(View.GONE);
        if(order.getOrderStatus().equals("Đặt hàng thành công") || order.getOrderStatus().equals("Đã tiếp nhận đơn hàng") )
            btnCancel.setVisibility(View.VISIBLE);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        tvTotal.setText(total);
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
    private void loadDataToForm()
    {
        GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
        goFoodDatabase.loadUserFullnameToTextView(order.getUserId(), tvCustomerName);
        tvDistance.setText("(" + order.getDistance() +"km)");
        if(order.getShipperId().equals(" "))
        {
            tvShipperName.setVisibility(View.GONE);
            tvShipperPhone.setVisibility(View.GONE);
            tvShipper.setVisibility(View.GONE);
            tv16.setVisibility(View.GONE);
        }
        else
            goFoodDatabase.loadUserFullNameAndPhoneToTextView(order.getShipperId(), tvShipperName, tvShipperPhone);
        tvOrderStatus.setText(order.getOrderStatus());
        if(order.getOrderStatus().contains("Đã hủy"))
        {
            tvOrderStatus.setText("Đã hủy");
            tvOrderStatus.setTextColor(Color.RED);
        }
        else
        {
            tvOrderStatus.setText(order.getOrderStatus());
        }
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        tvTotal.setText(total);
        tvCountProduct.setText("(" + order.getOrderDetail().size() + " món)");
        tvOrderId.setText(order.getOrderId());
        int subTotal = 0;
        if(order.getDoorDelivery() == 1)
        {
            ckbDoorDelivery.setChecked(true);
            subTotal += 5000;
        }
        subTotal = subTotal + order.getTotal() - order.getApplyFee() - order.getDeliveryFee();
        if(order.getTakeEatingUtensils() == 1)
            ckbTakeEatingUtensils.setChecked(true);
        ckbDoorDelivery.setEnabled(false);
        ckbTakeEatingUtensils.setEnabled(false);
        String shipFee = currencyVN.format(order.getDeliveryFee()).replace("₫", "")+ " ₫";
        String applyFee = currencyVN.format(order.getApplyFee()).replace("₫", "")+ " ₫";
        String subTotalString = currencyVN.format(subTotal).replace("₫", "")+ " ₫";
        tvApplyFee.setText(applyFee);
        tvShipFee.setText(shipFee);
        tvSubTotal.setText(subTotalString);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        tvOrderDate.setText(dateFormat.format(order.getOrderDate()));
        goFoodDatabase.loadShippingAddressToTextViewByOrderId(order.getOrderId(), tvReceiver, tvShippingAddress);
        goFoodDatabase.loadStoreNameAndAddressToTextView(order.getStoreId(), tvStoreName, tvStoreAddress);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail);
        receiveOrderInfo();
        initUI();
        loadDataToForm();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(CustomerOrderDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn có chắc muốn hủy đơn hàng này?")
                        .setConfirmText("Đồng ý")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                order.setOrderStatus("Đã hủy bởi khách");
                                GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
                                goFoodDatabase.updateOrder(order);
                                btnCancel.setVisibility(View.GONE);
                                tvOrderStatus.setText("Đã hủy");
                                tvOrderStatus.setTextColor(Color.RED);
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }
}
