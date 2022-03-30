package com.example.myapplication;

import android.content.SharedPreferences;

import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoFoodDatabase {
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public void writeNewProduct(String productName, int price, String productDescription, String storeId, boolean isAvailable) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").child(storeId).child("Menu").push().getKey();

        // Lưu thông tin product vào realtime database
//        String avatarFileName = "avatar" + key + ".png";
        Product product= new Product(productName, price, productDescription, storeId, isAvailable);
        Map<String, Object> productValues = product.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/"+ storeId + "/Menu/" + key, productValues);
        mDatabase.updateChildren(childUpdates);

        // Lưu ảnh avatar vào firebase storage
//        addAvatarImageToFirebase(avatarFileName);
    }
}
