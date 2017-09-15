package com.example.admin.tripapplication.view.loginview;

import android.content.Context;

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View view;
    private static final String TAG = "LoginActivityPresenter";
    private Context context;

    @Override
    public void attachView(LoginContract.View view) {
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
