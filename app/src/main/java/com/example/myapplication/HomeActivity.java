package com.example.myapplication;

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
import com.example.myapplication.models.Home_CategoriesOrderModel;
import com.example.myapplication.models.Home_MenuCategoriesModel;
import com.example.myapplication.models.Home_RecommendedOrderModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnBottom_Nav_HomeID = findViewById(R.id.bottom_nav_home);
        setFragment(new CateHomeFragment());
        btnBottom_Nav_HomeID.setSelectedItemId(R.id.nav_home);

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