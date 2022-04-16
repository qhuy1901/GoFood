package com.example.myapplication.customer.cart;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.CartSession;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>
{
    private final List<CartItem> cart;
    private CartSession cartSession;
    private Context context;
    private GoFoodDatabase goFoodDatabase = new GoFoodDatabase();

    public CartItemAdapter(List<CartItem> cart, Context context) {
        this.cart = cart;
        this.context = context;
        cartSession = new CartSession(context);
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
        String priceAfterFormat = currencyVN.format(cartItem.product.getPrice()).replace("₫", "")+ " ₫";
        holder.tvPrice.setText(priceAfterFormat);
//        holder.tvDescription.setText(cartItem.product.getProductDescription());
//        if(cartItem.product.getProductDescription().isEmpty())
//            holder.tvDescription.setVisibility(View.GONE);
        String topping = cartItem.getTopping();
        if(topping.equals(""))
        {
            holder.tvTopping.setVisibility(View.GONE);
        }
        else
        {
            holder.tvTopping.setText("+Topping: "+ topping);
        }
        if(!cartItem.product.getProductImage().isEmpty())
        {
            Glide.with(context).load(cartItem.product.getProductImage()).into(holder.ivProductImage);
//            goFoodDatabase.loadImageToImageView(holder.ivProductImage, "product_image" , cartItem.product.getProductImage());
        }
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int newQuantity = cartItem.quantity++;
                cartSession.updateQuantity(cartItem.product, newQuantity);
                if(newQuantity != 0)
                    holder.tvQuantity.setText(newQuantity + "");
                if (context instanceof CartActivity) {
                    ((CartActivity)context).updateTotalPrice();
                }
            }
        });
        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int newQuantity = cartItem.quantity--;
                cartSession.updateQuantity(cartItem.product, newQuantity);
                if(newQuantity == 0)
                {
                    if (context instanceof CartActivity) {
                        ((CartActivity)context).checkEmptyCartImageView();
                        ((CartActivity)context).updateTotalPrice();
                    }
                }
                else
                    holder.tvQuantity.setText(newQuantity + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cart != null)
            return cart.size();
        return 0;
    }

    public class CartItemViewHolder  extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvPrice, tvQuantity, tvTopping;
        private ImageView ivProductImage;
        private ConstraintLayout clProductItem;
        private ImageButton btnPlus, btnSubtract;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_cart_item_tv_product_name);
            tvPrice = itemView.findViewById(R.id.item_cart_item_tv_price);
            ivProductImage = itemView.findViewById(R.id.item_cart_item_iv_product_image);
            clProductItem = itemView.findViewById(R.id.item_cart_item_cl_product_item);
            btnPlus = itemView.findViewById(R.id.item_cart_item_plus);
            btnSubtract= itemView.findViewById(R.id.item_cart_item_subtract);
            tvQuantity = itemView.findViewById(R.id.item_cart_item_quantity);
            tvTopping = itemView.findViewById(R.id.item_cart_item_tv_topping);
//            tvDescription = itemView.findViewById(R.id.item_cart_item_tv_description);
        }
    }
}
