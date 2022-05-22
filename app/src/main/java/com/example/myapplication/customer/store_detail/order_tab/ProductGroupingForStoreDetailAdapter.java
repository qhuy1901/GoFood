package com.example.myapplication.customer.store_detail.order_tab;

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

public class ProductGroupingForStoreDetailAdapter extends RecyclerView.Adapter<ProductGroupingForStoreDetailAdapter.ProductGroupingForStoreDetailViewHolder>
{
    private final List<ProductWithProductGrouping> list;
    private Context context;

    public ProductGroupingForStoreDetailAdapter(List<ProductWithProductGrouping> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductGroupingForStoreDetailAdapter.ProductGroupingForStoreDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grouping_for_product_fragment, parent,false);
        return new ProductGroupingForStoreDetailAdapter.ProductGroupingForStoreDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupingForStoreDetailAdapter.ProductGroupingForStoreDetailViewHolder holder, int position) {
        ProductWithProductGrouping productWithProductGrouping = list.get(position);
        if(productWithProductGrouping == null)
            return;
        if(productWithProductGrouping.getProductGrouping().equals("Không xác định"))
        {
            holder.tvProductGroupingForStoreDetailName.setText("Danh sách món");
            holder.tvQuantity.setText("");
        }
        else
        {
            holder.tvProductGroupingForStoreDetailName.setText(productWithProductGrouping.getProductGrouping());
            holder.tvQuantity.setText("(" + productWithProductGrouping.getProductList().size() + ")");
        }
        loadProductList(holder, productWithProductGrouping);
    }

    private void loadProductList(@NonNull ProductGroupingForStoreDetailAdapter.ProductGroupingForStoreDetailViewHolder holder, ProductWithProductGrouping productWithProductGrouping)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rcvProducts.setLayoutManager(linearLayoutManager);
        ProductForStoreDetailAdapter adapter = new ProductForStoreDetailAdapter(productWithProductGrouping.getProductList(), context);
        holder.rcvProducts.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public class ProductGroupingForStoreDetailViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQuantity, tvProductGroupingForStoreDetailName;
        private RecyclerView rcvProducts;

        public ProductGroupingForStoreDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.item_product_grouping_for_menu_management_tv_quantity);
            tvProductGroupingForStoreDetailName = itemView.findViewById(R.id.item_product_grouping_for_menu_management_tv_product_grouping_name);
            rcvProducts = itemView.findViewById(R.id.item_product_grouping_for_menu_management_rcv_product_list);

        }
    }
}
