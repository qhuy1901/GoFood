package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.store_detail.StoreDetailActivity;
import com.example.myapplication.models.Store;

import java.util.List;

public class StoreForHomeAdapter extends RecyclerView.Adapter<StoreForHomeAdapter.StoreForHomeViewHolder>{
    private final List<Store> storeList;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public StoreForHomeAdapter(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoreForHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_for_home, parent,false);
        return new StoreForHomeAdapter.StoreForHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreForHomeViewHolder holder, int position) {
        Store store = storeList.get(position);
        if(store == null)
            return ;
        holder.tvStoreName.setText(store.getStoreName());
        if(!store.getAvatar().isEmpty())
        {
            goFoodDatabase.loadImageToImageView(holder.ivStoreAvatar, store.getAvatar());
        }
        holder.clStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(context, StoreDetailActivity.class);
                switchActivityIntent.putExtra("store", store);
                context.startActivity(switchActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(storeList != null)
            return storeList.size();
        return 0;
    }

    public class StoreForHomeViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvStoreName;
        private ImageView ivStoreAvatar;
        private ConstraintLayout clStore;

        public StoreForHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = (TextView) itemView.findViewById(R.id.item_store_for_home_tv_store_name);
            ivStoreAvatar = (ImageView) itemView.findViewById(R.id.item_store_for_home_iv_store_avatar);
            clStore = (ConstraintLayout) itemView.findViewById(R.id.item_store_for_home_cl_store_parent);
        }
    }
}
