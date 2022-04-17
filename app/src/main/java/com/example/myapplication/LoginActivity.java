package com.example.myapplication;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {


    private TabLayout tablayout;
    private ViewPager2 viewPager;

    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_paper);

        final LoginAdapter adapter = new LoginAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tablayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Đăng nhập");

                    break;
                case 1:
                    tab.setText("Đăng ký");
                    break;
            }
        }).attach();

        tablayout.setTranslationY(300);
        tablayout.setAlpha(v);
        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

}