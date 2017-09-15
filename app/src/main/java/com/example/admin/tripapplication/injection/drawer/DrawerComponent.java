package com.example.admin.tripapplication.injection.drawer;

import com.example.admin.tripapplication.view.drawerview.DrawerView;

import dagger.Component;

@Component(modules = DrawerModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface DrawerComponent {

    void inject(DrawerView drawerView);

}
