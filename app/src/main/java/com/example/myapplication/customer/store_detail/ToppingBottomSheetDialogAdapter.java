package com.example.myapplication.customer.store_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Topping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ToppingBottomSheetDialogAdapter extends RecyclerView.Adapter<ToppingBottomSheetDialogAdapter.ToppingBottomSheetDialogViewHolder>{
    private final List<Topping> list;
    private Context context;

    public ToppingBottomSheetDialogAdapter(List<Topping> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ToppingBottomSheetDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping_for_topping_bottom_dialog,parent,false);
        return new  ToppingBottomSheetDialogAdapter. ToppingBottomSheetDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingBottomSheetDialogViewHolder holder, int position) {
        Topping topping = list.get(position);
        if(topping == null)
            return ;
        holder.tvToppingName.setText(topping.getToppingName());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(topping.getToppingPrice()).replace("₫", "")+ " ₫";
        holder.tvPrice.setText(price);
        holder.ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof StorePageDetailActivity) {
                    if(holder.ckb.isChecked())
                        ((StorePageDetailActivity)context).getToppingBottomSheetDialog().updatePriceWhenAddTopping(topping.getToppingPrice());
                    else
                        ((StorePageDetailActivity)context).getToppingBottomSheetDialog().updatePriceWhenAddTopping(topping.getToppingPrice() * -1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public class ToppingBottomSheetDialogViewHolder extends RecyclerView.ViewHolder{
        private TextView tvToppingName, tvPrice;
        private CheckBox ckb;

        public ToppingBottomSheetDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingName = itemView.findViewById(R.id.item_topping_for_topping_bottom_dialog_tv_topping_name);
            tvPrice = itemView.findViewById(R.id.item_topping_for_topping_bottom_dialog_tv_price);
            ckb = itemView.findViewById(R.id.item_topping_for_topping_bottom_dialog_ckb_is_include);
        }
    }
}
