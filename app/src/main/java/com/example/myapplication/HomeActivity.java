package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView tvHello;
    private Button btnLogout, btnSwitchToMerchantHome;

    private void initUI()
    {
        // Khai báo các thành phần trong View
        tvHello = (TextView) findViewById(R.id.tvHello);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnSwitchToMerchantHome = (Button) findViewById(R.id.btnSwitchToChooseStore);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        FirebaseDatabase goFoodDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = goFoodDatabase.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();

        // Đây được coi như là Session như bên web
        SharedPreferences.Editor editor = getSharedPreferences("Session", MODE_PRIVATE).edit();
        editor.putString("UserId", userId); // Lưu mã user vào Session
        editor.apply();


        myRef.child(userId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvHello.setText("Xin chào " + snapshot.
                        child("FullName").getValue().toString() + userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        // Xử lý sự kiện btn Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent switchActivityIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        btnSwitchToMerchantHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent switchActivityIntent = new Intent(HomeActivity.this, ChooseStoreActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

}