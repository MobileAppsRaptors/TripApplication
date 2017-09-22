package com.example.admin.tripapplication.view.mytripsview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 9/22/2017.
 */

public class MyTripsView extends Fragment implements FirebaseInterface {

    @Inject
    MyTripsPresenter presenter;

    @BindView(R.id.recViewTrips)
    RecyclerView recViewTrips;

    TripAdapter tripAdapter;

    ArrayList<Trip> tripList;
    FirebaseHelper fbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytrips, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tripList = new ArrayList<>();
        tripAdapter = new TripAdapter(tripList);

        recViewTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewTrips.setItemAnimator(new DefaultItemAnimator());
        recViewTrips.setAdapter(tripAdapter);
        recViewTrips.setHasFixedSize(true);
        recViewTrips.setItemViewCacheSize(20);
        recViewTrips.setDrawingCacheEnabled(true);
        recViewTrips.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        fbHelper = new FirebaseHelper(this);
        fbHelper.GetUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void parseTrip(Trip trip) {

    }

    @Override
    public void parseGeoFireTrip(String trip_key, GeoLocation geoLocation, String source) {

    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(User user) {
        if(user.getTripList() != null) {
            for (Trip trip : user.getTripList().values()) {
                tripList.add(trip);
            }
            tripAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void parseUserReviews(Map<String, Review> reviewList) {
    }

    @Override
    public void throwError(DatabaseError error) {

    }

    @Override
    public void operationSuccess(String operation) {

    }
}
