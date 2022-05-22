package com.example.myapplication.shipper.ui.shipper_order.done;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.merchant.order_detail.MerchantOrderDetailActivity;
import com.example.myapplication.models.Order;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShipperDoneAdapter extends RecyclerView.Adapter<ShipperDoneAdapter.ShipperDoneViewHolder>{
    private final List<Order> orders;
    private Context context;

    public  ShipperDoneAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public  ShipperDoneAdapter. ShipperDoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_for_shipper_done,parent,false);
        return new  ShipperDoneAdapter. ShipperDoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ShipperDoneAdapter.ShipperDoneViewHolder holder, int position) {
        Order order = orders.get(position);
        if(order == null)
            return ;
        holder.tvOrder.setText((position+1) + "");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String shipFee = currencyVN.format(order.getDeliveryFee()).replace("₫", "")+ " ₫";
        String totalReceived = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        String totalPayable = currencyVN.format(order.getTotal() - order.getApplyFee() - order.getDeliveryFee()).replace("₫", "")+ " ₫";
        holder.tvTotalPayable.setText(totalPayable);
        holder.tvTotalReceived.setText(totalReceived);
        holder.tvShipFee.setText(shipFee);
        holder.tvOrderStatus.setText(order.getOrderStatus());
        holder.tvDistance.setText(order.getDistance() + "km");
        holder.tvCountProduct.setText(order.getOrderDetail().size()+"");
        holder.tvOrderId.setText(order.getOrderId());

        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        holder.tvOrderDate.setText(dateFormat.format(order.getOrderDate()));

        GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
        goFoodDatabase.loadUserFullnameToTextView(order.getUserId(), holder.tvFullName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOrderDetail(order);
            }
        });
    }

    private void goToOrderDetail(Order order)
    {
        Intent switchActivityIntent = new Intent(this.context, MerchantOrderDetailActivity.class);
        switchActivityIntent.putExtra("order", order);
        context.startActivity(switchActivityIntent);
    }

    @Override
    public int getItemCount() {
        if(orders != null)
            return orders.size();
        return 0;
    }

    public class  ShipperDoneViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOrderDate, tvOrderStatus, tvDistance, tvFullName, tvShipFee, tvTotalPayable, tvTotalReceived, tvCountProduct, tvOrderId, tvOrder;
        private ConstraintLayout clParent;

        public  ShipperDoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderDate = itemView.findViewById(R.id.item_order_for_shipper_done_tv_order_date);
            tvOrderStatus = itemView.findViewById(R.id.item_order_for_shipper_done_tv_order_status);
            tvFullName = itemView.findViewById(R.id.item_order_for_shipper_done_tv_full_name);
            tvTotalPayable = itemView.findViewById(R.id.item_order_for_shipper_done_tv_total_payable);
            tvTotalReceived = itemView.findViewById(R.id.item_order_for_shipper_done_tv_total_received);
            tvCountProduct = itemView.findViewById(R.id.item_order_for_shipper_done_tv_count_product);
            tvOrderId = itemView.findViewById(R.id.item_order_for_shipper_done_tv_order_id);
            tvDistance = itemView.findViewById(R.id.item_order_for_shipper_done_tv_distance);
            clParent = itemView.findViewById(R.id.item_order_for_shipper_done_cl_parent);
            tvOrder = itemView.findViewById(R.id.item_order_for_shipper_done_tv_order);
            tvShipFee = itemView.findViewById(R.id.item_order_for_shipper_done_tv_ship_fee);
        }
    }
}
