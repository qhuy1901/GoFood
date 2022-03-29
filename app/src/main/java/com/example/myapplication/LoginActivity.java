package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btnSwitchToRegisterActivity;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;
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