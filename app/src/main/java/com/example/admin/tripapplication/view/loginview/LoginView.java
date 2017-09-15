package com.example.admin.tripapplication.view.loginview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.login.DaggerLoginComponent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class LoginView extends AppCompatActivity {

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerLoginComponent.create().inject(this);
    }

    //TODO facebook authentication

    //TODO google authentication

    //TODO permissions check (internet, location)

    //TODO regular authentication

    //new line
}
