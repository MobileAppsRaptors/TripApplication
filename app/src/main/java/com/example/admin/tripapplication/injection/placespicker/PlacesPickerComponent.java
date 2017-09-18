package com.example.admin.tripapplication.injection.placespicker;

import com.example.admin.tripapplication.injection.placespicker.PlacesPickerModule;
import com.example.admin.tripapplication.view.placespickerview.PlacesPickerView;

import dagger.Component;

@Component(modules = PlacesPickerModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface PlacesPickerComponent {

    void inject(PlacesPickerView placesPickerView);

}
