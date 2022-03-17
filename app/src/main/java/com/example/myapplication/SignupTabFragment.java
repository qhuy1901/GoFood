package com.example.myapplication;

import android.os.Bundle;
import android.view. LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
public class SignupTabFragment extends Fragment {
    TextView un, nm, pass,cfPass;
    Button signup;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        un = root.findViewById(R.id.un);
        nm = root.findViewById(R.id.nm);
        pass = root.findViewById(R.id.pass);
        cfPass = root.findViewById(R.id.cfpass);
        signup = root.findViewById(R.id.btnSignup);
        un.setTranslationX(800);
        nm.setTranslationX(800);
        pass.setTranslationX(800);
        cfPass.setTranslationX(800);
        signup.setTranslationX(800);
        un.setAlpha(v);
        nm.setAlpha(v);
        pass.setAlpha(v);
        cfPass.setAlpha(v);
        signup.setAlpha(v);
        un.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        nm.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        cfPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup. animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }
}
