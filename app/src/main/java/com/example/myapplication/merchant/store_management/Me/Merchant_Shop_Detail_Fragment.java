package com.example.myapplication.merchant.store_management.Me;

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
import com.example.myapplication.customer.home.HomeActivity;

public class Merchant_Shop_Detail_Fragment extends Fragment {
    private Button btnLogout;
    private ConstraintLayout clCustomer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant__shop__detail_, container, false);
        btnLogout = (Button) view.findViewById(R.id.fragment_mertchant_me_btn_logout) ;
        clCustomer = (ConstraintLayout) view.findViewById(R.id.fragment_mertchant_me_cl_customer) ;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        clCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), HomeActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        return view;
    }
}