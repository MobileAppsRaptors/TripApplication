package com.example.admin.tripapplication.view.singupview;

import android.content.Context;

import com.example.admin.tripapplication.view.singupview.SingUpContract;

public class SingUpPresenter implements SingUpContract.Presenter {
    SingUpContract.View view;
    private static final String TAG = "SingUpPresenter";
    private Context context;

    @Override
    public void attachView(SingUpContract.View view) {
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
