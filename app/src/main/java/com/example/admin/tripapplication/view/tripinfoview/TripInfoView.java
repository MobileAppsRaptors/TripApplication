package com.example.admin.tripapplication.view.tripinfoview;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.tripinfoview.DaggerTripInfoComponent;
import com.example.admin.tripapplication.model.Settings;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.view.settingsview.SettingsAdapter;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.admin.tripapplication.util.CONSTANTS.GET_TRIP_DATA_TO_DIALOG;

public class TripInfoView extends Dialog implements TripInfoContract.View, FirebaseInterface {

    private static final String TAG = "TripInfoView";
    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvSeats)
    TextView tvSeats;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private String trip_id;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private List<User> userList = new ArrayList<>();
    private FirstAdapter firstAdapter;

    Context context;
    FirebaseHelper fbHelper;

    String creator_id = "";

    @Inject
    TripInfoPresenter presenter;

    public TripInfoView(Context context, String trip_id) {
        super(context);

        this.context = context;
        this.trip_id = trip_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_trip_info);

        ButterKnife.bind(this);

        setupDaggerComponent();

        initPresenter();

        fbHelper = new FirebaseHelper(this);

        Events.MessageEvent event = new Events.MessageEvent(GET_TRIP_DATA_TO_DIALOG,null);
        fbHelper.GetTrip(trip_id,event);
    }

    private void initPresenter() {
        presenter.attachView(this);
    }

    private void setupDaggerComponent() {
        DaggerTripInfoComponent.create().inject(this);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void parseTrip(String trip_id, Trip trip, Events.MessageEvent messageEvent) {
        if (messageEvent!=null && messageEvent.getAction() != null && messageEvent.getAction().equals(GET_TRIP_DATA_TO_DIALOG)) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = new ArrayList<>();
            double lat = trip.getOrigin().getLat();
            double lng = trip.getOrigin().getLng();
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addresses.get(0) != null) {
                tvOrigin.setText(addresses.get(0).getAddressLine(0));
            } else {
                tvOrigin.setText(lat + " ," + lng);
            }

            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = new ArrayList<>();
            lat = trip.getDestination().getLat();
            lng = trip.getDestination().getLng();
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addresses.get(0) != null) {
                tvDestination.setText(addresses.get(0).getAddressLine(0));
            } else {
                tvDestination.setText(lat + " ," + lng);
            }

            tvDate.setText(trip.getDate().toString());
            tvDescription.setText(trip.getDescription());
            tvPrice.setText(String.valueOf(trip.getCost()));
            tvSeats.setText(String.valueOf(trip.getSeats()));

            recycler.setLayoutManager(layoutManager);
            recycler.setItemAnimator(itemAnimator);
            recycler.setHasFixedSize(true);
            recycler.setItemViewCacheSize(20);
            recycler.setDrawingCacheEnabled(true);
            recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            firstAdapter = new FirstAdapter(userList);
            recycler.setAdapter(firstAdapter);

            creator_id = trip.getCreator().getUser_id();

            fbHelper.GetUserData(trip.getCreator().getUser_id());

            //TODO NEED implementation of passengers
            Map<String, User> passengerList = trip.getPassengerList();
            if(passengerList != null) {
                for (int i = 0; i < passengerList.size(); i++)
                    fbHelper.GetUserData(passengerList.get(i).getUser_id());
            }
        }
    }

    @Override
    public void parseGeoFireTrip(String trip_key, GeoLocation geoLocation, String source) {

    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(String user_id, User user) {
        if(user.getUser_id().equals(creator_id)) {
            if (userList.size() == 0)
                userList.add(user);
            else
                userList.set(0, user);
        }
        else
            userList.add(user);

        firstAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.btnClose, R.id.btnReserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnReserve:
                //TODO VALIDATE currentUserid != creator_id to Reserve
//                if(currentUserid != creator_id){
                Toast.makeText(context, "Not working still", Toast.LENGTH_SHORT).show();

//                }
                break;
        }
    }
}
