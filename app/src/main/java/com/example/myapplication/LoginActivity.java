package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private TabLayout tablayout;
    private ViewPager viewPager;

    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_paper);
        tablayout.addTab(tablayout.newTab().setText("Đăng nhập"));
        tablayout.addTab(tablayout.newTab().setText("Đăng ký"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tablayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setTranslationY(300);
        tablayout.setAlpha(v);
        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();


        // Reset Session
//        SharedPreferences settings = this.getSharedPreferences("cart", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.clear();
//        editor.commit();
    }

}