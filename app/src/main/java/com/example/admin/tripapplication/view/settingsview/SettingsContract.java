package com.example.admin.tripapplication.view.settingsview;

import android.content.Context;

import com.example.admin.tripapplication.BasePresenter;
import com.example.admin.tripapplication.BaseView;
import com.example.admin.tripapplication.model.Settings;

import java.util.List;

public interface SettingsContract {

    interface View extends BaseView {
        void sendMenu(List<Settings> settingsList);
    }

    interface Presenter extends BasePresenter<View>{
        void setContext(Context context);

        void getMenu();
    }
}
