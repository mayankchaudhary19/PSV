package com.example.myapplication.ui.psvStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PSVStoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PSVStoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is psv Store fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}