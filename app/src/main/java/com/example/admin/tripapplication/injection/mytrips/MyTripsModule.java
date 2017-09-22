package com.example.admin.tripapplication.injection.mytrips;

/**
 * Created by Admin on 9/22/2017.
 */

import com.example.admin.tripapplication.view.mytripsview.MyTripsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyTripsModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    MyTripsPresenter providesTripListPresenter(){

        return new MyTripsPresenter();
    }
}