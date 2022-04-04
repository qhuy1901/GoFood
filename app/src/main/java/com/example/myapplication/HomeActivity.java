package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.adapter.Home_CategoriesOrderAdapter;
import com.example.myapplication.adapter.Home_RecommendedAdapter;
import com.example.myapplication.models.Home_CategoriesOrderModel;
import com.example.myapplication.models.Home_RecommendedOrderModel;
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

public class HomeActivity extends AppCompatActivity {

    private TextView tvLocation;

    DatabaseReference realtimedbRef;
    FirebaseFirestore fire_store;

    RecyclerView categories_order_RecyclerView;
    Home_CategoriesOrderAdapter categories_order_Adapter;
    List<Home_CategoriesOrderModel> categories_order_ModelList;

    RecyclerView recommended_order_RecyclerView;
    Home_RecommendedAdapter recommended_order_Adapter;
    List<Home_RecommendedOrderModel> recommended_order_ModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        if(WelcomeActivity.type_usr == 1) {

            // get location
            getLocation();

            // get recommended order
            getRecommendedOrder();

            // categories order
            getCategoriesOrder();

            // category - food
        }else{
            tvLocation.setText("MERCHANT LOGIN");
        }
    }

    private void getCategoriesOrder(){
        categories_order_RecyclerView = findViewById(R.id.categories_order_rec);
        categories_order_RecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categories_order_ModelList = new ArrayList<>();
        categories_order_Adapter = new Home_CategoriesOrderAdapter(this, categories_order_ModelList);
        categories_order_RecyclerView.setAdapter(categories_order_Adapter);

        fire_store.collection("Categories_Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Home_CategoriesOrderModel categories_order = document.toObject(Home_CategoriesOrderModel.class);
                                categories_order_ModelList.add(categories_order);
                                categories_order_Adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void getRecommendedOrder(){
        recommended_order_RecyclerView = findViewById(R.id.recommended_rec);
        recommended_order_RecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recommended_order_ModelList = new ArrayList<>();
        recommended_order_Adapter = new Home_RecommendedAdapter(this, recommended_order_ModelList);
        recommended_order_RecyclerView.setAdapter(recommended_order_Adapter);

        fire_store.collection("Recommended_Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Home_RecommendedOrderModel recommended_order = document.toObject(Home_RecommendedOrderModel.class);
                                recommended_order_ModelList.add(recommended_order);
                                recommended_order_Adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void getLocation(){
        realtimedbRef = FirebaseDatabase.getInstance().getReference("Users");
        fire_store = FirebaseFirestore.getInstance();
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