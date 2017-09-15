package com.example.admin.tripapplication.injection.trip;

import com.example.admin.tripapplication.view.tripview.TripPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TripModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    TripPresenter providesTripPresenter(){

        return new TripPresenter();
    }
}
