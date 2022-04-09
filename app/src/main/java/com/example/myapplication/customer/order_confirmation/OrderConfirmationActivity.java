package com.example.myapplication.customer.order_confirmation;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.CartSession;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private CartSession cartSession;
    private List<CartItem> cart;
    private CartItemForOrderConfirmationAdapter adapter;
    private TextView tvTotal, tvApplyFee, tvDeliveryFee, tvSum;
    private SwitchCompat scDoorDelivery, scTakeEatingUtensils;
    private Button btnCofirm;
    private RadioGroup rgPaymentMethod;
    private RadioButton rbCash, rbOnlinePayment;
    private int sum = 0, deliveryFee = 0, applyFee = 0;

    private void initUi()
    {
        rcvProduct = (RecyclerView) findViewById(R.id.activity_order_confirmation_rcv_cart);
        tvSum = (TextView)findViewById(R.id.activity_order_confirmation_tv_sum) ;
        tvTotal = (TextView)findViewById(R.id.activity_order_confirmation_tv_total) ;
        tvApplyFee = (TextView)findViewById(R.id.activity_order_confirmation_tv_apply_fee) ;
        tvDeliveryFee = (TextView)findViewById(R.id.activity_order_confirmation_tv_delivery_fee);
        scDoorDelivery = (SwitchCompat)findViewById(R.id.activity_order_confirmation_sc_door_delivery);
        scTakeEatingUtensils= (SwitchCompat) findViewById(R.id.activity_order_confirmation_sc_take_eating_utensils);
        btnCofirm =(Button) findViewById(R.id.activity_order_confirmation_btn_confirm);
        rgPaymentMethod = (RadioGroup)findViewById(R.id.activity_order_confirmation_rg_payment_method);
        rbCash = (RadioButton)  findViewById(R.id.activity_order_confirmation_rb_cash);
        rbOnlinePayment = (RadioButton)findViewById(R.id.activity_order_confirmation_rb_online_payment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        cartSession = new CartSession(OrderConfirmationActivity.this);
        cart = cartSession.getCart();
        adapter = new CartItemForOrderConfirmationAdapter( cart, this);
        rcvProduct.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadInfoToForm()
    {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

        // Load sum cart
        sum = cartSession.getTotal();
        String sumString = currencyVN.format(sum).replace("₫", "")+ " ₫";
        tvSum.setText(sumString);

        // Load apply fee
        applyFee = 2000;
        String applyFeeString = currencyVN.format(applyFee).replace("₫", "")+ " ₫";
        tvApplyFee.setText(applyFeeString);

        // Load delivery fee
        deliveryFee = 0;
        String deliveryFeeString = currencyVN.format(deliveryFee).replace("₫", "")+ " ₫";
        tvDeliveryFee.setText(deliveryFeeString);

        // Load total fee
        int total = sum + applyFee + deliveryFee;
        String totalString = currencyVN.format(total).replace("₫", "")+ " ₫";
        tvTotal.setText(totalString);

        btnCofirm.setText("Đặt đơn - " + totalString);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        initUi();
        loadInfoToForm();
    }
}