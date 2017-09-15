package com.example.admin.tripapplication.injection.settings;

import com.example.admin.tripapplication.view.settingsview.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    SettingsPresenter providesSettingsPresenter(){

        return new SettingsPresenter();
    }
}
