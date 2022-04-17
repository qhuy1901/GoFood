package com.example.myapplication.customer.home.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.LoginTabFragment;
import com.example.myapplication.R;
import com.example.myapplication.WelcomeActivity;
import com.example.myapplication.models.CartSession;
import com.example.myapplication.models.Home_CategoriesItemModel;
import com.example.myapplication.models.Home_RecommendedModel;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private TextView tvLocation;
    DatabaseReference realtimedbRef;
    FirebaseFirestore fire_store = FirebaseFirestore.getInstance();

    RecyclerView categories_order_RecyclerView;
    CategoriesItemAdapter categories_order_Adapter;
    List<Home_CategoriesItemModel> categories_order_ModelList;

    RecyclerView recommended_order_RecyclerView;
    RecommendedAdapter recommended_order_Adapter;
    List<Home_RecommendedModel> recommended_order_ModelList;


    private RecyclerView rcvStoreListByCategory;
    private List<Store> storeListByCategory;
    private ListStoreAdapter listStoreAdapter;

    private void initUi(ViewGroup root)
    {

        CartSession cartSession = new CartSession(getActivity());
        cartSession.removeAllItem();

        tvLocation = (TextView) root.findViewById(R.id.tvLocation);

        rcvStoreListByCategory = (RecyclerView) root.findViewById(R.id.rcv_store_list_by_category);

        storeListByCategory = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        rcvStoreListByCategory.setLayoutManager(linearLayoutManager);
        listStoreAdapter = new ListStoreAdapter( storeListByCategory, getContext());
        rcvStoreListByCategory.setAdapter(listStoreAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_catehome, container, false);
        initUi(root);

        if(WelcomeActivity.type_usr == 1) {

            // get location
            getLocation();

            // get recommended order
            getRecommendedOrder(root);

            // categories order
            getCategoriesOrder(root);


            getStoreListByCategoryFromRealtimeDatabase();
        }else{
            tvLocation.setText("MERCHANT LOGIN");
        }
        // Inflate the layout for this fragment
        return root;
    }

    public void getStoreListByCategoryFromRealtimeDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeListByCategory.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Store store = postSnapshot.getValue(Store.class);
                    storeListByCategory.add(store);
                }
                listStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStoreListByCategoryFromRealtimeDatabase(String category)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeListByCategory.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Store store = postSnapshot.getValue(Store.class);
                    if(store.getStoreCategory().contains(category))
                    {
                        storeListByCategory.add(store);
                    }
                }
                listStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategoriesOrder(ViewGroup view){
        categories_order_RecyclerView = view.findViewById(R.id.categories_order_rec);
        categories_order_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categories_order_ModelList = new ArrayList<>();
        categories_order_Adapter = new CategoriesItemAdapter(getActivity(), categories_order_ModelList, this);
        categories_order_RecyclerView.setAdapter(categories_order_Adapter);

        fire_store.collection("Categories_Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Home_CategoriesItemModel categories_order = document.toObject(Home_CategoriesItemModel.class);
                                categories_order_ModelList.add(categories_order);
                                categories_order_Adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void getRecommendedOrder(ViewGroup view){
        recommended_order_RecyclerView = view.findViewById(R.id.recommended_rec);
        recommended_order_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommended_order_ModelList = new ArrayList<>();
        recommended_order_Adapter = new RecommendedAdapter(getActivity(), recommended_order_ModelList);
        recommended_order_RecyclerView.setAdapter(recommended_order_Adapter);

        fire_store.collection("Recommended_Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Home_RecommendedModel recommended_order = document.toObject(Home_RecommendedModel.class);
                                recommended_order_ModelList.add(recommended_order);
                                recommended_order_Adapter.notifyDataSetChanged();
                            }
                        }
                    }

                });
    }


    private void getLocation(){
        realtimedbRef = FirebaseDatabase.getInstance().getReference("Users");
        realtimedbRef.child(LoginTabFragment.UID).child("cur_location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cur_location = snapshot.getValue(String.class);
                tvLocation.setText(cur_location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}