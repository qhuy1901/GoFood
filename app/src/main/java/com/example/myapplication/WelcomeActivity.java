package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.merchant.choose_store.ChooseStoreActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity implements LocationListener{


    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Button btnCustomer;
    private Button btnMerchant;
    public static int type_usr = 1;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

    private void initUi()
    {
        btnCustomer = (Button) findViewById(R.id.btnCustomer);
        btnMerchant = (Button) findViewById(R.id.btnMerchant);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initUi();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(WelcomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(WelcomeActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                }else{
                    ActivityCompat.requestPermissions(WelcomeActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                Intent switchActivityIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(switchActivityIntent);

            }
        });

        btnMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WelcomeActivity.type_usr = 0;
                Intent switchActivityIntent = new Intent(WelcomeActivity.this, ChooseStoreActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getlocation(){
        try{
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) WelcomeActivity.this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            Geocoder geocoder = new Geocoder(WelcomeActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            myRef.child(LoginTabFragment.UID).child("cur_location").setValue(address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}