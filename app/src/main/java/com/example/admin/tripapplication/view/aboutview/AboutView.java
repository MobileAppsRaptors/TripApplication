package com.example.admin.tripapplication.view.aboutview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.about.DaggerAboutComponent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Calendar;

import javax.inject.Inject;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutView extends Fragment {

    @Inject
    AboutPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        simulateDayNight(0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.logo_splash)
                .addItem(new Element().setTitle("Version 1.1"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("mobilappsdeveloperstuff@gmail.com")
                .addWebsite("https://github.com/MobileAppsRaptors")
                .addFacebook("mobileraptors.appsraptors.9")
                .addTwitter("MobileAppsRapto")
                .addYoutube("UCqVQSEnSzqxTKJ_FdGnlkug")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addInstagram("mobileraptors")
                .addGitHub("MobileAppsRaptors")
                .addItem(getCopyRightsElement())
                .create();
        return aboutPage;
        //return inflater.inflate(R.layout.fragment_about, container, false);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }


        @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerAboutComponent.create().inject(this);
    }
}
