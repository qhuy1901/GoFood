package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.adapter.Home_AllCateAdapter;
import com.example.myapplication.adapter.Home_CategoriesOrderAdapter;
import com.example.myapplication.adapter.Home_RecommendedAdapter;
import com.example.myapplication.customer.home.CateHomeFragment;
import com.example.myapplication.customer.home.CateOrderFragment;
import com.example.myapplication.adapter.StoreForHomeAdapter;

import com.example.myapplication.models.Home_CategoriesOrderModel;
import com.example.myapplication.models.Home_MenuCategoriesModel;
import com.example.myapplication.models.Home_RecommendedOrderModel;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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

    BottomNavigationView btnBottom_Nav_HomeID;


    private RecyclerView rcvStoreListByCategory;
    private List<Store> storeListByCategory;
    private  StoreForHomeAdapter storeForHomeAdapter;

    private void initUi()
    {
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        rcvStoreListByCategory = (RecyclerView) findViewById(R.id.rcv_store_list_by_category);

        storeListByCategory = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvStoreListByCategory.setLayoutManager(linearLayoutManager);
        storeForHomeAdapter = new StoreForHomeAdapter( storeListByCategory, HomeActivity.this);
        rcvStoreListByCategory.setAdapter(storeForHomeAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnBottom_Nav_HomeID = findViewById(R.id.bottom_nav_home);
        setFragment(new CateHomeFragment());
        btnBottom_Nav_HomeID.setSelectedItemId(R.id.nav_home);

        initUi();

        if(WelcomeActivity.type_usr == 1) {
            // get location
            getLocation();

            // get recommended order
            getRecommendedOrder();

            // categories order
            getCategoriesOrder();

            // category - allcate
//            getAllCateMenu();

            //
            getStoreListByCategoryFromRealtimeDatabase();
        }else{
            tvLocation.setText("MERCHANT LOGIN");
        }
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
                storeForHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
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
                storeForHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Không lấy được danh sách món", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void getAllCateMenu(){
        allcate_menu_RecyclerView = findViewById(R.id.menucate_rec);
        allcate_menu_RecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        allcate_menu_ModelList = new ArrayList<>();
        allcate_menu_Adapter = new Home_AllCateAdapter(this, allcate_menu_ModelList);
        allcate_menu_RecyclerView.setAdapter(allcate_menu_Adapter);


        btnBottom_Nav_HomeID.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        setFragment(new CateHomeFragment());
                        return true;
                    case R.id.nav_my_ords:
                        setFragment(new CateOrderFragment());
                        return true;
                }
                return false;
            }
        });
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_framelayout, fragment);
        fragmentTransaction.commit();
    }
}