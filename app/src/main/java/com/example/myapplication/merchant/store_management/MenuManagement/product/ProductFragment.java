package com.example.myapplication.merchant.store_management.MenuManagement.product;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentProductBinding;
import com.example.myapplication.merchant.store_management.MenuManagement.product.product_grouping.ProductGroupingActivity;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.ProductWithProductGrouping;
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
    private ProductGroupingForProductFragmentAdapter adapter;
    private List<String> productGroupList;
    private List<ProductWithProductGrouping> productList;
    private TextView tvProductGrouping;
    private String storeId;

    private void initUi()
    {
        fabAddNewProduct = binding.fabAddNewProduct;
        rcvProduct = binding.rcvProductList;
        tvProductGrouping = binding.fragmentProductTvCategory;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvProduct.setLayoutManager(linearLayoutManager);

        productGroupList = new ArrayList<>();
        productList = new ArrayList<>();
        adapter = new ProductGroupingForProductFragmentAdapter(productList, getContext());
        rcvProduct.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Lấy mã cửa hàng
        SharedPreferences prefs = getContext().getSharedPreferences("Session", MODE_PRIVATE);
        storeId = prefs.getString("storeId", "No name defined");

        initUi();
        getProductGroupingListFromRealtimeDatabase();
        return root;
    }

    private void getProductGroupingListFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("productGrouping");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String productGrouping = postSnapshot.getValue(String.class);
                    productGroupList.add(productGrouping);
                    ProductWithProductGrouping productWithProductGrouping = new ProductWithProductGrouping();
                    productWithProductGrouping.setProductGrouping(productGrouping);
                    List<Product> pList = new ArrayList<>();
                    // Set các món vào nhóm món
                    database.getReference().child("stores").child(storeId).child("menu").child("products").orderByChild("productGrouping").equalTo(productGrouping).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pList.clear();
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                Product product = postSnapshot.getValue(Product.class);
                                pList.add(product);
                            }
                            productWithProductGrouping.setProductList(pList);
                            productList.add(productWithProductGrouping);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void getProductListFromRealtimeDatabase()
//    {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("menu").child("products");
//        Log.e("Hi111", "Đã chạy getProductListFromRealtimeDatabase - " + productGroupList.size());
//        for(String productGrouping : productGroupList)
//        {
//            // Set tên nhóm món
//            ProductWithProductGrouping productWithProductGrouping = new ProductWithProductGrouping();
//            productWithProductGrouping.setProductGrouping(productGrouping);
//            List<Product> pList = new ArrayList<>();
//            Log.e("Hi11", "Đã chạy getProductListFromRealtimeDatabase - " + productGrouping);
//
//            // Set các món vào nhóm món
//            myRef.orderByChild("productGrouping").equalTo(productGrouping).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    pList.clear();
//                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                        Product product = postSnapshot.getValue(Product.class);
//                        pList.add(product);
//                    }
//                    productWithProductGrouping.setProductList(pList);
//                    productList.add(productWithProductGrouping);
//                    adapter.notifyDataSetChanged();
//                    Log.e("Hi1", "Đã chạy getProductListFromRealtimeDatabase");
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

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
        tvProductGrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getContext(), ProductGroupingActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }


}
