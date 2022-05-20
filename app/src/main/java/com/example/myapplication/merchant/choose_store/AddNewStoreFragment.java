package com.example.myapplication.merchant.choose_store;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddNewStoreBinding;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewStoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentAddNewStoreBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private String[] storeCategories = {"Đồ ăn", "Đồ uống", "Đồ chay", "Bánh kem"};
    private boolean[] selected;
    private List<Integer> selectedList = new ArrayList<>();
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

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

        selected = new boolean[storeCategories.length];
        binding.tvChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn loại cửa hàng");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(storeCategories, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // Check condition
                        if(b){
                            // When checkbox selected
                            // Add position in day list
                            selectedList.add(i);
                            Collections.sort(selectedList);
                        }
                        else{
                            selectedList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j = 0; j < selectedList.size(); j++)
                        {
                            // Concat array value
                            stringBuilder.append(storeCategories[selectedList.get(j)]);

                            if(j != selectedList.size() -1)
                            {
                                stringBuilder.append(", ");
                            }
                        }
                        // Set text for spinner
                        binding.tvChooseCategory.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < selected.length; j++)
                        {
                            selected[j] = false;
                            selectedList.clear();
                        }
                        binding.tvChooseCategory.setText("");
                    }
                });
                builder.show();
            }
        });


        binding.btnAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = binding.etStoreName.getText().toString();
                String storeCategory = binding.tvChooseCategory.getText().toString();
                String storeAddress = binding.etStoreAddress.getText().toString();

                // Lấy mã user trong Session
                SharedPreferences preferences = getContext().getSharedPreferences("Session", getContext().MODE_PRIVATE);
                String owner = preferences.getString("userId", "default value");

                // Lưu thông tin store vào realtime database
                Store store = new Store();;
                store.setStoreName(storeName);
                store.setStoreCategory(storeCategory);
                store.setStoreAddress(storeAddress);
                store.setOwner(owner);
                goFoodDatabase.insertStore(store, binding.ivStoreAvatar);
                Toast.makeText(getActivity(), "Thêm cửa hàng mới thành công",Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(AddNewStoreFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
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