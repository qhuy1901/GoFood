package com.example.myapplication;

import android.os.Bundle;
import android.view. LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    private Button btnRegister;
    private EditText edtFullName, edtNM;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private TextView register;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        register= root.findViewById(R.id.register);
        edtFullName = root.findViewById(R.id.edtFullName);
        edtNM = root.findViewById(R.id.edtNM);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPassword = root.findViewById(R.id.edtPassword);
        edtConfirmPassword = root.findViewById(R.id.edtConfirmPassword);
        btnRegister = root.findViewById(R.id.btnRegister);
        register.setTranslationX(300);
        register.setAlpha(v);
        register. animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        edtFullName.setTranslationX(300);
        edtNM.setTranslationX(300);
        edtEmail.setTranslationX(300);
        edtPassword.setTranslationX(300);
        edtConfirmPassword.setTranslationX(300);
        btnRegister.setTranslationX(300);

        edtFullName.setAlpha(v);
        edtNM.setAlpha(v);
        edtPassword.setAlpha(v);
        edtConfirmPassword.setAlpha(v);
        btnRegister.setAlpha(v);
        edtEmail.setAlpha(v);


        edtFullName.animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        edtNM.animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        edtEmail.animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        edtPassword.animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        edtConfirmPassword.animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();
        btnRegister. animate().translationX(0).alpha(1).setDuration(300).setStartDelay(300).start();


        return root;


    }
}
