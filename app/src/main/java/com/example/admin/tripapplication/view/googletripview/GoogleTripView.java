package com.example.admin.tripapplication.view.googletripview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.googletrip.DaggerGoogleTripComponent;
import com.example.admin.tripapplication.model.SearchTrip;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.example.admin.tripapplication.util.Events;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.admin.tripapplication.util.CONSTANTS.*;
import static com.example.admin.tripapplication.util.Functions.*;

public class GoogleTripView extends Fragment implements OnMapReadyCallback, FirebaseInterface {

    private static final String TAG = "GoogleTripView";
    private static final String ARG_PARAM1 = "ARG_PARAM1";

    GoogleMap mGoogleMap;
    MapView mapView;

    @Inject
    GoogleTripPresenter presenter;

    @BindView(R.id.map)
    MapView mMapView;

    Unbinder unbinder;
    private SearchTrip searchTrip;

    FirebaseHelper fbHelper;

    HashMap <String, GeoLocation> origins = new HashMap<>();
    HashMap <String, GeoLocation> destinations = new HashMap<>();

    public static GoogleTripView newInstance(SearchTrip searchTrip) {
        GoogleTripView fragment = new GoogleTripView();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, searchTrip);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.searchTrip = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_trip, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupDaggerComponent();

        initMaps();

        fbHelper = new FirebaseHelper(this);

    }

    private void initMaps() {
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    private void setupDaggerComponent() {
        DaggerGoogleTripComponent.create().inject(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Log.d(TAG, "onMapReady: ");
        fbHelper.GetGeoTrips(searchTrip.getOrigin(),searchTrip.getRadius(),"origin");
        fbHelper.GetGeoTrips(searchTrip.getOrigin(),searchTrip.getRadius(),"destination");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.MessageEvent event){
        if(event.getAction().equals(PASS_VALUES_GOOGLE)) {
            searchTrip = (SearchTrip) event.getObject();
            Log.d(TAG, "onMessageEvent: ");
            fbHelper.GetGeoTrips(searchTrip.getOrigin(),searchTrip.getRadius(),"origin");
            fbHelper.GetGeoTrips(searchTrip.getOrigin(),searchTrip.getRadius(),"destination");
        }
    }

    @Override
    public void parseTrip(Trip trip) {

    }

    @Override
    public void parseGeoFireTrip(String trip_key, GeoLocation geoLocation, String source) {
        Log.d(TAG, "parseGeoFireTrip: " + geoLocation.toString());
        if(source.equals("origin"))
            origins.put(trip_key,geoLocation);
        else
            destinations.put(trip_key,geoLocation);

        if(origins.get(trip_key) != null && destinations.get(trip_key) != null){
            BitmapDescriptor color = BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360));
            putMarker(origins.get(trip_key), trip_key, color);
            putMarker(destinations.get(trip_key), trip_key, color);
        }

    }

    public void putMarker(GeoLocation geoLocation, String trip_key, BitmapDescriptor color){
        LatLng latLng = new LatLng(geoLocation.latitude, geoLocation.longitude);
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(trip_key)
                .snippet("I hope")
                .icon(color));

        CameraPosition Liberty = CameraPosition.builder().target(latLng).zoom(16).bearing(0).tilt(45).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(User user) {

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