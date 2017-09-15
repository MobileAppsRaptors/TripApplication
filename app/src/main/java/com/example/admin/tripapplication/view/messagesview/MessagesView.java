package com.example.admin.tripapplication.view.messagesview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.messages.DaggerMessagesComponent;

import javax.inject.Inject;

public class MessagesView extends Fragment {

    @Inject
    MessagesPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerMessagesComponent.create().inject(this);
    }
}
