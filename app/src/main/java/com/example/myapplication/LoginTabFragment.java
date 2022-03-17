package com.example.myapplication;

import android.os.Bundle;
import android.view. LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
public class LoginTabFragment extends Fragment {

    TextView un, pass,forgetPass;
    Button login;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        un = root.findViewById(R.id.un);
        pass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.fg_pass);
        login = root.findViewById(R.id.btnLogin);
        un.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);
        un.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);
        un.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login. animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }
}