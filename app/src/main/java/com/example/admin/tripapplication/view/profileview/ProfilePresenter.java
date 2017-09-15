package com.example.admin.tripapplication.view.profileview;

import android.content.Context;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    private static final String TAG = "ProfileActivityPresenter";
    private Context context;

    @Override
    public void attachView(ProfileContract.View view) {
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
