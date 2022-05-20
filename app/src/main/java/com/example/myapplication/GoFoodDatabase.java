package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.models.Order;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.example.myapplication.models.Topping;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GoFoodDatabase {
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public void insertProduct(Product product, ImageView ivProductImage) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").child(product.getStoreId()).child("menu").push().getKey();
        product.setProductId(key);

        /* Xử lý thêm ảnh vô firebase */
        StorageReference storageRef = storage.getReference( "product_image/product_avatar_" + key + ".png");
        // Get the data from an ImageView as bytes
        ivProductImage.setDrawingCacheEnabled(true);
        ivProductImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivProductImage.getDrawable()).getBitmap();
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
                /* Thêm dữ liệu vô Realtime database */
                Map<String, Object> productValues = product.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/stores/"+ product.getStoreId() + "/menu/products/" + key, productValues);
                mDatabase.updateChildren(childUpdates);

                /* Lấy url sau khi upload ảnh thành công */
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        product.setProductImage(uri.toString());
                        /* Thêm dữ liệu vô Realtime database */
                        mDatabase.child("stores").child(product.getStoreId()).child("menu").child("products").child(key).child("productImage").setValue(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });


    }

    public void deleteProduct(Product product, Context context)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("stores/"+product.getStoreId()+"/menu/products/"+product.getProductId());
        mDatabase.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                deleteFileInStorageFirebase( product.getProductImage());
            }
        });
    }

    public void updateProduct(Product product) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Lưu thông tin product vào realtime database
//        String avatarFileName = "avatar" + product.getProductId() + ".png";
        Map<String, Object> productValues = product.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/"+ product.getStoreId() + "/menu/products/" + product.getProductId(), productValues);
        mDatabase.updateChildren(childUpdates);
//        addProductImageToFirebase(avatarFileName, );
    }

    private void addImageToStorageFirebase(String fileName, ImageView ivProductImage)
    {
        StorageReference storageRef = storage.getReference(fileName);

        // Get the data from an ImageView as bytes
        ivProductImage.setDrawingCacheEnabled(true);
        ivProductImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivProductImage.getDrawable()).getBitmap();
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

    private void deleteFileInStorageFirebase(String fileName)
    {
        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child(fileName);
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
    public void loadImageToImageView(@NonNull ImageView iv, String fileName)
    {
        StorageReference firebaseStorage = storage.getReference(fileName);
        try{
            final File localFile = File.createTempFile(fileName.replace(".png",""),"png");
            firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    iv.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadImageToImageView(@NonNull ImageView iv, String folderName, String image)
    {
        String fileName = image.replace(folderName + "/", "");
        StorageReference firebaseStorage = storage.getReference().child(folderName).child(fileName);
        try{
            final File localFile = File.createTempFile(fileName.replace(".png",""),"png");
            firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    iv.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertStore(Store store, ImageView imageView) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").push().getKey();
        store.setStoreId(key);

        /* Xử lý thêm ảnh vô firebase */
        StorageReference storageRef = storage.getReference( "store_image/store-avatar" + key + ".png");
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
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
                /* Thêm dữ liệu vô Realtime database */
                Map<String, Object> storeValues = store.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/stores/" + key, storeValues);
                mDatabase.updateChildren(childUpdates);

                /* Lấy url sau khi upload ảnh thành công */
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        store.setAvatar(uri.toString());
                        /* Thêm dữ liệu vô Realtime database */
                        mDatabase.child("stores").child(key).child("avatar").setValue(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });
    }

    public void insertOrder(Order order) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("orders").push().getKey();
        order.setOrderId(key);

        Map<String, Object> orderValues = order.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/orders/" + key, orderValues);
        mDatabase.updateChildren(childUpdates);
    }

    public void insertTopping(Topping topping, String storeId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("stores").child(storeId).child("menu").child("topping").push().getKey();
        topping.setToppingId(key);

        Map<String, Object> orderValues = topping.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/stores/" + storeId + "/menu/topping/"+ key, orderValues);
        mDatabase.updateChildren(childUpdates);
    }

    public void loadUserFullnameToTextView(String userId, TextView tv)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    User user = task.getResult().getValue(User.class);
                    tv.setText(user.getFullName());
                }
            }
        });
    }

    public void loadStoreNameAndAddressToTextView(String storeId, TextView tvName, TextView tvAddress)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("stores").child(storeId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Store store = task.getResult().getValue(Store.class);
                    tvName.setText(store.getStoreName());
                    tvAddress.setText(store.getStoreAddress());
                }
            }
        });
    }

    public void loadCustomerShippingAddressToTextView(String userId, TextView tvName, TextView tvPhone ,TextView tvAddress)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    User user = task.getResult().getValue(User.class);
                    tvName.setText(user.getFullName());
                    tvAddress.setText(user.getCur_location().replace(", Vietnam", "").replace(", Việt Nam", ""));
                    tvPhone.setText(user.getPhoneNumber());
                }
            }
        });
    }

    public void loadShippingAddressToTextViewByOrderId(String orderId, TextView tvName, TextView tvAddress)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("orders").child(orderId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Order order = task.getResult().getValue(Order.class);
                    tvName.setText(order.getShippingAddress().getReceiver());
                    tvAddress.setText(order.getShippingAddress().getAddress().replace(", Vietnam", "").replace(", Việt Nam", ""));
                }
            }
        });
    }

//    public void loadShippingFeeToTextView(String storeId, TextView tvShippingFee, int lenOfCustomerAddress)
//    {
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("stores").child(storeId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Store store = task.getResult().getValue(Store.class);
//                    float distance = (float) ((store.getStoreAddress().length() + lenOfCustomerAddress) / 100 + 0.3);
//                    tvShippingFee.setText("(" + distance + " km)");
//                }
//            }
//        });
//    }

    public void updateOrder(Order order) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> values = order.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/orders/"+ order.getOrderId(),values);
        mDatabase.updateChildren(childUpdates);
    }
}
