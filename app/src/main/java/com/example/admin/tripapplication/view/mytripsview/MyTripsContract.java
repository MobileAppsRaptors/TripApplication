package com.example.admin.tripapplication.view.mytripsview;

import android.content.Context;

import com.example.admin.tripapplication.BasePresenter;
import com.example.admin.tripapplication.BaseView;

/**
 * Created by Admin on 9/22/2017.
 */

public interface MyTripsContract {

    interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<View> {
        void setContext(Context context);
    }

}
