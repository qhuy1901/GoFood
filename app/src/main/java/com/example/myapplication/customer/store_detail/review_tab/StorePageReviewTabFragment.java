package com.example.myapplication.customer.store_detail.review_tab;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.Review;
import com.example.myapplication.models.Store;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StorePageReviewTabFragment extends Fragment {
    private Store storeInfo;
    private RecyclerView rcvReview;
    private List<Review> reviewList;
    private ReviewForStoreDetailAdapter reviewForStoreDetailAdapter;
    private GoFoodDatabase goFoodDatabase;
    private ImageView cmtBtn;

    public StorePageReviewTabFragment(Store storeInfo) {
        this.storeInfo = storeInfo;
    }

    private void initUI(ViewGroup root) {
        cmtBtn = (ImageView) root.findViewById(R.id.cmtBtn);
        rcvReview = root.findViewById(R.id.storedetail_review_rcv);
        reviewList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        rcvReview.setLayoutManager(linearLayoutManager);
        reviewForStoreDetailAdapter = new ReviewForStoreDetailAdapter(reviewList, getActivity());
        rcvReview.setAdapter(reviewForStoreDetailAdapter);
    }
    private void getReviewStoreListFromRealtimeDatabase() {
        // Lấy mã cửa hàng
        String storeId = storeInfo.getStoreId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("stores").child(storeId).child("reviews");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviewList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Review review= postSnapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                reviewForStoreDetailAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Không lấy được danh sách review", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cus_shoppage_review, container, false);
        initUI(root);
        getReviewStoreListFromRealtimeDatabase();

        return root;
    }
}