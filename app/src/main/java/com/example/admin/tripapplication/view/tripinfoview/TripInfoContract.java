package com.example.admin.tripapplication.view.tripinfoview;

import com.example.admin.tripapplication.BasePresenter;
import com.example.admin.tripapplication.BaseView;
import com.example.admin.tripapplication.model.firebase.Trip;

public interface TripInfoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View>{
    }
}
