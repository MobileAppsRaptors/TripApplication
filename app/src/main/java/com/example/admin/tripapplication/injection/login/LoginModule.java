package com.example.admin.tripapplication.injection.login;

import com.example.admin.tripapplication.view.loginview.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    LoginPresenter providesLoginPresenter(){

        return new LoginPresenter();
    }
}
