package com.example.admin.tripapplication.view.settingsview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.settings.DaggerSettingsComponent;
import com.example.admin.tripapplication.model.Settings;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.tripapplication.util.CONSTANTS.*;

public class SettingsView extends Fragment implements SettingsContract.View, ZipDialogClass.OnZipEventListener, UnitDialogClass.OnUnitEventListener {

    private static final String TAG = "SettingsView";

    @Inject
    SettingsPresenter presenter;

    @BindView(R.id.recycler_settings)
    RecyclerView recycler_settings;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private List<Settings> settingsList;
    private SettingsAdapter settingsAdapter;

    private HashMap<String, String> changes = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupDaggerComponent();

        presenter.attachView(this);
        presenter.setContext(getContext());

        initRecyclerView();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        itemAnimator = new DefaultItemAnimator();
        recycler_settings.setLayoutManager(layoutManager);
        recycler_settings.setItemAnimator(itemAnimator);
        recycler_settings.setHasFixedSize(true);
        recycler_settings.setItemViewCacheSize(20);
        recycler_settings.setDrawingCacheEnabled(true);
        recycler_settings.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        presenter.getMenu();
    }

    private void setupDaggerComponent() {
        DaggerSettingsComponent.create().inject(this);
    }

    @Override
    public void sendMenu(List<Settings> settingsList) {
        this.settingsList = settingsList;
        settingsAdapter = new SettingsAdapter(this.settingsList);
        recycler_settings.setAdapter(settingsAdapter);
        settingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void UnitUpdated(String value) {
        Log.d(TAG, "UnitUpdated: ");
        settingsList.get(1).setValue(value);
        settingsAdapter.notifyItemChanged(1);
        changes.put(MY_PREFS_UNITS,value);
    }

    @Override
    public void ZipUpdated(String value) {
        Log.d(TAG, "ZipUpdated: ");
        settingsList.get(0).setValue(value);
        settingsAdapter.notifyItemChanged(0);
        changes.put(MY_PREFS_ZIP,value);
    }

    @Override
    public void showError(String s) {

    }
}
