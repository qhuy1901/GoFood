package com.example.myapplication.customer.home.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.customer.cart.CartActivity;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;

public class CustomerMeFragment extends Fragment {
    private Button btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_customer_me, container, false);
        btnBack = (Button) root.findViewById(R.id.fragment_cus_me_logout_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        return root;
    }
}