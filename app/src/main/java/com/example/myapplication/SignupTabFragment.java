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
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        edtFullName = root.findViewById(R.id.edtFullName);
        edtNM = root.findViewById(R.id.edtNM);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPassword = root.findViewById(R.id.edtPassword);
        edtConfirmPassword = root.findViewById(R.id.edtConfirmPassword);
        btnRegister = root.findViewById(R.id.btnRegister);

        edtFullName.setTranslationX(800);
        edtNM.setTranslationX(800);
        edtEmail.setTranslationX(800);
        edtPassword.setTranslationX(800);
        edtConfirmPassword.setTranslationX(800);
        btnRegister.setTranslationX(800);

        edtFullName.setAlpha(v);
        edtNM.setAlpha(v);
        edtPassword.setAlpha(v);
        edtConfirmPassword.setAlpha(v);
        btnRegister.setAlpha(v);
        edtEmail.setAlpha(v);


        edtFullName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtNM.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        edtConfirmPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnRegister. animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;


    }
}
