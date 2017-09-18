package com.example.admin.tripapplication.injection.placespicker;

import com.example.admin.tripapplication.view.placespickerview.PlacesPickerPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PlacesPickerModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    PlacesPickerPresenter providesPlacesPickerPresenter(){

        return new PlacesPickerPresenter();
    }
}
