package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Button btnSwitchToRegisterActivity;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String UID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khai báo các thành phần trong View
        btnSwitchToRegisterActivity = (Button) findViewById(R.id.btnSwitchToRegisterActivity);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        ProgressDialog progressDialog = new ProgressDialog(this);

        // Xử lý sự kiện click button Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                progressDialog.setMessage("Đang đăng nhập...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UID = mAuth.getCurrentUser().getUid();
                                    Intent switchActivityIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
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