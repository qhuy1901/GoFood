package com.example.myapplication.customer.home.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Home_CategoriesItemModel;

import java.util.List;

public class CategoriesItemAdapter extends RecyclerView.Adapter<CategoriesItemAdapter.HomeCategoriesViewHolder>{

    private Context context;
    private List<Home_CategoriesItemModel> li_popular_prods;
    private HomeFragment homeFragment;

    public CategoriesItemAdapter(Context context, List<Home_CategoriesItemModel> li_popular_prods, HomeFragment homeFragment){
        this.context = context;
        this.li_popular_prods = li_popular_prods;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public HomeCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeCategoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoriesViewHolder holder, int position) {
        Glide.with(context).load(li_popular_prods.get(position).getImg_url()).into(holder.catImg);
        String categoryName = li_popular_prods.get(position).getName();
        holder.catName.setText(categoryName);
        holder.clParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryName.equals("Tất cả"))
                {
//                    if (context instanceof HomeFragment) {
//                        ((HomeFragment)context).getStoreListByCategoryFromRealtimeDatabase();
//                    }
                    homeFragment.getStoreListByCategoryFromRealtimeDatabase();
                }
                else
                {
//                    if (context instanceof HomeFragment) {
//                        ((HomeFragment)context).getStoreListByCategoryFromRealtimeDatabase(categoryName);
//                    }
                    homeFragment.getStoreListByCategoryFromRealtimeDatabase(categoryName);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return li_popular_prods.size();
    }

    public class HomeCategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView catName;
        CardView clParent;

        public HomeCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);
            clParent = itemView.findViewById(R.id.item_categories_order_cv_parent);
        }
    }
}
