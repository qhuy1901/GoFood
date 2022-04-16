package com.example.myapplication.merchant.store_management;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityStoreManagementBinding;
import com.example.myapplication.models.Store;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreManagementActivity extends AppCompatActivity {

    private ActivityStoreManagementBinding binding;
    private Store store;
//    private TextView tvStoreName;
//
//    private void initUi()
//    {
//        tvStoreName = (TextView) findViewById(R.id.text_dashboard);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initUi();
        binding = ActivityStoreManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_statistic,R.id.navigation_setting                         )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_store_detail);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null)
//        {
//            store = (Store) bundle.get("StoreDetail");
//            tvStoreName.setText(store.getStoreName());
//        }
    }
}