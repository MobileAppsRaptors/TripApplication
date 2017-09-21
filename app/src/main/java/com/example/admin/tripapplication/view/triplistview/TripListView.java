package com.example.admin.tripapplication.view.triplistview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.injection.triplist.DaggerTripListComponent;
import com.example.admin.tripapplication.model.SearchTrip;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.example.admin.tripapplication.util.Events;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.example.admin.tripapplication.util.CONSTANTS.*;
import static com.example.admin.tripapplication.util.Functions.getCurrentDate;
import static com.example.admin.tripapplication.util.Functions.getCurrentHour;
import static com.example.admin.tripapplication.util.Functions.getCurrentMinute;

public class TripListView extends Fragment {

    private static final String TAG = "TripListView";
    private static final int REQUEST_PLACE_ORIGIN = 1;
    private static final int REQUEST_PLACE_DESTINATION = 2;
    Location origin;
    Location destination;
    private boolean stop_duplicate_call = false;
    private FirebaseHelper fbHelper;

    @Inject
    TripListPresenter presenter;
    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvRadius)
    EditText tvRadius;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupDaggerComponent();

    }

    private void setupDaggerComponent() {
        DaggerTripListComponent.create().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSearch, R.id.COrigin, R.id.tvOrigin, R.id.CDestination, R.id.tvDestination})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                SearchTripM();
                break;
            case R.id.tvOrigin:
            case R.id.COrigin:
                if (!stop_duplicate_call) {
                    stop_duplicate_call = true;
                    try {
                        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                        Intent intent = intentBuilder.build(getActivity());
                        // Start the Intent by requesting a result, identified by a request code.
                        startActivityForResult(intent, REQUEST_PLACE_ORIGIN);

                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        Toast.makeText(getActivity(), "Google Play Services is not available.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
                break;
            case R.id.tvDestination:
            case R.id.CDestination:
                if (!stop_duplicate_call) {
                    stop_duplicate_call = true;
                    try {
                        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                        Intent intent = intentBuilder.build(getActivity());
                        // Start the Intent by requesting a result, identified by a request code.
                        startActivityForResult(intent, REQUEST_PLACE_DESTINATION);

                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        Toast.makeText(getActivity(), "Google Play Services is not available.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
                break;
        }
    }

    private void SearchTripM() {

        Date date = null;
        try {
            date = new SimpleDateFormat("mm/dd/yyyy").parse(tvDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        float radius = -1;
        try {
            radius = Float.parseFloat(tvRadius.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Number for radius is too big!!", Toast.LENGTH_SHORT).show();
            return;
        }

        String error = "";
        if (origin == null) {
            error += " origin,";
            tvOrigin.setError("Origin required");
        }
        if (destination == null) {
            error += " destination,";
            tvDestination.setError("Destination required");
        }
        if (tvDate.getText().toString().isEmpty()) {
            error += " date,";
            tvDate.setError("Date Required");
        }
        if (radius == -1) {
            error += " radius,";
            tvRadius.setError("Radius required");
        }

        if (!error.isEmpty()) {
            error = error.substring(0, error.length() - 1);
            Toast.makeText(getContext(), "You need to fill the fields: " + error, Toast.LENGTH_SHORT).show();
            return;
        }

        SearchTrip searchTrip = new SearchTrip(
                origin,
                destination,
                date,
                radius
        );

        Events.MessageEvent event = new Events.MessageEvent(START_GOOGLE_TRIP, searchTrip);
        org.greenrobot.eventbus.EventBus.getDefault().post(event);
    }

    @OnClick({R.id.BtnDate, R.id.tvDate})
    public void onDateClicked(View view) {
        final Calendar c = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.tvDate:
            case R.id.BtnDate:
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String month_format = String.format("%02d", (monthOfYear + 1));
                                String day_format = String.format("%02d", dayOfMonth);
                                String year_format = String.format("%04d", year);
                                tvDate.setText(month_format + "/" + day_format + "/" + year_format);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PLACE_ORIGIN:
                stop_duplicate_call = false;
                switch (resultCode) {
                    case RESULT_OK:
                        Place selectedPlace = PlacePicker.getPlace(getContext(), data);
                        if (selectedPlace.getAddress().toString().isEmpty())
                            tvOrigin.setText(selectedPlace.getLatLng().toString());
                        else
                            tvOrigin.setText(selectedPlace.getAddress().toString());

                        origin = new Location(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude);
                        break;
                }
                break;
            case REQUEST_PLACE_DESTINATION:
                stop_duplicate_call = false;
                switch (resultCode) {
                    case RESULT_OK:
                        Place selectedPlace = PlacePicker.getPlace(getContext(), data);
                        if (selectedPlace.getAddress().toString().isEmpty())
                            tvDestination.setText(selectedPlace.getLatLng().toString());
                        else
                            tvDestination.setText(selectedPlace.getAddress().toString());
                        destination = new Location(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude);
                        break;
                }
                break;
        }
    }
}