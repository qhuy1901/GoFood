package com.example.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.UserViewHolder>{
    private List<Store> storeList;

    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Store store = storeList.get(position);
        if(store == null)
            return ;
        holder.tvStoreName.setText("Tên cửa hàng: " + store.getStoreName());
        holder.tvStoreCategory.setText("Loại: " + store.getStoreCategory());
        loadAvatarFromFirebaseStorage(holder, store.getAvatar());
    }

    public void loadAvatarFromFirebaseStorage(@NonNull UserViewHolder holder, String avatarFileName)
    {
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference(avatarFileName);
        try{
            final File localFile = File.createTempFile(avatarFileName.replace(".png",""),"png");
            firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.ivAvatar.setImageBitmap(bitmap);
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

    @Override
    public int getItemCount() {
        if(storeList != null)
            return storeList.size();
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvStoreName;
        private TextView tvStoreCategory;
        private ImageView ivAvatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreCategory = itemView.findViewById(R.id.tvStoreCategory);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);

        }
    }
}
