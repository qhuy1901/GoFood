package com.example.myapplication.shipper.ui.notifications;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {
    private GoFoodDatabase goFoodDatabase;
    private TextView txtShipperName;
    private String userId;
    private FragmentNotificationsBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadInfoToForm()
    {
        goFoodDatabase = new GoFoodDatabase();

        goFoodDatabase.loadCustomerNameToTextView(userId, txtShipperName);
    }
    private void getUserInfo()
    {
        SharedPreferences preferences = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
        userId = preferences.getString("userId", "default value");
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        txtShipperName = (TextView) root.findViewById(R.id.txtShipperName);
        getUserInfo();
        loadInfoToForm();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}