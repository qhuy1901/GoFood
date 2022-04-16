package com.example.myapplication.merchant.choose_store;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentViewStoreBinding;
import com.example.myapplication.models.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ViewStoreFragment extends Fragment{
    private Context context;
    private FragmentViewStoreBinding binding;
    private RecyclerView rcvStores;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;

    private void initUi()
    {
        rcvStores = binding.rcvStore;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvStores.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcvStores.addItemDecoration(dividerItemDecoration);

        storeList = new ArrayList<>();
        storeAdapter = new StoreAdapter(getContext(),storeList);
        rcvStores.setAdapter(storeAdapter);
    }

    private void getStoreListFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores");

        SharedPreferences preferences = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
        String owner = preferences.getString("userId", "default value");

        Query query = myRef.orderByChild("owner").equalTo(owner);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //storeList.clear();
                Store store = snapshot.getValue(Store.class);
                if(store != null){
                    storeList.add(store);
                }
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Store store = snapshot.getValue(Store.class);
                if(store != null){
                    storeList.add(store);
                }
                storeAdapter.notifyDataSetChanged();
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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentViewStoreBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        getStoreListFromRealtimeDatabase();
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewStoreFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}