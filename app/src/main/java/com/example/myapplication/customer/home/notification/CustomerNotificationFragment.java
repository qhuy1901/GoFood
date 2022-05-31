package com.example.myapplication.customer.home.notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerNotificationFragment extends Fragment {

    private RecyclerView rcvNotification;
    private List<Notification> list;
    private CustomerNotificationAdapter adapter;
    
    private void initUi(ViewGroup root)
    {
        rcvNotification = (RecyclerView) root.findViewById(R.id.fragment_customer_notification_rcv_notification);

        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        rcvNotification.setLayoutManager(linearLayoutManager);
        adapter =  new CustomerNotificationAdapter(list, getContext());
        rcvNotification.setAdapter(adapter);
    }

    public void getNotificationList()
    {

        SharedPreferences prefs = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
        String userId = prefs.getString("userId", "No name defined");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(userId).child("notifications");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Notification notification = postSnapshot.getValue(Notification.class);
                    list.add(notification);
                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Không lấy được danh sách", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_customer_notification, container, false);
        initUi(root);
        getNotificationList();
        return root;
    }
}