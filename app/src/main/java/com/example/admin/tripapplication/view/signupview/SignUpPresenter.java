package com.example.admin.tripapplication.view.signupview;

import android.content.Context;

public class SignUpPresenter implements SignUpContract.Presenter {
    SignUpContract.View view;
    private static final String TAG = "SingUpPresenter";
    private Context context;

    @Override
    public void attachView(SignUpContract.View view) {
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
