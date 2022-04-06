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
import com.example.myapplication.models.Home_MenuCategoriesModel;

import java.util.List;

public class Home_AllCateAdapter extends RecyclerView.Adapter<Home_AllCateAdapter.AllCateViewHolder>{

    Context context;
    List<Home_MenuCategoriesModel> allcateList;

    public Home_AllCateAdapter(Context context, List<Home_MenuCategoriesModel> allcateList) {
        this.context = context;
        this.allcateList = allcateList;
    }

    @NonNull
    @Override
    public AllCateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllCateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllCateViewHolder holder, int position) {
        Glide.with(context).load(allcateList.get(position).getImg_url()).into(holder.allcateImage);
        holder.allcateName.setText(allcateList.get(position).getName());
        holder.allcateDeliveryTime.setText(allcateList.get(position).getDeliveryTime());
        holder.allcateDeliveryType.setText(allcateList.get(position).getDeliveryType());
        holder.allcatePrice.setText(allcateList.get(position).getPrice());
        holder.allcateNote.setText(allcateList.get(position).getNote());
        holder.allcateRating.setText(allcateList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return allcateList.size();
    }

    public class AllCateViewHolder extends RecyclerView.ViewHolder {
        ImageView allcateImage;
        TextView allcateName, allcateDeliveryTime, allcateDeliveryType, allcatePrice, allcateNote, allcateRating;

        public AllCateViewHolder(@NonNull View itemView) {
            super(itemView);
            allcateImage = itemView.findViewById(R.id.catprod_image);
            allcateName = itemView.findViewById(R.id.catprod_name);
            allcateDeliveryTime = itemView.findViewById(R.id.catprod_delivery_time);
            allcateDeliveryType = itemView.findViewById(R.id.catprod_delivery_type);
            allcatePrice = itemView.findViewById(R.id.catprod_price);
            allcateNote = itemView.findViewById(R.id.catprod_note);
            allcateRating = itemView.findViewById(R.id.catprod_rating);
        }
    }
}
