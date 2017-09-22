package com.example.admin.tripapplication.injection.tripinfoview;

import com.example.admin.tripapplication.view.tripinfoview.TripInfoPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TripInfoModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    TripInfoPresenter providesTripInfoPresenter(){

        return new TripInfoPresenter();
    }
}
