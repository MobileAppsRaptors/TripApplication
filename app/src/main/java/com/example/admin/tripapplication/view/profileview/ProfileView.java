package com.example.admin.tripapplication.view.profileview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.profile.DaggerProfileComponent;

import javax.inject.Inject;

public class ProfileView extends AppCompatActivity {

    @Inject
    ProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerProfileComponent.create().inject(this);
    }

    //TODO segment view by user/viewer

    //TODO get profile information for signed in user

    //TODO edit/add profile information

    //TODO get review information for user

    //TODO write review for user
}
