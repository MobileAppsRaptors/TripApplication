package com.example.admin.tripapplication.view.tripactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.tripactivity.DaggerTripActivityComponent;

import javax.inject.Inject;

public class TripActivity extends AppCompatActivity {

    @Inject
    TripActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerTripActivityComponent.create().inject(this);
    }

    //TODO get specified trip from database

    //testing new commit
}
