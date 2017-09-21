package com.example.admin.tripapplication.injection.googletrip;


import com.example.admin.tripapplication.view.googletripview.GoogleTripPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleTripModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    GoogleTripPresenter providesGoogleTripPresenter(){

        return new GoogleTripPresenter();
    }
}
