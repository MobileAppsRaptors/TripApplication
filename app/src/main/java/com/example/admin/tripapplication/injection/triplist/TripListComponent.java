package com.example.admin.tripapplication.injection.triplist;

import com.example.admin.tripapplication.view.triplistview.TripListView;

import dagger.Component;

@Component(modules = TripListModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface TripListComponent {

    void inject(TripListView tripListView);

}
