package com.example.admin.tripapplication.injection.tripinfoview;

import com.example.admin.tripapplication.injection.tripinfoview.TripInfoModule;
import com.example.admin.tripapplication.view.tripinfoview.TripInfoView;

import dagger.Component;

@Component(modules = TripInfoModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface TripInfoComponent {

    void inject(TripInfoView tripInfoView);

}
