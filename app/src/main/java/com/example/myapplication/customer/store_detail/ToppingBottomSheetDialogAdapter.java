package com.example.myapplication.customer.store_detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ToppingBottomSheetDialogAdapter {
    public class ToppingBottomSheetDialogViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStoreName;
        private TextView tvStoreCategory;
        private ImageView ivAvatar;
        private ConstraintLayout clStoreItem;

        public ToppingBottomSheetDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreCategory = itemView.findViewById(R.id.tvStoreCategory);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            clStoreItem = itemView.findViewById(R.id.clStoreItem);
        }
    }
}
