package com.example.admin.tripapplication.view.profileactivity;

import android.content.Context;

public class ProfileActivityPresenter implements ProfileActivityContract.Presenter {
    ProfileActivityContract.View view;
    private static final String TAG = "ProfileActivityPresenter";
    private Context context;

    @Override
    public void attachView(ProfileActivityContract.View view) {
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
