package com.example.myapplication.merchant.store_management.MenuManagement.product;

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
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{
    private final List<Product> productList;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent,false);
        return new ProductViewHolder(view);
    }

    private void onClickGoToDetail(Product product)
    {
        Intent switchActivityIntent = new Intent(this.context, UpdateProductActivity.class);
        switchActivityIntent.putExtra("product", product);
        context.startActivity(switchActivityIntent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Product product = productList.get(position);
        if(product == null)
            return ;
        holder.tvProductName.setText(product.getProductName());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(product.getPrice()).replace("₫", "")+ " ₫";
        holder.tvPrice.setText(priceAfterFormat);

        holder.tvDescription.setText(product.getProductDescription());
        if(product.getProductDescription().isEmpty())
            holder.tvDescription.setVisibility(View.GONE);
        holder.clProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(product);
            }
        });
        Glide.with(context).load(product.getProductImage()).into(holder.ivProductImage);
//        if(!product.getProductImage().isEmpty())
//        {
////            Glide.with(context).load(product.getProductImage()).into(holder.ivProductImage);
////            goFoodDatabase.loadImageToImageView(holder.ivProductImage, "product_image" ,product.getProductImage());
//        }
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public class ProductViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice, tvDescription;
        private ImageView ivProductImage;
        private ConstraintLayout clProductItem;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_product_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_product_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_product_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_product_cl_product_item);
            tvDescription = itemView.findViewById(R.id.item_product_tv_description);
        }
    }
}
