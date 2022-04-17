package com.example.myapplication.customer.home.myorderpage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyOrderOngoingTabFragment extends Fragment {
    private RecyclerView rcvOrderOngoing;
    private MyOrderOngoingTabAdapter adapter;
    private List<Order> orders;

    private void initUI(ViewGroup root){
        rcvOrderOngoing = root.findViewById(R.id.myorder_ongoing_rcv);
        orders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvOrderOngoing.setLayoutManager(linearLayoutManager);
        adapter = new MyOrderOngoingTabAdapter(orders, getActivity());
        rcvOrderOngoing.setAdapter(adapter);
    }

    private void getOrderFromRealtimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("orders");

        SharedPreferences preferences = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
        String userId = preferences.getString("userId", "default value");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for (DataSnapshot querysnapshot: snapshot.getChildren()) {
                    Order order = querysnapshot.getValue(Order.class);
                    if (order != null) {
                        if (!order.getOrderStatus().contains("Đã hủy") && !order.getOrderStatus().equals("Giao hàng thành công") && order.getUserId().equals(userId)) {
                            orders.add(order);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_order_ongoing, container, false);
        initUI(root);
        getOrderFromRealtimeDatabase();
        return root;
    }
}