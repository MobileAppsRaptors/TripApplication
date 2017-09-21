package com.example.admin.tripapplication.view.drawerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.drawer.DaggerDrawerComponent;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.view.homeview.HomeView;
import com.example.admin.tripapplication.view.profileview.ProfileView;
import com.example.admin.tripapplication.view.singupview.SingUpView;
import com.facebook.login.LoginManager;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.tripapplication.util.CONSTANTS.classSubName;
import static com.example.admin.tripapplication.util.Functions.setFragment;
import static com.example.admin.tripapplication.util.Functions.string_to_fragment;
import static com.example.admin.tripapplication.util.CONSTANTS.*;
import static com.example.admin.tripapplication.util.Functions.*;

public class DrawerView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FirebaseInterface {

    private static final String TAG = "DrawerView";

    @Inject
    DrawerPresenter presenter;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    CircularImageView profileImage;

    TextView tvProfileName;

    User user;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_drawer);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        initDrawer();

        initHomeFragment(savedInstanceState);

        setupDaggerComponent();

        initFirebaseAuth();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.MessageEvent event){
        if(event.getAction().equals(START_SIGNUP_ACTIVITY)){
            Intent intent = new Intent(this, SingUpView.class);
            intent.putExtra("user", (String) event.getObject());
            startActivityForResult(intent, ACTIVITY_SUCC);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        User user = data.getParcelableExtra("user");
        if(resultCode == ACTIVITY_SUCC){
            Events.MessageEvent event = new Events.MessageEvent(UPDATED_USER_PROFILE, user);
            EventBus.getDefault().post(event);
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvProfileName = (TextView) header.findViewById(R.id.tvProfileName);
        profileImage = (CircularImageView) header.findViewById(R.id.profileImage);

        profileImage.setOnClickListener(this);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

    }

    private void initHomeFragment(Bundle savedInstanceState) {
        Fragment fragment = new HomeView();
        if (fragment != null && savedInstanceState == null) {
            setFragment(fragment, R.id.content_main, getSupportFragmentManager(), this);
            setTitle("Home");
        }
    }

    private void setupDaggerComponent() {
        DaggerDrawerComponent.create().inject(this);
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        AddImgCircle();

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        if(user != null)
            tvProfileName.setText(user.getFirstName() + " " +user.getLastName());
    }

    private void AddImgCircle() {
        String img = getImg(user, mAuth);
        if(img.isEmpty())
            profileImage.setImageResource(R.drawable.ic_without_picture);
        else
            Picasso.with(getApplicationContext()).load(img).into(profileImage);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            //finish();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            //Obtaining Titles when back with classSubName
            FragmentManager manager = getSupportFragmentManager();
            String class_s = manager.getBackStackEntryAt(manager.getBackStackEntryCount()-2).getName();
            String class_s_split [] = class_s.split("\\.");
            class_s = class_s_split[class_s_split.length-1];
            setTitle(classSubName.get(class_s));
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String who = item.getTitle().toString();

        Fragment fragment = string_to_fragment(getApplicationContext().getPackageName() + "." + classSubName.get(who));
        setFragment(fragment, R.id.content_main, getSupportFragmentManager(), this);
        setTitle(who);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileImage:
                Fragment fragment = new ProfileView();
                setFragment(fragment, R.id.content_main, getSupportFragmentManager(), this);
                setTitle("Profile");
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void parseTrip(Trip trip) {

    }

    @Override
    public void parseGeoFireTrip(String trip_key, GeoLocation geoLocation) {

    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(User user) {

    }

    @Override
    public void parseUserReviews(Map<String, Review> reviewList) {

    }

    @Override
    public void throwError(DatabaseError error) {

    }

    @Override
    public void operationSuccess(String operation) {

    }
}
