package com.example.admin.tripapplication.view.tripview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.trip.DaggerTripComponent;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.example.admin.tripapplication.util.Functions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.example.admin.tripapplication.util.Functions.*;

public class TripView extends Fragment implements TripContract.View {

    private static final int REQUEST_PLACE_ORIGIN = 1;
    private static final int REQUEST_PLACE_DESTINATION = 2;
    private static final String TAG = "TripView";
    Location origin;
    Location destination;

    @Inject
    TripPresenter presenter;


    @BindView(R.id.tvHour)
    TextView tvHour;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvDate)
    TextView tvDate;
    Unbinder unbinder;
    @BindView(R.id.tvDescription)
    EditText tvDescription;
    @BindView(R.id.Seats)
    HorizontalPicker Seats;
    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.CDestination)
    BootstrapButton CDestination;
    @BindView(R.id.COrigin)
    BootstrapButton COrigin;
    private boolean stop_duplicate_call = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TypefaceProvider.registerDefaultIconSets();
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDaggerComponent();

        initPresenter();
    }

    private void initPresenter() {
        presenter.attachView(this);
    }

    private void setupDaggerComponent() {
        DaggerTripComponent.create().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.CDestination, R.id.COrigin, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.COrigin:
                if(!stop_duplicate_call) {
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
            case R.id.CDestination:
                if(!stop_duplicate_call) {
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
            case R.id.btnSubmit:
                CollectDataToInsert();

                break;
        }
    }

    private void CollectDataToInsert() {
        Date date = null;
        try {
            date = new SimpleDateFormat("mm/dd/yyyy HH:mm").parse(tvDate.getText().toString() + " " + tvHour.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double leniancy = 2;
        User user = null;  //TODO need implementation of user still
        int seats = Seats.getSelectedItem();
        float cost = Float.parseFloat(etPrice.getText().toString());
        List<User> passengerList = new ArrayList<>();

        String error = "";
        if(origin == null)
            error += " origin,";
        if(destination == null)
            error += " destination,";
        if(tvDate.getText().toString().isEmpty())
            error += " date,";
        if(tvHour.getText().toString().isEmpty())
            error += " hour,";

        if(!error.isEmpty()) {
            Toast.makeText(getContext(), "You need to fill the fields: " + error, Toast.LENGTH_SHORT).show();
            return;
        }

        Trip trip = new Trip(
                origin,
                destination,
                date,
                leniancy,
                user,
                seats,
                cost,
                passengerList
        );
        presenter.InsertTrip(trip);
    }

    @OnClick({R.id.BtnDate, R.id.BtnHour})
    public void onDateClicked(View view) {
        final Calendar c = Calendar.getInstance();
        switch (view.getId()) {
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


                                //Validating is not time in the past
                                Double current_hour = getCurrentHour();
                                Double current_minute = getCurrentMinute();

                                String today = getCurrentDate();

                                if(!tvHour.getText().toString().isEmpty()){
                                    Double selected_hour = Double.parseDouble(tvHour.getText().toString().split(":")[0]);
                                    Double selected_minute = Double.parseDouble(tvHour.getText().toString().split(":")[1]);
                                    if(tvDate.getText().toString().equals(today) && (selected_hour < current_hour || (selected_hour == current_hour && selected_minute < current_minute))){
                                        Toast.makeText(getContext(), "You must not put time in the past", Toast.LENGTH_SHORT).show();
                                        tvHour.setText("");
                                    }
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
                break;
            case R.id.BtnHour:
                // Get Current Time
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                final int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String hour_format = String.format("%02d", hourOfDay);
                                String minute_format = String.format("%02d", minute);

                                //Validating is not time in the past
                                String today = getCurrentDate();
                                if(tvDate.getText().toString().equals(today) && (hourOfDay < mHour || (hourOfDay == mHour && minute < mMinute))){
                                    Toast.makeText(getContext(), "You must not put time in the past", Toast.LENGTH_SHORT).show();
                                    tvHour.setText("");
                                }
                                else
                                    tvHour.setText(hour_format + ":" + minute_format);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
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

    @Override
    public void showError(String s) {

    }

    @Override
    public void sendInsertStatus(boolean status) {
        String status_string = (status) ? "successfully" : "unsuccessfully";

        Toast.makeText(getContext(), "Your insert was " + status_string, Toast.LENGTH_SHORT).show();
    }

    //TODO get specified trip from database


}
