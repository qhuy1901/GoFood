package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>
{
    private final List<CartItem> cart;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public CartItemAdapter(List<CartItem> cart, Context context) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_item, parent,false);
        return new CartItemAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cart.get(position);
        if(cartItem == null)
            return ;
        holder.tvProductName.setText(cartItem.product.getProductName());
        holder.tvQuantity.setText(cartItem.quantity+"");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String priceAfterFormat = currencyVN.format(cartItem.product.getPrice());
        holder.tvPrice.setText(priceAfterFormat);

        if(!cartItem.product.getProductImage().isEmpty())
        {
            goFoodDatabase.loadImageToImageView(holder.ivProductImage, "product_image" , cartItem.product.getProductImage());
        }
    }

    @Override
    public int getItemCount() {
        if(cart != null)
            return cart.size();
        return 0;
    }

    public class CartItemViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice, tvQuantity;
        private ImageView ivProductImage;
        private ConstraintLayout clProductItem;
        private Button btnPlus, btnSubtract;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_cart_item_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_cart_item_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_cart_item_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_cart_item_cl_product_item);
            btnPlus = itemView.findViewById(R.id.item_cart_item_plus);
            btnSubtract= itemView.findViewById(R.id.item_cart_item_subtract);
            tvQuantity = itemView.findViewById(R.id.item_cart_item_quantity);
        }
    }
}
