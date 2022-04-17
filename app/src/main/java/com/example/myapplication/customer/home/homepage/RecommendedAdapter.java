package com.example.myapplication.customer.home.homepage;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Home_RecommendedModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Home_RecommendedModel> recommendedList;

    public RecommendedAdapter(Context context, List<Home_RecommendedModel> recommendedList){
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
        int newPrice = Integer.valueOf(recommendedList.get(position).getPrice().replace(".","").replace(" ₫",""));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(newPrice * 1.5).replace("₫", "")+ " ₫";
        holder.recommendedPrice2.setText(priceAfterFormat);
        holder.recommendedPrice2.setPaintFlags(holder.recommendedPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendedImage;
        TextView recommendedName, recommendedDeliveryTime, recommendedDeliveryType, recommendedPrice, recommendedPrice2;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedDeliveryType = itemView.findViewById(R.id.recommended_delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);
            recommendedPrice2 = itemView.findViewById(R.id.recommended_price2);
        }
    }
}
