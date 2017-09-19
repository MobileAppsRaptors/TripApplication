package com.example.admin.tripapplication.view.profileview;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.profile.DaggerProfileComponent;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileView extends AppCompatActivity {

    private static final String TAG = "ProfileView";

    @Inject
    ProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerProfileComponent.create().inject(this);
    }

    //TODO segment view by user/viewer

    //TODO get profile information for signed in user

    //TODO edit/add profile information

    //TODO get review information for user

    //TODO write review for user
}
