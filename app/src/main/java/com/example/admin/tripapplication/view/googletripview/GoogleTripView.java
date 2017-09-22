package com.example.admin.tripapplication.view.googletripview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.admin.tripapplication.util.PicassoMarker;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.admin.tripapplication.util.CONSTANTS.*;
import static com.example.admin.tripapplication.util.Functions.*;

public class GoogleTripView extends Fragment implements OnMapReadyCallback, FirebaseInterface, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "GoogleTripView";
    private static final String ARG_PARAM1 = "ARG_PARAM1";
    public static Date CURRENT_DATE;

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
        mGoogleMap.setOnMarkerClickListener(this);

        Log.d(TAG, "onMapReady: ");
        DoSearch();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        fbHelper.stopListener();
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
            DoSearch();
        }
    }

    private void DoSearch() {
        fbHelper.GetGeoTrips(searchTrip.getOrigin(),searchTrip.getRadius(),"origin");
        fbHelper.GetGeoTrips(searchTrip.getDestination(),searchTrip.getRadius(),"destination");
        CURRENT_DATE = getCurrentDateDate();

        LatLng originLatLng = new LatLng(searchTrip.getOrigin().getLat(), searchTrip.getOrigin().getLng());
        LatLng destinationLatLng = new LatLng(searchTrip.getDestination().getLat(), searchTrip.getDestination().getLng());

        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);

        drawRadius(searchTrip.getOrigin(), searchTrip.getRadius());
        drawRadius(searchTrip.getDestination(), searchTrip.getRadius());

        moveCameraBetween(searchTrip.getOrigin(), searchTrip.getDestination());
    }

    @Override
    public void parseTrip(String trip_id, Trip trip, Events.MessageEvent messageEvent) {
        if(messageEvent==null || messageEvent.getAction() == null || !messageEvent.getAction().equals(GET_TRIP_DATA_TO_DIALOG)) {
            //If Trip is not valid do nothing
            if (!validateTrip(trip_id, trip, searchTrip)) return;

            //Generate color and put the marker
            //BitmapDescriptor color = BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360));
            Bitmap icon = CustomMarker();
            putMarker(origins.get(trip_id), trip_id, icon);
            putMarker(destinations.get(trip_id), trip_id, icon);
        }
    }

    private void moveCameraBetween(Location origin, Location destination) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng originLatLng = new LatLng(origin.getLat(), origin.getLng());
        LatLng destinationLatLng = new LatLng(destination.getLat(), destination.getLng());
        builder.include(originLatLng);
        builder.include(destinationLatLng);
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mGoogleMap.moveCamera(cu);
        mGoogleMap.animateCamera(cu);
    }

    private void drawRadius(Location location, Float radius) {
        mGoogleMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLat(), location.getLng()))
                .radius(radius*1000)
                .strokeColor(0x220000FF)
                .fillColor(0x220000FF)
        );
    }

    private Bitmap CustomMarker() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        color = Color.HSVToColor(hsv);

        Bitmap ob = drawableToBitmap(ContextCompat.getDrawable(getActivity(), R.drawable.ic_car), color);
        Bitmap obm = Bitmap.createBitmap(ob.getWidth(), ob.getHeight(), ob.getConfig());
        Canvas canvas = new Canvas(obm);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(ob, 0f, 0f, paint);
        return obm;
    }

    private boolean validateTrip(String trip_id, Trip trip, SearchTrip searchTrip) {
        if(trip.getDate().compareTo(CURRENT_DATE) >= 0
                //&& searchTrip.getRadius() > distanceInKmBetweenEarthCoordinates(searchTrip.getOrigin().getLat(), searchTrip.getOrigin().getLng(), searchTrip.getDestination().getLat(), searchTrip.getDestination().getLng())
                //&&
                )
            return true;

        return false;
    }

    @Override
    public void parseGeoFireTrip(String trip_id, GeoLocation geoLocation, String source) {
        Log.d(TAG, "parseGeoFireTrip: " + geoLocation.toString());
        if(source.equals("origin"))
            origins.put(trip_id,geoLocation);
        else
            destinations.put(trip_id,geoLocation);

        if(origins.get(trip_id) != null && destinations.get(trip_id) != null){
            fbHelper.GetTrip(trip_id);
        }

    }

    public void putMarker(GeoLocation geoLocation, String trip_id, Bitmap icon){
        LatLng latLng = new LatLng(geoLocation.latitude, geoLocation.longitude);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
//                .title(trip_id)
                .snippet("I hope")
                .snippet(trip_id)
                .icon(BitmapDescriptorFactory.fromBitmap(icon)));
    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(String user_id, User user) {

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

    private String getMapsApiDirectionsUrl() {
        String origin = searchTrip.getOrigin().getLat() + "," + searchTrip.getOrigin().getLng();
        String destination = searchTrip.getDestination().getLat() + "," + searchTrip.getDestination().getLng();

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + API_KEY;
        return url;
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(6);
                polyLineOptions.color(Color.RED);
            }

            mGoogleMap.addPolyline(polyLineOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Events.MessageEvent event = new Events.MessageEvent(GET_TRIP_TO_DIALOG, marker.getSnippet());
        org.greenrobot.eventbus.EventBus.getDefault().post(event);
        return true;
    }
}