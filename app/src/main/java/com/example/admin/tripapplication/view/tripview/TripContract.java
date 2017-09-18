package com.example.admin.tripapplication.view.tripview;

import android.content.Context;

import com.example.admin.tripapplication.BasePresenter;
import com.example.admin.tripapplication.BaseView;
import com.example.admin.tripapplication.model.firebase.Trip;

public interface TripContract {

    interface View extends BaseView {


        void sendInsertStatus(boolean status);
    }

    interface Presenter extends BasePresenter<View>{
        void InsertTrip(Trip trip);
    }
}
