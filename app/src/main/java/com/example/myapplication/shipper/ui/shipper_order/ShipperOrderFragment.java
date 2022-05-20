package com.example.myapplication.shipper.ui.shipper_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.databinding.FragmentShipperOrderBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShipperOrderFragment extends Fragment {

    private FragmentShipperOrderBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ShipperOrderViewPagerAdapter viewPagerAdapter;
    private ImageView ivBtnBack;

    private void initUi()
    {
        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        ivBtnBack = binding.fragementShipperOrderIbBack;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentShipperOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPagerAdapter = new ShipperOrderViewPagerAdapter(this.getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position)
            {
                case 1:
                    tab.setText("Đang làm");
                    break;
                case 2:
                    tab.setText("Đã xong");
                    break;
                default:
                    tab.setText("Nhận đơn");
                    break;
            }
        }).attach();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}