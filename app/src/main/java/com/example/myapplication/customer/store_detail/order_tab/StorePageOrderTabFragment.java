package com.example.myapplication.customer.store_detail.order_tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StorePageOrderTabFragment extends Fragment {
    private Store storeInfo;
    private RecyclerView rcvProduct;
    private List<Product> productList;
    private ProductForStoreDetailAdapter productForStoreDetailAdapter;

    public StorePageOrderTabFragment(Store storeInfo) {
        this.storeInfo = storeInfo;
    }

    private void initUi(ViewGroup root)
    {
        rcvProduct = root.findViewById(R.id.rcv_store_product_list);
        productList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        rcvProduct.setLayoutManager(linearLayoutManager);
        productForStoreDetailAdapter = new ProductForStoreDetailAdapter(productList, getActivity());
        rcvProduct.setAdapter(productForStoreDetailAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_store_page_order_tab, container, false);

        initUi(root);
        getProductListFromRealtimeDatabase();
        return root;
    }

    private void getProductListFromRealtimeDatabase()
    {
        // Lấy mã cửa hàng
        String storeId = storeInfo.getStoreId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("menu").child("products");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productForStoreDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
    }
}