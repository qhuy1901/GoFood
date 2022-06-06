package com.example.myapplication.customer.home.myorderpage.order_history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.customer.home.myorderpage.order_confirmation.OrderConfirmationActivity;
import com.example.myapplication.customer.store_detail.StorePageDetailActivity;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.Review;
import com.example.myapplication.models.Store;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class WriteStoreReview extends AppCompatActivity {

    private EditText txtReview;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private ImageView btnSubmitBack;
    private GoFoodDatabase goFoodDatabase;
    private Order orderInfo;

    private void initUi()
    {
        btnSubmit = (Button) findViewById(R.id.btn_submit_review);
        btnSubmitBack = (ImageView) findViewById(R.id.btn_submit_back);
        txtReview = (EditText) findViewById(R.id.txtReviewSubmit);
        ratingBar = (RatingBar) findViewById(R.id.rating_submit);
    }
    private void receiveOrdInfo(){
        Intent intent = getIntent();
        orderInfo = (Order) intent.getSerializableExtra("order");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_review_form);
        goFoodDatabase = new GoFoodDatabase();
        initUi();
        receiveOrdInfo();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            // comment review store
            @Override
            public void onClick(View view) {
                String cmt = txtReview.getText().toString();
                float rating = ratingBar.getRating();
                SharedPreferences prefs = view.getContext().getSharedPreferences("Session", view.getContext().MODE_PRIVATE);
                String userId = prefs.getString("userId", "No name defined");
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            User user = task.getResult().getValue(User.class);
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
                            String cmt_date = dateFormat.format(orderInfo.getOrderDate());
                            Review review = new Review(user.getFullName(), cmt, cmt_date, rating);
                            goFoodDatabase.insertStoreComment(review, orderInfo.getStoreId(), userId);
                            goFoodDatabase.updateRatingtotal(orderInfo.getStoreId());

                            txtReview.getText().clear();
                            new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setContentText("Viết review thành công")
                                    .show();
                        }
                    }
                });
                finish();
            }
        });
        btnSubmitBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
