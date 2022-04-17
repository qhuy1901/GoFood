package com.example.myapplication.customer.home.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.customer.address.CustomerAddressActivity;
import com.example.myapplication.merchant.choose_store.ChooseStoreActivity;

public class CustomerMeFragment extends Fragment {
    private Button btnBack;
    private ConstraintLayout clCustomerAddress,clMerchant;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_customer_me, container, false);
        btnBack = (Button) root.findViewById(R.id.fragment_cus_me_logout_btn);
        clCustomerAddress = (ConstraintLayout)  root.findViewById(R.id.fragment_customer_me_cl_customer_address);
        clMerchant = (ConstraintLayout)  root.findViewById(R.id.fragment_customer_me_cl_merchant);
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