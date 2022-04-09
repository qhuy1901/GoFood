package com.example.myapplication.merchant.store_management.MerchantOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MerchantOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MerchantOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}