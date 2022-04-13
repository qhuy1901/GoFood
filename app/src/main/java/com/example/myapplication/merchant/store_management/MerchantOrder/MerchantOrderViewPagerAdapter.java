package com.example.myapplication.merchant.store_management.MerchantOrder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.merchant.store_management.MerchantOrder.confirm_order.ConfirmOrderFragment;
import com.example.myapplication.merchant.store_management.MerchantOrder.history_order.HistoryOrderFragment;
import com.example.myapplication.merchant.store_management.MerchantOrder.new_order.NewOrderFragment;

public class MerchantOrderViewPagerAdapter extends FragmentStateAdapter
{
    public  MerchantOrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ConfirmOrderFragment();
            case 2:
                return new HistoryOrderFragment();
            case 0:
                return new NewOrderFragment();
        }
        return new NewOrderFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
