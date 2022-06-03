package com.example.myapplication.customer.home.myorderpage.order_confirmation.address;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.home.myorderpage.order_confirmation.OrderConfirmationActivity;
import com.example.myapplication.customer.like.CustomerLikeAdapter;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.models.OrdAddress;
import com.example.myapplication.models.Store;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerOrdAddressAdapter extends RecyclerView.Adapter<CustomerOrdAddressAdapter.CustomerOrdAddressViewHolder> {
    private final List<OrdAddress> ordAddressList;
    private Context context;
    private Store storeInfo;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public CustomerOrdAddressAdapter(List<OrdAddress> ordAddressList, Context context, Store storeInfo) {
        this.ordAddressList = ordAddressList;
        this.context = context;
        this.storeInfo = storeInfo;
    }

    @NonNull
    @Override
    public CustomerOrdAddressAdapter.CustomerOrdAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent,false);
        return new CustomerOrdAddressAdapter.CustomerOrdAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrdAddressAdapter.CustomerOrdAddressViewHolder holder, int position) {
        OrdAddress ordAddress = ordAddressList.get(position);
        if(ordAddress == null)
            return;

        holder.address.setText(ordAddress.getAddress());
        holder.name.setText(ordAddress.getName());
        holder.phone.setText(ordAddress.getPhone_number());
        holder.clCustomerOrdAddress.setOnClickListener(new View.OnClickListener() {
            // xử lý set địa chỉ mới khi click chọn address
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Options")
                        .setContentText("Xóa hoặc chọn làm địa chỉ giao hàng")
                        .setConfirmText("Chọn làm địa chỉ")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent switchActivityIntent = new Intent(view.getContext(), OrderConfirmationActivity.class);
                                switchActivityIntent.putExtra("address", ordAddress);
                                switchActivityIntent.putExtra("store", storeInfo);
                                view.getContext().startActivity(switchActivityIntent);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Xóa", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                SharedPreferences prefs = view.getContext().getSharedPreferences("Session", view.getContext().MODE_PRIVATE);
                                String userId = prefs.getString("userId", "No name defined");

                                GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
                                goFoodDatabase.deleteOrdAddress(ordAddress.getPhone_number(), userId);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        if(ordAddressList != null)
            return ordAddressList.size();
        return 0;
    }

    public class CustomerOrdAddressViewHolder extends RecyclerView.ViewHolder {
        private TextView address;
        private TextView name;
        private TextView phone;
        private ConstraintLayout clCustomerOrdAddress;

        public CustomerOrdAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.item_add_address);
            name = (TextView) itemView.findViewById(R.id.item_add_name);
            phone = (TextView) itemView.findViewById(R.id.item_add_phone);
            clCustomerOrdAddress = (ConstraintLayout) itemView.findViewById(R.id.cl_item_address);
        }
    }
}
