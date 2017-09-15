package com.example.admin.tripapplication.view.homeview;

import android.content.Context;

import com.example.admin.tripapplication.view.drawerview.DrawerContract;

public class HomePresenter implements DrawerContract.Presenter {
    DrawerContract.View view;
    private static final String TAG = "DrawerActivityPresenter";
    private Context context;

    @Override
    public void attachView(DrawerContract.View view) {
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
