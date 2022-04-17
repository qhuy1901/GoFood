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
    private ImageView storeImg;
    public StorePageInformationTabFragment(Store storeInfo) {
        this.storeInfo = storeInfo;
    }

    private void initUI(ViewGroup root){
        storeImg = (ImageView) root.findViewById(R.id.fragment_cus_shoppage_information_shopimg);

        Glide.with(getActivity()).load(storeInfo.getAvatar()).into(storeImg);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cus_shoppage_information, container, false);
        initUI(root);
        return root;
    }
}