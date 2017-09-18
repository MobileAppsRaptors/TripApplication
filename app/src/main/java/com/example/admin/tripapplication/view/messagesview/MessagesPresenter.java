package com.example.admin.tripapplication.view.messagesview;

import android.content.Context;

import com.example.admin.tripapplication.view.messagesview.MessagesContract.View;

public class MessagesPresenter implements MessagesContract.Presenter {
    View view;
    private static final String TAG = "MessageActivityPresenter";
    private Context context;

    @Override
    public void attachView(View view) {
        this.view = view;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
