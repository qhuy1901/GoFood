package com.example.myapplication.shipper.ui.shipper_order;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.shipper.ui.shipper_order.doing.ShipperDoingFragment;
import com.example.myapplication.shipper.ui.shipper_order.done.ShipperDoneFragment;
import com.example.myapplication.shipper.ui.shipper_order.receive_order.ShipperReceiveOrderFragment;

public class  ShipperOrderViewPagerAdapter extends FragmentStateAdapter {
    public ShipperOrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ShipperDoingFragment();
            case 2:
                return new ShipperDoneFragment();
            case 0:
                return new ShipperReceiveOrderFragment();
        }
        return new ShipperReceiveOrderFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
