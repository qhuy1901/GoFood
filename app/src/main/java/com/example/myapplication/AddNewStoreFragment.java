package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAddNewStoreBinding;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewStoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentAddNewStoreBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private ActivityResultLauncher<Intent> checkPermission = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        binding.ivStoreAvatar.setImageURI(data.getData());
                    }
                }
            });


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

        binding.ivStoreAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.store_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnStoreCategory.setAdapter(adapter);
        //binding.spnStoreCategory.setOnItemClickListener(this);


        binding.btnAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = binding.etStoreName.getText().toString();
                String storeCategory = binding.spnStoreCategory.getSelectedItem().toString();
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

        // Lưu thông tin store vào realtime database
        String avatarFileName = "avatar" + key + ".png";
        Store post = new Store(key, storeName, storeCategory, description, owner, avatarFileName);
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/" + key, postValues);
        mDatabase.updateChildren(childUpdates);

        // Lưu ảnh avatar vào firebase storage
        addAvatarImageToFirebase(avatarFileName);
    }

    public void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        checkPermission.launch(Intent.createChooser(intent, "Select Avatar"));
    }

    private void addAvatarImageToFirebase(String avatarFileName)
    {
        StorageReference storageRef = storage.getReference(avatarFileName);

        // Get the data from an ImageView as bytes
        binding.ivStoreAvatar.setDrawingCacheEnabled(true);
        binding.ivStoreAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) binding.ivStoreAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}