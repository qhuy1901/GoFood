package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.customer.home.CateHomeFragment;
import com.example.myapplication.customer.home.CateOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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