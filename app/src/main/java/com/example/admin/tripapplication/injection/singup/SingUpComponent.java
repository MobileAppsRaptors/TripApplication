package com.example.admin.tripapplication.injection.singup;

import com.example.admin.tripapplication.injection.singup.SingUpModule;
import com.example.admin.tripapplication.view.singupview.SingUpView;

import dagger.Component;

@Component(modules = SingUpModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface SingUpComponent {

    void inject(SingUpView singUpView);

}
