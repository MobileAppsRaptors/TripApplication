package com.example.admin.tripapplication.view.homeview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.home.DaggerHomeComponent;
import com.example.admin.tripapplication.util.Events;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.admin.tripapplication.util.CONSTANTS.*;


public class HomeView extends Fragment {

    @Inject
    HomePresenter presenter;

    public static final String TAG = "HomeView";
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDaggerComponent();

    }

    private void setupDaggerComponent() {
        DaggerHomeComponent.create().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnPublish, R.id.btnProfile, R.id.btnMessage, R.id.btnSearch})
    public void onViewClicked(View view) {
        Events.MessageEvent event = new Events.MessageEvent("",null);
        switch (view.getId()) {
            case R.id.btnPublish:
                event.setAction(START_PUBLISH);
                org.greenrobot.eventbus.EventBus.getDefault().post(event);
                break;
            case R.id.btnProfile:
                event.setAction(START_PROFILE);
                org.greenrobot.eventbus.EventBus.getDefault().post(event);
                break;
            case R.id.btnMessage:
                event.setAction(START_MESSAGE);
                org.greenrobot.eventbus.EventBus.getDefault().post(event);
                break;
            case R.id.btnSearch:
                event.setAction(START_SEARCH);
                org.greenrobot.eventbus.EventBus.getDefault().post(event);
                break;
        }
    }
}
