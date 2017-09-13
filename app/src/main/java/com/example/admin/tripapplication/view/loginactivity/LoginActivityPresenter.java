package com.example.admin.tripapplication.view.loginactivity;

import android.content.Context;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    LoginActivityContract.View view;
    private static final String TAG = "MainActivityPresenter";
    private Context context;

    @Override
    public void attachView(LoginActivityContract.View view) {
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
