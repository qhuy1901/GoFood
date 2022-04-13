package com.example.myapplication.merchant.store_management.MenuManagement;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.merchant.store_management.MenuManagement.product.ProductFragment;
import com.example.myapplication.merchant.store_management.MenuManagement.topping.ToppingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ToppingFragment();
            case 0:
                return new ProductFragment();
        }
        return new ToppingFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
