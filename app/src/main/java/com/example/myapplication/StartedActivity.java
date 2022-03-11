package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);

//        Button btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
//
//        btnGetStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent switchActivityIntent = new Intent(StartedActivity.this, LoginActivity.class);
//                startActivity(switchActivityIntent);
//            }
//        });

    }
}