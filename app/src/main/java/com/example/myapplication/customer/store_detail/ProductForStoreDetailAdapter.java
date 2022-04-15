package com.example.myapplication.customer.store_detail;

import android.content.Context;
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
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductForStoreDetailAdapter extends RecyclerView.Adapter<ProductForStoreDetailAdapter.ProductForStoreDetailViewHolder>{
    private final List<Product> productList;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public ProductForStoreDetailAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
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
        String priceAfterFormat = currencyVN.format(product.getPrice());
        holder.tvPrice.setText(priceAfterFormat);
        if(!product.getProductImage().isEmpty())
        {
            Glide.with(context).load(product.getProductImage()).into(holder.ivProductImage);
        }
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                ToppingBottomSheetDialog toppingBottomSheetDialog = new ToppingBottomSheetDialog(context, product);
                toppingBottomSheetDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "chooseTopping");

                if (context instanceof StorePageDetailActivity) {
                    ((StorePageDetailActivity)context).setToppingBottomSheetDialog(toppingBottomSheetDialog);
                }

                // Lưu mã cửa hàng vào Session
//                CartSession cart = new CartSession(context);
//                CartItem cartItem = new CartItem(product, 1);
//                cart.addToCart(cartItem);
//
//                if (context instanceof StorePageDetailActivity) {
//                    ((StorePageDetailActivity)context).updateTotalPrice();
//                }
//                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Thành công")
//                                        .setContentText("Đã thêm món vào giỏ hàng!")
//                                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList != null)
            return productList.size();
        return 0;
    }

    public class ProductForStoreDetailViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice;
        private ImageView ivProductImage;
        private ConstraintLayout clProductItem;
        private ImageButton btnAddToCart;

        public ProductForStoreDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_product_for_store_detail_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_product_for_store_detail_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_product_for_store_detail_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_product_for_store_detail_cl_product_item);
            btnAddToCart = itemView.findViewById(R.id.item_product_for_store_detail_btn_add_to_cart);
        }
    }
}
