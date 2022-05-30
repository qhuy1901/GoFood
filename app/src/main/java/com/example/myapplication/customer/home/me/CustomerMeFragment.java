package com.example.myapplication.customer.home.me;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.customer.address.CustomerAddressActivity;
import com.example.myapplication.merchant.choose_store.ChooseStoreActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomerMeFragment extends Fragment {
    private Button btnBack;
    private TextView txtFullName;
    private ConstraintLayout clCustomerAddress,clMerchant;
    private GoFoodDatabase goFoodDatabase;
    private String userId;

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    private void loadInfoToForm()
    {
        goFoodDatabase = new GoFoodDatabase();

        goFoodDatabase.loadCustomerNameToTextView(userId, txtFullName);
    }
    private void getUserInfo()
    {
        SharedPreferences preferences = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
        userId = preferences.getString("userId", "default value");
    }
    @RequiresApi(api = Build.VERSION_CODES.N)*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_customer_me, container, false);
        btnBack = (Button) root.findViewById(R.id.fragment_cus_me_logout_btn);
        clCustomerAddress = (ConstraintLayout)  root.findViewById(R.id.fragment_customer_me_cl_customer_address);
        clMerchant = (ConstraintLayout)  root.findViewById(R.id.fragment_customer_me_cl_merchant);
        txtFullName = (TextView) root.findViewById(R.id.txt_username);
        /*getUserInfo();
        loadInfoToForm();*/
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        clCustomerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), CustomerAddressActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        clMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), ChooseStoreActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        return root;
    }

}