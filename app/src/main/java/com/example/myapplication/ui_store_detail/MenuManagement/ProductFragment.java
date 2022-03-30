package com.example.myapplication.ui_store_detail.MenuManagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddProductActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.StartedActivity;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.adapter.StoreAdapter;
import com.example.myapplication.databinding.FragmentMenuManagementBinding;
import com.example.myapplication.databinding.FragmentProductBinding;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    private FragmentProductBinding binding;
    private FloatingActionButton fabAddNewProduct;
    private RecyclerView rcvProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private void initUi()
    {
        fabAddNewProduct = binding.fabAddNewProduct;
        rcvProduct = binding.rcvProductList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvProduct.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(dividerItemDecoration);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        rcvProduct.setAdapter(productAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();
        getProductListFromRealtimeDatabase();
        return root;
    }

    private void getProductListFromRealtimeDatabase()
    {
        // Lấy mã cửa hàng
        SharedPreferences prefs = getContext().getSharedPreferences("Session", MODE_PRIVATE);
        String storeId = prefs.getString("storeId", "No name defined");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("Menu");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getContext(), AddProductActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }


}
