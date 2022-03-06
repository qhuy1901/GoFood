package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        // Khai báo các thành phần trong View
        Button btnSwitchToRegisterActivity = (Button) findViewById(R.id.btnSwitchToRegisterActivity);
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        ProgressDialog progressDialog = new ProgressDialog(this);

        // Xử lý sự kiện click button Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent switchActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(switchActivityIntent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        // Xử lý sự kiện click button đăng kí
        btnSwitchToRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}