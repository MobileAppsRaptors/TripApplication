package com.example.admin.tripapplication.injection.trip;

import com.example.admin.tripapplication.view.tripview.TripView;

import dagger.Component;

@Component(modules = TripModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface TripComponent {

    void inject(TripView tripView);

}
