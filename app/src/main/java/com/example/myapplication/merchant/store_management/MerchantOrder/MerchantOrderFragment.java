package com.example.myapplication.merchant.store_management.MerchantOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.databinding.FragmentMerchantOrderBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MerchantOrderFragment extends Fragment {
    private FragmentMerchantOrderBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MerchantOrderViewPagerAdapter viewPagerAdapter;

    private void initUi()
    {
        tabLayout = binding.fragmentMerchantOrderTabLayout;
        viewPager = binding.fragmentMerchantOrderViewPager;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMerchantOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerAdapter = new MerchantOrderViewPagerAdapter(this.getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position)
            {
                case 1:
                    tab.setText("Đã nhận");
                    break;
                case 2:
                    tab.setText("Lịch sử");
                    break;
                default:
                    tab.setText("Mới");
                    break;
            }
        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}