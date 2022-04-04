package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Home_CategoriesOrderModel;

import java.util.List;

public class Home_CategoriesOrderAdapter extends RecyclerView.Adapter<Home_CategoriesOrderAdapter.ViewHolder>{

    private Context context;
    private List<Home_CategoriesOrderModel> li_popular_prods;

    public Home_CategoriesOrderAdapter(Context context, List<Home_CategoriesOrderModel> li_popular_prods){
        this.context = context;
        this.li_popular_prods = li_popular_prods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(li_popular_prods.get(position).getImg_url()).into(holder.catImg);
        holder.catName.setText(li_popular_prods.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return li_popular_prods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);
        }
    }
}
