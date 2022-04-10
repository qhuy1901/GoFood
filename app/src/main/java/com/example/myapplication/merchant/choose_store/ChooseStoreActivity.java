package com.example.myapplication.merchant.choose_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.WelcomeActivity;
import com.example.myapplication.databinding.ActivityChooseStoreBinding;

public class ChooseStoreActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityChooseStoreBinding binding;
    private AppCompatImageButton backButton;
    private TextView tvLogout;

    private void initUi()
    {
        backButton = (AppCompatImageButton) findViewById(R.id.activity_add_product_ib_back);
        tvLogout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityChooseStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_choose_store);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(ChooseStoreActivity.this, WelcomeActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(ChooseStoreActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_choose_store);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}