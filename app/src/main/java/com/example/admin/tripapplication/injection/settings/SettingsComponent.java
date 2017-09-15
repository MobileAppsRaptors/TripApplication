package com.example.admin.tripapplication.injection.settings;

import com.example.admin.tripapplication.view.settingsview.SettingsView;

import dagger.Component;

@Component(modules = SettingsModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface SettingsComponent {

    void inject(SettingsView settingsView);

}
