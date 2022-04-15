package com.example.myapplication.customer.store_detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class ToppingBottomSheetDialog extends BottomSheetDialogFragment {
    private Context context;
    private Product product;
    private Button btnAddToCart;
    private TextView tvProductName, tvPrice;
    private ImageView ivProductImage, ivBtnDismiss;

    private void initUi(View view)
    {
        btnAddToCart = (Button) view.findViewById(R.id.dialog_topping_bottom_sheeet_btn_add_to_cart);
        tvProductName = (TextView) view.findViewById(R.id.dialog_topping_bottom_sheeet_tv_product_name);
        tvPrice = (TextView) view.findViewById(R.id.dialog_topping_bottom_sheeet_tv_product_price);
        ivProductImage = (ImageView) view.findViewById(R.id.dialog_topping_bottom_sheeet_iv_product_image);
        ivBtnDismiss = (ImageView) view.findViewById(R.id.dialog_topping_bottom_sheeet_iv_btn_dismiss);

        tvProductName.setText(product.getProductName());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(product.getPrice()).replace("₫", "")+ " ₫";
        tvPrice.setText(price);
        if(!product.getProductImage().isEmpty())
        {
            Glide.with(context).load(product.getProductImage()).into(ivProductImage);
        }
    }

    public ToppingBottomSheetDialog(Context context, Product product) {
        this.context = context;
        this.product = product;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivBtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_topping_bottom_sheet,container,false);
        initUi(view);
        return view;
    }
}
