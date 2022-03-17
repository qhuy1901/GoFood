package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

public class SigninActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewPager;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_paper);
        tablayout.addTab(tablayout.newTab().setText("Đăng nhập"));
        tablayout.addTab(tablayout.newTab().setText("Đăng ký"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tablayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout. TabLayoutOnPageChangeListener(tablayout));

        tablayout.setTranslationY(300);
        tablayout.setAlpha(v);
        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();



    }
}