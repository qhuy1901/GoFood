package com.example.myapplication.merchant.store_management.MerchantOrder.history_order;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentHistoryOrderBinding;
import com.example.myapplication.models.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderFragment extends Fragment {

    private FragmentHistoryOrderBinding binding;
    private RecyclerView rcvOrder;

    private HistoryOrderAdapter adapter;
    private List<Order> orders;

    private void initUi()
    {
        rcvOrder = binding.fragmentHistoryOrderRcvOrder;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvOrder .setLayoutManager(linearLayoutManager);

        orders = new ArrayList<>();
        adapter = new HistoryOrderAdapter(orders, getContext());
        rcvOrder.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getOrderFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("orders");

        SharedPreferences prefs = getActivity().getSharedPreferences("Session", MODE_PRIVATE);
        String storeId = prefs.getString("storeId", "No name defined");

        Query query = myRef.orderByChild("storeId").equalTo(storeId);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if(order != null && (order.getOrderStatus().contains("Đã hủy") || order.getOrderStatus().equals("Giao hàng thành công"))){
                    orders.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                orders.clear();
                Order order = snapshot.getValue(Order.class);
                if(order != null && (order.getOrderStatus().contains("Đã hủy") || order.getOrderStatus().equals("Giao hàng thành công"))){
                    orders.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();
        getOrderFromRealtimeDatabase();
        return root;
    }
}