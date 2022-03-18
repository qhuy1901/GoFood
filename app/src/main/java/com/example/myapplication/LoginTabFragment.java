package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view. LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {


    private Button btnSwitchToRegisterActivity;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView forgetPass;
    float v=0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String UID = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        btnLogin = root.findViewById(R.id.btnLogin);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPassword = root.findViewById(R.id.edtPassword);
        forgetPass = root.findViewById(R.id.fg_pass);

        edtEmail.setTranslationX(800);
        edtPassword.setTranslationX(800);
        forgetPass.setTranslationX(800);
        btnLogin.setTranslationX(800);
        edtEmail.setAlpha(v);
        edtPassword.setAlpha(v);
        forgetPass.setAlpha(v);
        btnLogin.setAlpha(v);
        edtEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin. animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

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
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UID = mAuth.getCurrentUser().getUid();
                                    Intent switchActivityIntent = new Intent(getActivity(), WelcomeActivity.class);
                                    startActivity(switchActivityIntent);
                                } else {
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return root;
    }
}