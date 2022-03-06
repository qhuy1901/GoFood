package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        // Khai báo các thành phần trong View
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        ProgressDialog progressDialog = new ProgressDialog(this);

        // Xử lý sự kiện
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent switchActivityIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(switchActivityIntent);
                                    finishAffinity();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}