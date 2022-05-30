package com.example.myapplication.customer.store_detail.order_tab;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.product_detail.ProductDetailActivity;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.customer.store_detail.ToppingBottomSheetDialog;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Store;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductForStoreDetailAdapter extends RecyclerView.Adapter<ProductForStoreDetailAdapter.ProductForStoreDetailViewHolder>{
    private final List<Product> productList;
    private Context context;

    private Store storeInfo;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();
    private boolean isProductHaveTopping = false;

    public ProductForStoreDetailAdapter(List<Product> productList, Context context, Store storeInfo) {
        this.productList = productList;
        this.context = context;
        this.storeInfo = storeInfo;
    }

    @NonNull
    @Override
    public ProductForStoreDetailAdapter.ProductForStoreDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_for_store_detail, parent,false);
        return new ProductForStoreDetailAdapter.ProductForStoreDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductForStoreDetailViewHolder holder, int position) {
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
        if(!product.getProductImage().isEmpty())
        {
            Glide.with(context).load(product.getProductImage()).into(holder.ivProductImage);
        }
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                List <String> toppingStringList = new ArrayList<>();
                ToppingBottomSheetDialog toppingBottomSheetDialog = new ToppingBottomSheetDialog(context, product);
                toppingBottomSheetDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "chooseTopping");

                if (context instanceof StorePageDetailActivity) {
                    ((StorePageDetailActivity)context).setToppingBottomSheetDialog(toppingBottomSheetDialog);
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(product, storeInfo);
            }
        });

        if(product.getAvailable() == 1)
        {
            holder.ivIsAvailable.setVisibility(View.GONE);
        }
        else
            holder.btnAddToCart.setVisibility(View.GONE);
        if(store.getStoreStatus() == 0)
        {
            holder.ivIsAvailable.setVisibility(View.GONE);
            holder.btnAddToCart.setVisibility(View.GONE);
        }
    }

    private void onClickGoToDetail(Product product, Store storeInfo)
    {
        Intent switchActivityIntent = new Intent(this.context, ProductDetailActivity.class);
        switchActivityIntent.putExtra("product", product);
        switchActivityIntent.putExtra("store", storeInfo);

        context.startActivity(switchActivityIntent);
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public class ProductForStoreDetailViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice, tvDescription;
        private ImageView ivProductImage, ivIsAvailable;
        private ConstraintLayout clProductItem;
        private ImageButton btnAddToCart;

        public ProductForStoreDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_product_for_store_detail_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_product_for_store_detail_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_product_for_store_detail_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_product_for_store_detail_cl_product_item);
            btnAddToCart = itemView.findViewById(R.id.item_product_for_store_detail_btn_add_to_cart);
            tvDescription = itemView.findViewById(R.id.item_product_for_store_detail_tv_description);
            ivIsAvailable = itemView.findViewById(R.id.item_product_for_store_detail_iv_is_available);
        }
    }
}
