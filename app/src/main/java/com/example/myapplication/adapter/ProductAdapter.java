package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{
    private final List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product == null)
            return ;
        holder.tvProductName.setText(product.getProductName());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(product.getPrice());
        holder.tvPrice.setText(priceAfterFormat);
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public class ProductViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice;
        private ImageView ivProductImage;
        private ConstraintLayout clProductItem;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_product_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_product_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_product_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_product_cl_product_item);
        }
    }
}
