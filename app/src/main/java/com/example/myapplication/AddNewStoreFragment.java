package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAddNewStoreBinding;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddNewStoreFragment extends Fragment {

    private FragmentAddNewStoreBinding binding;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddNewStoreBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddNewStoreFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        binding.btnAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = binding.etStoreName.getText().toString();
                String storeCategory = binding.etStoreCategory.getText().toString();
                String description = binding.etDescription.getText().toString();

                writeNewStore(storeName, storeCategory, description);

                Toast.makeText(getActivity(), "Thêm cửa hàng mới thành công",Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(AddNewStoreFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }


    private void writeNewStore(String storeName, String storeCategory, String description) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").push().getKey();

        // Lấy mã user trong Session
        SharedPreferences preferences = this.getActivity().getSharedPreferences("Session", this.getActivity().MODE_PRIVATE);
        String owner = preferences.getString("UserId", "default value");

        Store post = new Store(key, storeName, storeCategory, description, owner);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}