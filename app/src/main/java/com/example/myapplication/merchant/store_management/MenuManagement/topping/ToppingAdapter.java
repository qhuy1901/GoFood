package com.example.myapplication.merchant.store_management.MenuManagement.topping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Topping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter. ToppingViewHolder>
{
    private final List<Topping> toppingList;
    private Context context;

    public ToppingAdapter(List<Topping> toppingList, Context context) {
        this.toppingList = toppingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping,parent,false);
        return new ToppingAdapter.ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppingList.get(position);
        if(topping == null)
            return ;
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(topping.getToppingPrice()).replace("₫", "")+ " ₫";
        holder.tvPrice.setText(price);
        holder.tvToppingName.setText(topping.getToppingName());
    }

    @Override
    public int getItemCount() {
        if(toppingList != null)
            return toppingList.size();
        return 0;
    }

    public class ToppingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvToppingName;
        private TextView tvPrice;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingName = itemView.findViewById(R.id.item_topping_tv_topping_name);
            tvPrice = itemView.findViewById(R.id.item_topping_tv_price);
        }
    }
}
