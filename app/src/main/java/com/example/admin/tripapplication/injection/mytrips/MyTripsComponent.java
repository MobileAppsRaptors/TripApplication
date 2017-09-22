package com.example.admin.tripapplication.injection.mytrips;

import com.example.admin.tripapplication.view.mytripsview.MyTripsView;

import dagger.Component;

/**
 * Created by Admin on 9/22/2017.
 */

@Component(modules = MyTripsModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface MyTripsComponent {

    void inject(MyTripsView placesPickerView);

}