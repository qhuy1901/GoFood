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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Topping;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ToppingBottomSheetDialog extends BottomSheetDialogFragment {
    public Context context;
    private Product product;
    private Button btnAddToCart;
    private TextView tvProductName, tvPrice;
    private ImageView ivProductImage, ivBtnDismiss;
    private RecyclerView rcvTopping;
    private List<Topping> toppingList;
    private ToppingBottomSheetDialogAdapter adapter;
    private  Locale localeVN;
    private NumberFormat currencyVN;
    private int currentProductPrice = 0;

    public ToppingBottomSheetDialog(Context context, Product product) {
        this.context = context;
        this.product = product;
    }

    private void initUi(View view)
    {
        btnAddToCart = (Button) view.findViewById(R.id.dialog_topping_bottom_sheeet_btn_add_to_cart);
        tvProductName = (TextView) view.findViewById(R.id.dialog_topping_bottom_sheeet_tv_product_name);
        tvPrice = (TextView) view.findViewById(R.id.dialog_topping_bottom_sheeet_tv_product_price);
        ivProductImage = (ImageView) view.findViewById(R.id.dialog_topping_bottom_sheeet_iv_product_image);
        ivBtnDismiss = (ImageView) view.findViewById(R.id.dialog_topping_bottom_sheeet_iv_btn_dismiss);
        rcvTopping = (RecyclerView) view.findViewById(R.id.dialog_topping_bottom_sheeet_rcv_topping);

        tvProductName.setText(product.getProductName());
        localeVN = new Locale("vi", "VN");
        currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(product.getPrice()).replace("₫", "")+ " ₫";
        tvPrice.setText(price);
        btnAddToCart.setText("Thêm giỏ hàng - " + price);
        if(!product.getProductImage().isEmpty())
        {
            Glide.with(context).load(product.getProductImage()).into(ivProductImage);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvTopping.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        rcvTopping.addItemDecoration(dividerItemDecoration);

        toppingList = new ArrayList<>();
        adapter  = new ToppingBottomSheetDialogAdapter(toppingList, context);
        rcvTopping.setAdapter(adapter);
    }

    private void getToppingListFromRealtimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("stores").child(product.getStoreId()).child("menu").child("topping");
        Query query = myRef.orderByChild("toppingId");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //storeList.clear();
                Topping topping = snapshot.getValue(Topping.class);
                if (topping != null) {
                    if (topping.getProductApplyList().contains(product.getProductName()))
                        toppingList.add(topping);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Topping topping = snapshot.getValue(Topping.class);
                if(topping != null)
                {
                    if(topping.getProductApplyList().contains(product.getProductName()))
                        toppingList.add(topping);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        getToppingListFromRealtimeDatabase();
        currentProductPrice = product.getPrice();
        return view;
    }

    public void updatePriceWhenAddTopping(int toppingPrice)
    {
        currentProductPrice += toppingPrice;
        String priceString = currencyVN.format(currentProductPrice).replace("₫", "")+ " ₫";
        tvPrice.setText(priceString);
        btnAddToCart.setText("Thêm giỏ hàng - " + priceString);
    }
}
