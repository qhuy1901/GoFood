package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.models.User;
import com.example.myapplication.models.UserSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginTabFragment extends Fragment {
    private UserSession userSession;
    private Button btnSwitchToRegisterActivity;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView forgetPass, logIn;
    float v=0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static String UID = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        userSession = new UserSession(getActivity());

        btnLogin = root.findViewById(R.id.btnLogin);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPassword = root.findViewById(R.id.edtPassword);
        forgetPass = root.findViewById(R.id.fg_pass);
        logIn = root.findViewById(R.id.login);
        logIn.setTranslationX(500);
        logIn.setAlpha(v);
        logIn. animate().translationX(0).alpha(1).setDuration(500).setStartDelay(700).start();

        edtEmail.setTranslationX(500);
        edtPassword.setTranslationX(500);
        forgetPass.setTranslationX(500);
        btnLogin.setTranslationX(500);
        edtEmail.setAlpha(v);
        edtPassword.setAlpha(v);
        forgetPass.setAlpha(v);
        btnLogin.setAlpha(v);
        edtEmail.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(300).start();
        edtPassword.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(500).start();
        btnLogin. animate().translationX(0).alpha(1).setDuration(500).setStartDelay(700).start();

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

                                    /* Lưu thông tin user đăng nhập vào Session */
                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User userInfo = snapshot.getValue(User.class);
                                            Log.e("Hihi", userInfo.getUserId() + userInfo.getFullName());
                                            userSession.saveUser(userInfo);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    /*  */

                                    Intent switchActivityIntent = new Intent(getActivity(), WelcomeActivity.class);
                                    startActivity(switchActivityIntent);
                                } else {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Vui lòng thử lại")
                                            .setContentText("Tên đăng nhập hoặc mật khẩu không đúng.")
                                            .show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });

        return root;
    }
}