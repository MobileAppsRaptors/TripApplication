package com.example.admin.tripapplication.injection.googletrip;

import com.example.admin.tripapplication.injection.googletrip.GoogleTripModule;
import com.example.admin.tripapplication.view.googletripview.GoogleTripView;

import dagger.Component;

@Component(modules = GoogleTripModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface GoogleTripComponent {

    void inject(GoogleTripView googleTripView);

}
