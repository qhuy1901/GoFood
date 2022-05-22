package com.example.myapplication.merchant.store_management.MenuManagement.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.ProductWithProductGrouping;

import java.util.List;

public class ProductGroupingForProductFragmentAdapter extends RecyclerView.Adapter<ProductGroupingForProductFragmentAdapter.ProductGroupingForProductFragmentViewHolder>
{
    private final List<ProductWithProductGrouping> list;
    private Context context;

    public ProductGroupingForProductFragmentAdapter(List<ProductWithProductGrouping> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductGroupingForProductFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grouping_for_product_fragment, parent,false);
        return new ProductGroupingForProductFragmentAdapter.ProductGroupingForProductFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupingForProductFragmentViewHolder holder, int position) {
        ProductWithProductGrouping productWithProductGrouping = list.get(position);
        if(productWithProductGrouping == null)
            return;
        holder.tvProductGroupingForProductFragmentName.setText(productWithProductGrouping.getProductGrouping());
        holder.tvQuantity.setText("(" + productWithProductGrouping.getProductList().size() + ")");
        loadProductList(holder, productWithProductGrouping);
    }

    private void loadProductList(@NonNull ProductGroupingForProductFragmentViewHolder holder, ProductWithProductGrouping productWithProductGrouping)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rcvProducts.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(productWithProductGrouping.getProductList(), context);
        holder.rcvProducts.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public class ProductGroupingForProductFragmentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQuantity, tvProductGroupingForProductFragmentName;
        private RecyclerView rcvProducts;

        public ProductGroupingForProductFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.item_product_grouping_for_menu_management_tv_quantity);
            tvProductGroupingForProductFragmentName = itemView.findViewById(R.id.item_product_grouping_for_menu_management_tv_product_grouping_name);
            rcvProducts = itemView.findViewById(R.id.item_product_grouping_for_menu_management_rcv_product_list);

        }
    }
}
