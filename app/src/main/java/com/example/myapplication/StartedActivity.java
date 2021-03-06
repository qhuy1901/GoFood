package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartedActivity extends AppCompatActivity {

    private Button btnGetStarted;

    private void initUi()
    {
        btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences("Session", MODE_PRIVATE);
        String userId = prefs.getString("userId", "No name defined");
        if(userId != "No name defined")
        {
            Intent switchActivityIntent = new Intent(StartedActivity.this, WelcomeActivity.class);
            startActivity(switchActivityIntent);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_started);
        initUi();

        //Animations
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        Animation fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_anim);

        ImageView image = findViewById(R.id.imageView2);
        TextView slogan = findViewById(R.id.textView);
        ImageView img = findViewById(R.id.imageView);

        image.setAnimation(topAnim);
        slogan.setAnimation(topAnim);
        img.setAnimation(fadeAnim);
        btnGetStarted.setAnimation(botAnim);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(StartedActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });

    }
}