package com.example.admin.tripapplication.injection.singup;

import com.example.admin.tripapplication.view.signupview.SignUpPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SignUpModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    SignUpPresenter providesSingUpPresenter(){

        return new SignUpPresenter();
    }
}
