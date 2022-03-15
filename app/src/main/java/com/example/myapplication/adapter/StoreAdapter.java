package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Store;

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
        holder.tvStoreName.setText("Tên cửa hàng: " + store.storeName);
        holder.tvStoreCategory.setText("Loại: " + store.storeCategory);
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

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreCategory = itemView.findViewById(R.id.tvStoreCategory);
        }
    }
}
