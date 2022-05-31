package com.example.myapplication.shipper.ui.shipper_order.receive_order;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Notification;
import com.example.myapplication.models.Order;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        goFoodDatabase.loadShippingAddressToTextViewByOrderId(order.getOrderId(), holder.tvCustomerName, holder.tvCustomerAddress);
        goFoodDatabase.loadStoreNameAndAddressToTextView(order.getStoreId(), holder.tvStoreName, holder.tvStoreAddress);
        holder.tvDistance.setText(order.getDistance() +" km");
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn có chắc muốn nhận đơn hàng này?").setCustomImage(R.drawable.shipper_icon)
                        .setConfirmText("Đồng ý")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                SharedPreferences prefs = context.getSharedPreferences("Session", MODE_PRIVATE);
                                String userId = prefs.getString("userId", "No name defined");
                                order.setShipperId(userId);
                                order.setOrderStatus("Đang vận chuyển");
                                goFoodDatabase.updateOrder(order);
                                createNotification(order);
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
        holder.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders.remove(order);
                notifyDataSetChanged();
            }
        });
    }

    private void createNotification(Order order)
    {
        Notification notification = new Notification();
        notification.setUserId(order.getUserId());
        notification.setTitle("Đơn hàng của bạn đang được vận chuyển");
        notification.setContent("Đơn hàng " + order.getOrderId()  + " của quý khách đã được shipper tiếp nhận và đang được vận chuyển. Vui lòng xem chi tiết đơn hàng để biết thông tin về người vận chuyển.");
        notification.setNotificationTime(new Date());
        GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
        goFoodDatabase.insertNotification(notification);
    }

    @Override
    public int getItemCount() {
        if(orders != null)
            return orders.size();
        return 0;
    }

    public class ShipperReceiveOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView tvShipFee, tvTotalPayable, tvTotalReceived, tvStoreName, tvCustomerName, tvOrderDate, tvStoreAddress, tvCustomerAddress, tvDistance;
        private Button btnAccept, btnSkip;
        private ConstraintLayout clParent;

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
            tvStoreAddress = itemView.findViewById(R.id.item_order_for_shipper_order_tv_start_address);
            tvCustomerAddress = itemView.findViewById(R.id.item_order_for_shipper_order_tv_end_address);
            tvDistance = itemView.findViewById(R.id.item_order_for_shipper_order_tv_distance);
            clParent = itemView.findViewById(R.id.item_order_for_shipper_order_cl_parent);
        }
    }
}
