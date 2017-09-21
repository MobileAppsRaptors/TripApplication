package com.example.admin.tripapplication.view.googletripview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.googletrip.DaggerGoogleTripComponent;
import com.example.admin.tripapplication.view.googletripview.GoogleTripPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class GoogleTripView extends Fragment {

    @Inject
    GoogleTripPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        setupDaggerComponent();

    }
    private void setupDaggerComponent() {
        DaggerGoogleTripComponent.create().inject(this);
    }
}