package com.example.myapplication.customer.like;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerLikeFragment extends Fragment {
    private RecyclerView rcvStore;
    private List<Store> storeList;
    private CustomerLikeAdapter adapter;

    private void initUi(ViewGroup root)
    {
        rcvStore = (RecyclerView) root.findViewById(R.id.fragment_customer_like_rcv_store);

        storeList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        rcvStore.setLayoutManager(linearLayoutManager);
        adapter =  new CustomerLikeAdapter(storeList, getContext());
        rcvStore.setAdapter(adapter);
    }

    public void getStoreListByCategoryFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Store store = postSnapshot.getValue(Store.class);
                    if(store.getStoreCategory().contains("Đồ uống"))
                    {
                        storeList.add(store);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_customer_like, container, false);
        initUi(root);
        getStoreListByCategoryFromRealtimeDatabase();
        return root;
    }
}