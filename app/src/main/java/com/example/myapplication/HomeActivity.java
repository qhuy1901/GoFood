package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.models.PopularProductModel;
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

    RecyclerView popularprodsRecyclerView;
    HomeAdapter popularprodsAdapter;
    List<PopularProductModel> popularprodsModelList;
    DatabaseReference realtimedbRef;
    FirebaseFirestore fire_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        if(WelcomeActivity.type_usr == 1) {
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

            popularprodsRecyclerView = findViewById(R.id.popular_prods_rec);
            popularprodsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            popularprodsModelList = new ArrayList<>();
            popularprodsAdapter = new HomeAdapter(this, popularprodsModelList);
            popularprodsRecyclerView.setAdapter(popularprodsAdapter);

            fire_store.collection("Popular_Category")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()){
                            PopularProductModel popularproduct = document.toObject(PopularProductModel.class);
                            popularprodsModelList.add(popularproduct);
                            popularprodsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }else{
            tvLocation.setText("MERCHANT LOGIN");
        }
    }
}