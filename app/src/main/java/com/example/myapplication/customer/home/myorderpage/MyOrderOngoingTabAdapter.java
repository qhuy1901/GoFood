package com.example.myapplication.customer.home.myorderpage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.customer.home.myorderpage.order_detail.CustomerOrderDetailActivity;
import com.example.myapplication.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyOrderOngoingTabAdapter extends RecyclerView.Adapter<MyOrderOngoingTabAdapter.MyOrderOngoingTabViewHolder>{
    private List<Order> orders;
    private Context context;
    private DatabaseReference Database;

    public MyOrderOngoingTabAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrderOngoingTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderOngoingTabAdapter.MyOrderOngoingTabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myorder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderOngoingTabViewHolder holder, int position) {
        Order order = orders.get(position);
        if(order == null)
            return;
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String total = currencyVN.format(order.getTotal()).replace("₫", "")+ " ₫";
        Log.d("total", total);
        Log.d("numproduct", order.getOrderDetail().size()+" sản phẩm");
        holder.tvTotal.setText(total);
        holder.tvOrderStatus.setText(order.getOrderStatus());
        holder.tvNumProduct.setText("(" + order.getOrderDetail().size()+" sản phẩm)");
        holder.tvOrderId.setText(order.getOrderId());

        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        holder.tvOrderDate.setText(dateFormat.format(order.getOrderDate()));

        String storeID = order.getStoreId();
        Database = FirebaseDatabase.getInstance().getReference();

        holder.btnReorder.setVisibility(View.GONE);
        holder.btnCheckReview.setVisibility(View.GONE);


        Database.child("stores").child(storeID).child("storeName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String storeName = snapshot.getValue(String.class);
                holder.tvStoreName.setText(storeName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Database.child("stores").child(storeID).child("avatar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String avatar = snapshot.getValue(String.class);
                Glide.with(context).load(avatar).into(holder.imgStore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOrderDetail(order);
            }
        });

    }
    private void goToOrderDetail(Order order)
    {
        Intent switchActivityIntent = new Intent(this.context, CustomerOrderDetailActivity.class);
        switchActivityIntent.putExtra("order", order);
        context.startActivity(switchActivityIntent);
    }
    @Override
    public int getItemCount() {
        if(orders != null)
            return orders.size();
        return 0;
    }

    public class MyOrderOngoingTabViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderDate, tvStoreName, tvStoreAddress, tvTotal, tvNumProduct, tvOrderStatus;
        private ImageView imgStore;
        private Button btnReorder, btnCheckReview;

        public MyOrderOngoingTabViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = (TextView) itemView.findViewById(R.id.tv_myorder_ongoing_orderid);
            tvOrderDate = (TextView) itemView.findViewById(R.id.tv_myorder_ongoing_orderdate);
            tvStoreName = (TextView) itemView.findViewById(R.id.myorder_ongoing_storename);
            tvStoreAddress = (TextView) itemView.findViewById(R.id.myorder_ongoing_storeaddress);
            tvTotal = (TextView) itemView.findViewById(R.id.myorder_ongoing_total);
            tvNumProduct = (TextView) itemView.findViewById(R.id.myorder_ongoing_numproduct);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.myorder_ongoing_orderstt);
            imgStore = (ImageView) itemView.findViewById(R.id.img_myorder_ongoing_storeimg);
            btnReorder = itemView.findViewById(R.id.myorder_ongoing_reorder_btn);
            btnCheckReview = itemView.findViewById(R.id.myorder_ongoing_checkstorereview_btn);
        }
    }
}
