package com.example.myapplication.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Home_RecommendedOrderModel;

import java.util.List;

public class Home_RecommendedAdapter extends RecyclerView.Adapter<Home_RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Home_RecommendedOrderModel> recommendedList;

    public Home_RecommendedAdapter(Context context, List<Home_RecommendedOrderModel> recommendedList){
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        Glide.with(context).load(recommendedList.get(position).getImg_url()).into(holder.recommendedImage);
        holder.recommendedName.setText(recommendedList.get(position).getName());
        holder.recommendedDeliveryTime.setText(recommendedList.get(position).getDeliveryTime());
        holder.recommendedDeliveryType.setText(recommendedList.get(position).getDeliveryType());
        holder.recommendedPrice.setText(recommendedList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendedImage;
        TextView recommendedName, recommendedDeliveryTime, recommendedDeliveryType, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedDeliveryType = itemView.findViewById(R.id.recommended_delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);
        }
    }
}
