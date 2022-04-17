package com.example.myapplication.customer.store_detail.info_tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Store;

public class StorePageInformationTabFragment extends Fragment {
    private Store storeInfo;
    public StorePageInformationTabFragment(Store storeInfo) {
        this.storeInfo = storeInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cus_shoppage_information, container, false);
        return root;
    }
}