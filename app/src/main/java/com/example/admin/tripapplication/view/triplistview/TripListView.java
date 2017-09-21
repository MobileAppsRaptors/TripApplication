package com.example.admin.tripapplication.view.triplistview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.triplist.DaggerTripListComponent;
import com.example.admin.tripapplication.util.Events;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.admin.tripapplication.util.CONSTANTS.*;

public class TripListView extends Fragment {

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

    @OnClick({R.id.btnSearch, R.id.COrigin, R.id.tvOrigin, R.id.CDestination, R.id.tvDestination, R.id.tvDate, R.id.BtnDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                Events.MessageEvent event = new Events.MessageEvent(START_GOOGLE_TRIP, null);
                org.greenrobot.eventbus.EventBus.getDefault().post(event);
                break;
            case R.id.tvOrigin:
            case R.id.COrigin:
                break;
            case R.id.tvDestination:
            case R.id.CDestination:
                break;
            case R.id.tvDate:
            case R.id.BtnDate:
                break;
        }
    }
}