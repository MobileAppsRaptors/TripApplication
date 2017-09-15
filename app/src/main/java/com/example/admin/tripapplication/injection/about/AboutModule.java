package com.example.admin.tripapplication.injection.about;

import com.example.admin.tripapplication.view.aboutview.AboutPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    AboutPresenter providesAboutPresenter(){

        return new AboutPresenter();
    }
}
