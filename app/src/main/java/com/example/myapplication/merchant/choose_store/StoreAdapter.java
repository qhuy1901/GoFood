package com.example.myapplication.merchant.choose_store;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.merchant.store_management.StoreManagementActivity;
import com.example.myapplication.models.Store;

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
        holder.ivAvatar.setImageResource(R.drawable.default_store);
        if(!store.getAvatar().isEmpty())
            Glide.with(context).load(store.getAvatar()).into(holder.ivAvatar);
        if(store.getStoreStatus() == 1)
        {
            holder.tvStoreStatus.setText("Đang hoạt động");
            holder.tvStoreStatus.setTextColor(Color.parseColor("#00C21D"));
        }
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
        Intent switchActivityIntent = new Intent(this.context, StoreManagementActivity.class);
        context.startActivity(switchActivityIntent);
    }


    @Override
    public int getItemCount() {
        if(storeList != null)
            return storeList.size();
        return 0;
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStoreName;
        private TextView tvStoreCategory, tvStoreStatus;
        private ImageView ivAvatar;
        private ConstraintLayout clStoreItem;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreCategory = itemView.findViewById(R.id.tvStoreCategory);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            clStoreItem = itemView.findViewById(R.id.clStoreItem);
            tvStoreStatus = itemView.findViewById(R.id.tv_store_status);
        }
    }
}
