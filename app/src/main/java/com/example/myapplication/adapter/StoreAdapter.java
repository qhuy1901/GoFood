package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.StoreDetailActivity;
import com.example.myapplication.StoreManagementActivity;
import com.example.myapplication.models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder>{
    private final List<Store> storeList;
    private Context context;

    public StoreAdapter(Context context, List<Store> storeList) {
        this.storeList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        if(store == null)
            return ;
        holder.tvStoreName.setText(store.getStoreName());
        holder.tvStoreCategory.setText(store.getStoreCategory());
        loadAvatarFromFirebaseStorage(holder, store.getAvatar());
        holder.clStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(store);

                // Lưu mã cửa hàng vào Session
                SharedPreferences.Editor editor = context.getSharedPreferences("Session", MODE_PRIVATE).edit();
                editor.putString("storeId", store.getStoreId());
                editor.apply();
            }
        });
    }

    private void onClickGoToDetail(Store store)
    {
//        Intent switchActivityIntent = new Intent(this.context, StoreManagementActivity.class);
//        context.startActivity(switchActivityIntent);
        Intent switchActivityIntent = new Intent(this.context, StoreDetailActivity.class);
        switchActivityIntent.putExtra("store", store);
        context.startActivity(switchActivityIntent);
    }

    public void loadAvatarFromFirebaseStorage(@NonNull StoreViewHolder holder, String avatarFileName)
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

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStoreName;
        private TextView tvStoreCategory;
        private ImageView ivAvatar;
        private ConstraintLayout clStoreItem;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreCategory = itemView.findViewById(R.id.tvStoreCategory);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            clStoreItem = itemView.findViewById(R.id.clStoreItem);
        }
    }
}
