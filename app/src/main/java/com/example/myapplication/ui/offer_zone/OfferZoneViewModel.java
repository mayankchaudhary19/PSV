package com.example.myapplication.ui.offer_zone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OfferZoneViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OfferZoneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is offer Zone fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}