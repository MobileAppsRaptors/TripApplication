package com.example.admin.tripapplication.injection.about;

import com.example.admin.tripapplication.injection.about.AboutModule;
import com.example.admin.tripapplication.view.aboutview.AboutView;

import dagger.Component;

@Component(modules = AboutModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface AboutComponent {

    void inject(AboutView aboutView);

}
