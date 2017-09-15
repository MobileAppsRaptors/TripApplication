package com.example.admin.tripapplication.injection.home;

import com.example.admin.tripapplication.view.homeview.HomeView;

import dagger.Component;

@Component(modules = HomeModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface HomeComponent {

    void inject(HomeView homeView);

}
