package com.example.myapplication.customer.like;

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

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.models.Store;

import java.util.List;

public class CustomerLikeAdapter extends RecyclerView.Adapter<CustomerLikeAdapter.CustomerLikeViewHolder>{
    private final List<Store> storeList;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public CustomerLikeAdapter(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerLikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_for_customer_like, parent,false);
        return new CustomerLikeAdapter.CustomerLikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerLikeViewHolder holder, int position) {
        Store store = storeList.get(position);
        if(store == null)
            return ;
        holder.tvStoreName.setText(store.getStoreName());
        if(!store.getAvatar().isEmpty())
        {
            Glide.with(context).load(store.getAvatar()).into(holder.ivStoreAvatar);
        }
        holder.clStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(context, StorePageDetailActivity.class);
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


    public class CustomerLikeViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvStoreName;
        private ImageView ivStoreAvatar;
        private ConstraintLayout clStore;

        public CustomerLikeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = (TextView) itemView.findViewById(R.id.item_store_for_customer_like_tv_store_name);
            ivStoreAvatar = (ImageView) itemView.findViewById(R.id.item_store_for_customer_like_iv_store_avatar);
            clStore = (ConstraintLayout) itemView.findViewById(R.id.item_store_for_customer_like_cl_store_parent);
        }
    }
}
