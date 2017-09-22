package com.example.admin.tripapplication.injection.singup;

import com.example.admin.tripapplication.view.signupview.SignUpView;

import dagger.Component;

@Component(modules = SignUpModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface SignUpComponent {

    void inject(SignUpView signUpView);

}
