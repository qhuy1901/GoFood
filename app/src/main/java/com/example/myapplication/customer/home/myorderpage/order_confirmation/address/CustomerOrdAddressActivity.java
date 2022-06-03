package com.example.myapplication.customer.home.myorderpage.order_confirmation.address;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GoFoodDatabase;
import com.example.myapplication.R;
import com.example.myapplication.models.OrdAddress;
import com.example.myapplication.models.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrdAddressActivity extends AppCompatActivity {

    private ImageView ivBtnBack;
    private RecyclerView rcvLiAddress;
    private List<OrdAddress> liOrdAddress;
    private CustomerOrdAddressAdapter adapter;
    private EditText edtName, edtAddress, edtPhone_Number;
    private Button btnAddAddress;
    private String userId;
    private GoFoodDatabase goFoodDatabase;
    private Store storeInfo;

    private void initUi()
    {
        ivBtnBack = (ImageView) findViewById(R.id.activity_customer_address_iconBack);
        edtName = (EditText) findViewById(R.id.activity_customer_address_receiver_name);
        edtAddress = (EditText) findViewById(R.id.activity_customer_address_receiver_address);
        edtPhone_Number = (EditText) findViewById(R.id.activity_customer_address_receiver_phone);
        btnAddAddress = (Button) findViewById(R.id.activity_customer_address_btnAddAdress);
        rcvLiAddress = (RecyclerView) findViewById(R.id.rcv_li_address_ord);
        liOrdAddress = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CustomerOrdAddressActivity.this);
        rcvLiAddress.setLayoutManager(linearLayoutManager);
        adapter =  new CustomerOrdAddressAdapter(liOrdAddress, CustomerOrdAddressActivity.this, storeInfo);
        rcvLiAddress.setAdapter(adapter);
    }
    public void getOrdAddressList()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(userId).child("address_ord_list");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                liOrdAddress.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    OrdAddress ordAddress = postSnapshot.getValue(OrdAddress.class);
                    liOrdAddress.add(ordAddress);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CustomerOrdAddressActivity.this, "Không lấy được list addres", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void receiveStoreInfo()
    {
        Intent intent = getIntent();
        storeInfo = (Store) intent.getSerializableExtra("store");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        // receive user info
        SharedPreferences prefs = this.getSharedPreferences("Session", this.MODE_PRIVATE);
        userId = prefs.getString("userId", "No name defined");

        // receive store info
        receiveStoreInfo();

        // connect to gofood database
        goFoodDatabase = new GoFoodDatabase();

        initUi();
        getOrdAddressList();

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edtAddress.getText().toString();
                String name = edtName.getText().toString();
                String phone_number = edtPhone_Number.getText().toString();

                OrdAddress ordAddress = new OrdAddress(name, address, phone_number);
                goFoodDatabase.insertOrdAddress(ordAddress, userId);
            }
        });
    }
}