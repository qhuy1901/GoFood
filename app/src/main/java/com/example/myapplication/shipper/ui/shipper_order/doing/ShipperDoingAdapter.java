package com.example.myapplication.shipper.ui.shipper_order.doing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.merchant.order_detail.ProductForMerchantOrderDetailAdapter;
import com.example.myapplication.models.Order;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ShipperDoingAdapter extends RecyclerView.Adapter<ShipperDoingAdapter.ShipperDoingViewHolder>
{
    private final List<Order> orders;
    private Context context;

    public ShipperDoingAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ShipperDoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_for_shipper_doing,parent,false);
        return new  ShipperDoingAdapter. ShipperDoingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperDoingViewHolder holder, int position) {
        Order order = orders.get(position);
        if(order == null)
            return ;
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String shipFee = currencyVN.format(order.getDeliveryFee()).replace("₫", "")+ " ₫";
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        String payable = currencyVN.format(order.getTotal() - order.getDeliveryFee() - order.getApplyFee()).replace("₫", "")+ " ₫";
        holder.tvShipFee.setText(shipFee);
        holder.tvTotalReceived.setText(total);
        holder.tvTotalPayable.setText(payable);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        holder.tvOrderDate.setText(dateFormat.format(order.getOrderDate()));
        GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
        goFoodDatabase.loadShippingAddressToTextViewByOrderId(order.getOrderId(), holder.tvCustomerName, holder.tvCustomerAddress);
        goFoodDatabase.loadStoreNameAndAddressToTextView(order.getStoreId(), holder.tvStoreName, holder.tvStoreAddress);
        holder.tvDistance.setText(order.getDistance() +" km");
        loadProductList(holder, order);
        if(order.getDoorDelivery() == 1)
            holder.ckbDoorDelivery.setChecked(true);
        if(order.getTakeEatingUtensils() == 1)
            holder.ckbTakeEatingUtensils.setChecked(true);
        holder.ckbDoorDelivery.setEnabled(false);
        holder.ckbTakeEatingUtensils.setEnabled(false);
        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn đã giao hàng thành công?").setCustomImage(R.drawable.shipper_icon)
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                order.setOrderStatus("Giao hàng thành công");
                                order.setFinishTime(new Date());
                                goFoodDatabase.updateOrder(order);
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

    private void loadProductList(@NonNull ShipperDoingViewHolder holder, Order order)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rcvProducts.setLayoutManager(linearLayoutManager);
        ProductForMerchantOrderDetailAdapter adapter = new ProductForMerchantOrderDetailAdapter(order.getOrderDetail(), context);
        holder.rcvProducts.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if(orders != null)
            return orders.size();
        return 0;
    }

    public class ShipperDoingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvShipFee, tvTotalPayable, tvTotalReceived, tvStoreName, tvCustomerName, tvOrderDate, tvStoreAddress, tvCustomerAddress, tvDistance;
        private Button btnDone;
        private RecyclerView rcvProducts;
        private CheckBox ckbDoorDelivery, ckbTakeEatingUtensils;

        public ShipperDoingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShipFee = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_ship_fee);
            tvTotalPayable = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_total_payable);
            tvTotalReceived = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_total_received);
            tvStoreName = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_store_name);
            tvCustomerName  = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_customer_name);
            btnDone = itemView.findViewById(R.id.item_order_for_shipper_doing_btn_accept);
            tvOrderDate = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_order_date);
            tvStoreAddress = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_start_address);
            tvCustomerAddress = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_end_address);
            tvDistance = itemView.findViewById(R.id.item_order_for_shipper_doing_tv_distance);
            rcvProducts = itemView.findViewById(R.id.item_order_for_shipper_doing_rcv_products);
            ckbDoorDelivery = itemView.findViewById(R.id.item_order_for_shipper_doing_ckb_door_delivery);
            ckbTakeEatingUtensils = itemView.findViewById(R.id.item_order_for_shipper_doing_ckb_take_eating_utensils);
        }
    }
}
