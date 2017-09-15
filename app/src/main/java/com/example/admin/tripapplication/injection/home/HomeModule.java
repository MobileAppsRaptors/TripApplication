package com.example.admin.tripapplication.injection.home;


import com.example.admin.tripapplication.view.homeview.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    HomePresenter providesHomePresenter(){

        return new HomePresenter();
    }
}
