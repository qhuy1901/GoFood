package com.example.myapplication.customer.product_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Review;

import java.util.List;

public class ReviewForProductDetailAdapter extends RecyclerView.Adapter<com.example.myapplication.customer.product_detail.ReviewForProductDetailAdapter.ReviewForProductDetailViewHolder>{
    private final List<Review> reviewList;
    private Context context;

    public ReviewForProductDetailAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewForProductDetailAdapter.ReviewForProductDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewForProductDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_review, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewForProductDetailAdapter.ReviewForProductDetailViewHolder holder, int position) {
        Review review = reviewList.get(position);
        if(review == null)
            return ;
        holder.tvUserNameCmt.setText(review.getUser_cmt_name());
        holder.tvComment.setText(review.getCmt());
        holder.tvCommentDate.setText(review.getCmt_date());
    }

    @Override
    public int getItemCount() {
        if(reviewList != null)
            return reviewList.size();
        return 0;
    }

    public class ReviewForProductDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvComment, tvCommentDate, tvUserNameCmt;

        public ReviewForProductDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserNameCmt = (TextView) itemView.findViewById(R.id.prod_review_username);
            tvComment = (TextView) itemView.findViewById(R.id.prod_review_comment);
            tvCommentDate = (TextView) itemView.findViewById(R.id.prod_review_comment_time);
        }
    }
}

