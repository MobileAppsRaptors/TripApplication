package com.example.admin.tripapplication.view.drawerview;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.drawer.DaggerDrawerComponent;
import com.example.admin.tripapplication.view.homeview.HomeView;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.tripapplication.util.CONSTANTS.classSubName;
import static com.example.admin.tripapplication.util.Functions.setFragment;
import static com.example.admin.tripapplication.util.Functions.string_to_fragment;

public class DrawerView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DrawerView";

    @Inject
    DrawerPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

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
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
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
}
