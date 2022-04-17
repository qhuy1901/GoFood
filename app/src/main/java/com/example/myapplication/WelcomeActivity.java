package com.example.myapplication;

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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.customer.home.HomeActivity;
import com.example.myapplication.merchant.choose_store.ChooseStoreActivity;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UserSession;
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
    private TextView tvUsername;
    private UserSession userSession;
    public static int type_usr = 1;
    private int REQUEST_CODE_LOCATION_PERMISSION = 44;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

    private void initUi()
    {
        btnCustomer = (Button) findViewById(R.id.btnCustomer);
        btnMerchant = (Button) findViewById(R.id.btnMerchant);
        tvUsername = (TextView) findViewById(R.id.txt_username);
    }

    private void loadCurrentUserName()
    {
        User currentUser = userSession.getUser();
        Log.e("User", currentUser.getFullName() + " Hihi");
        tvUsername.setText(currentUser.getFullName());
//        myRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUserId());
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userInfo = snapshot.getValue(User.class);
//                tvUsername.setText(userInfo.getFullName());
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        userSession = new UserSession(WelcomeActivity.this);
        WelcomeActivity.type_usr = 1;
        initUi();
        loadCurrentUserName();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(WelcomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(WelcomeActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                    Intent switchActivityIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(switchActivityIntent);
                }else{
                    ActivityCompat.requestPermissions(WelcomeActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                }

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getlocation();
                Intent switchActivityIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(switchActivityIntent);
            }else{
                Toast.makeText(this, "Permission for location denied", Toast.LENGTH_SHORT).show();
            }
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

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}