package com.example.admin.tripapplication.injection.profile;

import com.example.admin.tripapplication.view.profileview.ProfileView;

import dagger.Component;

@Component(modules = ProfileModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface ProfileComponent {

    void inject(ProfileView profileView);

}
