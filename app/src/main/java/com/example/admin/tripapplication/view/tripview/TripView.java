package com.example.admin.tripapplication.view.tripview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.trip.DaggerTripComponent;

import javax.inject.Inject;

public class TripView extends Fragment {

    @Inject
    TripPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerTripComponent.create().inject(this);
    }

    //TODO get specified trip from database

    //testing new commit pancho
}
