package com.example.myapplication.customer.home.myorderpage.order_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProductForCustomerOrderDetailAdapter extends RecyclerView.Adapter<ProductForCustomerOrderDetailAdapter.ProductForCustomerOrderDetailViewHolder> {
    private final List<CartItem> cart;
    private Context context;

    public ProductForCustomerOrderDetailAdapter(List<CartItem> cart, Context context) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductForCustomerOrderDetailAdapter.ProductForCustomerOrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductForCustomerOrderDetailAdapter.ProductForCustomerOrderDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_for_order_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductForCustomerOrderDetailAdapter.ProductForCustomerOrderDetailViewHolder holder, int position) {
        CartItem item= cart.get(position);
        if(item == null)
            return ;
        holder.tvProductName.setText(item.product.getProductName());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(item.product.getPrice()).replace("₫", "")+ " ₫";
        holder.tvPrice.setText(price);
        holder.tvQuantity.setText("x"+item.quantity);
    }

    @Override
    public int getItemCount() {
        if(cart != null)
            return cart.size();
        return 0;
    }

    public class ProductForCustomerOrderDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvQuantity, tvPrice;
        public ProductForCustomerOrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.item_product_for_merchant_order_detail_tv_product_name);
            tvQuantity = itemView.findViewById(R.id.item_product_for_merchant_order_detail_tv_quantity);
            tvPrice = itemView.findViewById(R.id.item_product_for_merchant_order_detail_tv_unit_price);
        }
    }
}
