package com.example.admin.tripapplication.injection.singup;

import com.example.admin.tripapplication.view.singupview.SingUpPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SingUpModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    SingUpPresenter providesSingUpPresenter(){

        return new SingUpPresenter();
    }
}
