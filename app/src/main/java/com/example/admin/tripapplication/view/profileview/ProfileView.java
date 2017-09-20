package com.example.admin.tripapplication.view.profileview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.profile.DaggerProfileComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ProfileView extends Fragment {

    private static final String TAG = "ProfileView";

    @Inject
    ProfilePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

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
