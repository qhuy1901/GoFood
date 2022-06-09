package com.example.myapplication.customer.home.homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.customer.cart.CartActivity;
import com.example.myapplication.customer.product_detail.ProductDetailActivity;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.models.Home_RecommendedModel;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        Home_RecommendedModel recommended = recommendedList.get(position);
        Glide.with(context).load(recommended.getImg_url()).into(holder.recommendedImage);
        holder.recommendedName.setText(recommended.getName());
        holder.recommendedDeliveryTime.setText(recommended.getDeliveryTime());
        holder.recommendedDeliveryType.setText(recommended.getDeliveryType());
        holder.recommendedPrice.setText(recommended.getPrice());
        int newPrice = Integer.valueOf(recommended.getPrice().replace(".","").replace(" ₫",""));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(newPrice * 1.5).replace("₫", "")+ " ₫";
        holder.recommendedPrice2.setText(priceAfterFormat);
        holder.recommendedPrice2.setPaintFlags(holder.recommendedPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String store_ID = recommended.getStore_ID();
                String product_ID = recommended.getProduct_ID();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("stores").child(store_ID).child("menu").child("products").child(product_ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Product product_info = dataSnapshot.getValue(Product.class);
                        Intent switchActivityIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                        switchActivityIntent.putExtra("product", product_info);
                        view.getContext().startActivity(switchActivityIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(view.getContext(), "Không lấy được thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
