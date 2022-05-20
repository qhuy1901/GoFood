package com.example.myapplication.shipper.ui.shipper_order.receive_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ShipperReceiveOrderAdapter extends RecyclerView.Adapter<ShipperReceiveOrderAdapter.ShipperReceiveOrderViewHolder>
{
    private final List<Order> orders;
    private Context context;

    public ShipperReceiveOrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ShipperReceiveOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_for_shipper_order,parent,false);
        return new ShipperReceiveOrderAdapter.ShipperReceiveOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperReceiveOrderViewHolder holder, int position) {
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
        goFoodDatabase.loadUserFullnameToTextView(order.getUserId(), holder.tvCustomerName);
        goFoodDatabase.loadUserStoreNameToTextView(order.getStoreId(), holder.tvStoreName);
    }

    @Override
    public int getItemCount() {
        if(orders != null)
            return orders.size();
        return 0;
    }

    public class ShipperReceiveOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView tvShipFee, tvTotalPayable, tvTotalReceived, tvStoreName, tvCustomerName, tvOrderDate;
        private Button btnAccept, btnSkip;
        //private ConstraintLayout clParent;

        public ShipperReceiveOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShipFee = itemView.findViewById(R.id.item_order_for_shipper_order_tv_ship_fee);
            tvTotalPayable = itemView.findViewById(R.id.item_order_for_shipper_order_tv_total_payable);
            tvTotalReceived = itemView.findViewById(R.id.item_order_for_shipper_order_tv_total_received);
            tvStoreName = itemView.findViewById(R.id.item_order_for_shipper_order_tv_store_name);
            tvCustomerName  = itemView.findViewById(R.id.item_order_for_shipper_order_tv_customer_name);
            btnAccept = itemView.findViewById(R.id.item_order_for_shipper_order_btn_accept);
            btnSkip = itemView.findViewById(R.id.item_order_for_shipper_order_skip);
            tvOrderDate = itemView.findViewById(R.id.item_order_for_shipper_order_tv_order_date);
        }
    }
}
