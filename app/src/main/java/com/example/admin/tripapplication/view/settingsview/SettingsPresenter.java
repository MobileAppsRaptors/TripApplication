package com.example.admin.tripapplication.view.settingsview;

import android.content.Context;

import com.example.admin.tripapplication.view.settingsview.SettingsContract.View;

public class SettingsPresenter implements SettingsContract.Presenter {
    View view;
    private static final String TAG = "SettingsActivityPresenter";
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
