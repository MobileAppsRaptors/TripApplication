package com.example.admin.tripapplication.view.placespickerview;

import android.content.Context;

import com.example.admin.tripapplication.BasePresenter;
import com.example.admin.tripapplication.BaseView;

public interface PlacesPickerContract {

    interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<View>{
        void setContext(Context context);
    }
}
