package com.example.myapplication.shipper.ui.shipper_order.receive_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentShipperReceiveOrderBinding;
import com.example.myapplication.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShipperReceiveOrderFragment extends Fragment {

    private FragmentShipperReceiveOrderBinding binding;
    private RecyclerView rcvOrder;

    private ShipperReceiveOrderAdapter adapter;
    private List<Order> orders;
    private void initUi()
    {
        rcvOrder = binding.fragmentShipperReceiveOrderRcvOrder;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvOrder .setLayoutManager(linearLayoutManager);

        orders = new ArrayList<>();
        adapter = new ShipperReceiveOrderAdapter(orders, getContext());
        rcvOrder.setAdapter(adapter);
    }

    private void getOrderFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);
                    if(order != null && order.getOrderStatus().equals("Đã tiếp nhận đơn hàng")){
                        orders.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShipperReceiveOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();
        getOrderFromRealtimeDatabase();
        return root;
    }
}