package com.example.admin.tripapplication.injection.drawer;


import com.example.admin.tripapplication.view.drawerview.DrawerPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    DrawerPresenter providesDrawerPresenter(){

        return new DrawerPresenter();
    }
}
