package com.example.admin.tripapplication.injection.triplist;

import com.example.admin.tripapplication.view.triplistview.TripListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TripListModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    TripListPresenter providesTripListPresenter(){

        return new TripListPresenter();
    }
}
