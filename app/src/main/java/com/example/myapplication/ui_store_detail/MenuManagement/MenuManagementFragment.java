package com.example.myapplication.ui_store_detail.MenuManagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.example.myapplication.databinding.FragmentMenuManagementBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MenuManagementFragment extends Fragment {

//    private MenuManagementViewModel dashboardViewModel;
    private FragmentMenuManagementBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private void initUi()
    {
        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                new ViewModelProvider(this).get(MenuManagementViewModel.class);
        binding = FragmentMenuManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUi();

        // Set Action Bar
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddNewProductFragment nextFrag= new AddNewProductFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.menuManagementLayout, nextFrag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerAdapter = new ViewPagerAdapter(this.getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position)
            {
                case 1:
                    tab.setText("Topping");
                    break;
                default:
                    tab.setText("Món ăn");
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