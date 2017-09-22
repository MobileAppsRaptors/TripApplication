package com.example.admin.tripapplication.view.settingsview;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.admin.tripapplication.model.Settings;
import com.example.admin.tripapplication.view.settingsview.SettingsContract.View;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.tripapplication.util.CONSTANTS.*;

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

    @Override
    public void getMenu() {
        List<Settings> settingsList = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String zip = prefs.getString(MY_PREFS_ZIP, "No zip code");
        String units = prefs.getString(MY_PREFS_UNITS, "Fahrenheit");

        settingsList.add(new Settings("Zip", zip));
        settingsList.add(new Settings("Units", units));
        view.sendMenu(settingsList);
    }

}
