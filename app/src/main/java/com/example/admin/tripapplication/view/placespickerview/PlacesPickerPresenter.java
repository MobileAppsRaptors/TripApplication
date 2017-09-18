package com.example.admin.tripapplication.view.placespickerview;

import android.content.Context;

import com.example.admin.tripapplication.view.placespickerview.PlacesPickerContract.View;

public class PlacesPickerPresenter implements PlacesPickerContract.Presenter {
    View view;
    private static final String TAG = "PlacesPickerActivityPresenter";
    private Context context;

    @Override
    public void attachView(View view) {
        this.view = view;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
