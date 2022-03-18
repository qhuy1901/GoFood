package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.myapplication.WelcomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        if(WelcomeActivity.type_usr == 1) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
            myRef.child(LoginTabFragment.UID).child("cur_location").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String cur_location = snapshot.getValue(String.class);
                    tvLocation.setText(cur_location);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            tvLocation.setText("MERCHANT LOGIN");
        }
    }
}