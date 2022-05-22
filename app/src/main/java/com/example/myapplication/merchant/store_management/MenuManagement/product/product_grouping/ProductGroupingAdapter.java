package com.example.myapplication.merchant.store_management.MenuManagement.product.product_grouping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ProductGroupingAdapter extends RecyclerView.Adapter<ProductGroupingAdapter.ProductGroupingViewHolder>
{
    private final List<String> productGroupingList;
    private Context context;

    public ProductGroupingAdapter(List<String> productGroupingList, Context context) {
        this.productGroupingList = productGroupingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductGroupingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grouping,parent,false);
        return new ProductGroupingAdapter.ProductGroupingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupingViewHolder holder, int position) {
        String productGrouping = productGroupingList.get(position);
        if(productGrouping.isEmpty())
            return;
        holder.tvProductGroupingName.setText(productGrouping);
    }

    @Override
    public int getItemCount() {
        if(productGroupingList != null)
            return productGroupingList.size();
        return 0;
    }

    public class ProductGroupingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQuantity, tvProductGroupingName;

        public ProductGroupingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.item_product_grouping_tv_quantity);
            tvProductGroupingName = itemView.findViewById(R.id.item_product_grouping_tv_product_grouping_name);
        }
    }
}
