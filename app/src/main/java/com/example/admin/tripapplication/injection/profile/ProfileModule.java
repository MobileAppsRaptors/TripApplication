package com.example.admin.tripapplication.injection.profile;

import com.example.admin.tripapplication.view.profileview.ProfilePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    ProfilePresenter providesProfilePresenter(){

        return new ProfilePresenter();
    }
}
