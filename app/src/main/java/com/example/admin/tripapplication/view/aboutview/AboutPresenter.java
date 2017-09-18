package com.example.admin.tripapplication.view.aboutview;

import android.content.Context;

import com.example.admin.tripapplication.view.aboutview.AboutContract.View;

public class AboutPresenter implements AboutContract.Presenter {
    View view;
    private static final String TAG = "TripActivityPresenter";
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
