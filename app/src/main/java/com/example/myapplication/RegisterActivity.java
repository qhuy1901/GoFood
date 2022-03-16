package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

    private void initUI()
    {
        // Khai báo các thành phần trong View
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();

        ProgressDialog progressDialog = new ProgressDialog(this);

        // Xử lý sự kiện click button Đăng kí
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String fullName = edtFullName.getText().toString();

                if(confirmPassword.equals(password))
                {
                    progressDialog.show();
                    myRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(RegisterActivity.this, "Email already exists",Toast.LENGTH_SHORT).show();
                            }else{
                                String newUserID = myRef.push().getKey();
                                myRef.child(newUserID).child("fullname").setValue(fullName);
                                myRef.child(newUserID).child("email").setValue(email);
                                myRef.child(newUserID).child("password").setValue(password);
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent switchActivityIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(switchActivityIntent);
                                finishAffinity();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không đúng! Vui lòng nhập lại.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}