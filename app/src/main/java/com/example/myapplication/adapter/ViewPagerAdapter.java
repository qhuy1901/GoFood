package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.ui_store_detail.MenuManagement.MenuManagementFragment;
import com.example.myapplication.ui_store_detail.MenuManagement.ProductFragment;
import com.example.myapplication.ui_store_detail.MenuManagement.ToppingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position){
//            case 1:
//                title = "Topping";
//                break;
//            default:
//                title = "Món ăn";
//                break;
//        }
//        return title;
//    }

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
