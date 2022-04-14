package com.example.myapplication.merchant.store_management.MerchantOrder.new_order;

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

import com.example.myapplication.databinding.FragmentNewOrderBinding;
import com.example.myapplication.models.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class NewOrderFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private String mParam1;
//    private String mParam2;

    private FragmentNewOrderBinding binding;
    private RecyclerView rcvOrder;

    private NewAndConfirmOrderAdapter adapter;
    private List<Order> orders;

    private void initUi()
    {
        rcvOrder = binding.fragmentNewOrderRcvOrder;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvOrder .setLayoutManager(linearLayoutManager);

        orders = new ArrayList<>();
        adapter = new NewAndConfirmOrderAdapter(orders, getContext());
        rcvOrder.setAdapter(adapter);
    }

    public NewOrderFragment() {
        // Required empty public constructor
    }


//    public static NewOrderFragment newInstance(String param1, String param2) {
//        NewOrderFragment fragment = new NewOrderFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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
                if(order != null && order.getOrderStatus().equals("Đặt hàng thành công")){
                    orders.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                orders.clear();
                Order order = snapshot.getValue(Order.class);
                if(order != null && order.getOrderStatus().equals("Đặt hàng thành công")){
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
        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();
        getOrderFromRealtimeDatabase();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}