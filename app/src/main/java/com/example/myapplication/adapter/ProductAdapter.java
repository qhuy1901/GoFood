package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.StoreDetailActivity;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;
import com.example.myapplication.ui_store_detail.MenuManagement.UpdateProductActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
        String priceAfterFormat = currencyVN.format(product.getPrice());
        holder.tvPrice.setText(priceAfterFormat);
        holder.clProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(product);
            }
        });
        if(!product.getProductImage().isEmpty())
        {
            goFoodDatabase.loadImageToImageView(holder.ivProductImage, "product_image" ,product.getProductImage());
        }
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
