package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class GoFoodDatabase {
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public void insertProduct(String productName, int price, String productDescription, String storeId, int isAvailable) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").child(storeId).child("Menu").push().getKey();

        // Lưu thông tin product vào realtime database
//        String avatarFileName = "avatar" + key + ".png";
        Product product= new Product(key, productName, price, productDescription, storeId, isAvailable);
        Map<String, Object> productValues = product.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/"+ storeId + "/menu/products/" + key, productValues);
        mDatabase.updateChildren(childUpdates);

        // Lưu ảnh avatar vào firebase storage
//        addAvatarImageToFirebase(avatarFileName);
    }

    public void deleteProduct(Product product, Context context)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("stores/"+product.getStoreId()+"/menu/products/"+product.getProductId());
        mDatabase.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }

    public void updateProduct(Product product) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Lưu thông tin product vào realtime database
//        String avatarFileName = "avatar" + key + ".png";

        Map<String, Object> productValues = product.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/"+ product.getStoreId() + "/menu/products/" + product.getProductId(), productValues);
        mDatabase.updateChildren(childUpdates);

        // Lưu ảnh avatar vào firebase storage
//        addAvatarImageToFirebase(avatarFileName);
    }

}
