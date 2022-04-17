package com.example.myapplication.customer.store_detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.customer.store_detail.review_tab.StorePageReviewTabFragment;
import com.example.myapplication.models.Store;

public class StorePageViewPagerAdapter extends FragmentStateAdapter {

    private Store storeinfo;

    public StorePageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Store storeinfo) {
        super(fragmentActivity);
        this.storeinfo = storeinfo;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StorePageOrderTabFragment(storeinfo);
            case 1:
                return new StorePageReviewTabFragment(storeinfo);
            case 2:
                return new StorePageInformationTabFragment(storeinfo);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

