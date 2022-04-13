package com.example.myapplication.customer.store_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.models.Store;

public class StorePageInformationTabFragment extends Fragment {
    private Store storeInfo;
    public StorePageInformationTabFragment(Store storeinfo) {
        this.storeInfo = storeInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_page_information_tab, container, false);
    }
}