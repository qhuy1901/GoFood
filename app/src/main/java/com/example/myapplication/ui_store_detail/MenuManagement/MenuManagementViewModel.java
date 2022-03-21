package com.example.myapplication.ui_store_detail.MenuManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}