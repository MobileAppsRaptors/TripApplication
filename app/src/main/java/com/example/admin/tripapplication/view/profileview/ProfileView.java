package com.example.admin.tripapplication.view.profileview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.profile.DaggerProfileComponent;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.util.NormalButtonIcon;
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
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.admin.tripapplication.util.CONSTANTS.START_SIGNUP_ACTIVITY;
import static com.example.admin.tripapplication.util.CONSTANTS.UPDATED_USER_PROFILE;
import static com.example.admin.tripapplication.util.CONSTANTS.USER_ID;
import static com.example.admin.tripapplication.util.Functions.getImg;

public class ProfileView extends Fragment implements FirebaseInterface {

    private static final String TAG = "ProfileView";

    @Inject
    ProfilePresenter presenter;

    @BindView(R.id.btnGoEditProfile)
    ImageView btnGoEditProfile;
    @BindView(R.id.ivProfile_image)
    CircularImageView ivProfileImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnAddReview)
    NormalButtonIcon btnAddReview;

    FirebaseHelper fbHelper;
    String source_user_id;

    Unbinder unbinder;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //need to specify arguments if it's someone elses profile
        if (getArguments() != null) {
            String source_user_id = getArguments().getString(USER_ID);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupDaggerComponent();

        //if profile is being viewed by another user don't show the edit button
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (source_user_id != null && source_user_id != uid) {
            btnGoEditProfile.setVisibility(View.GONE);
        }

        fbHelper = new FirebaseHelper(this);

        fbHelper.GetPublicUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @OnClick(R.id.btnGoEditProfile)
    public void onEditProfileClick(View view) {
        Events.MessageEvent event = new Events.MessageEvent(START_SIGNUP_ACTIVITY, FirebaseAuth.getInstance().getCurrentUser().getUid());
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.btnAddReview)
    public void onAddReviewClick(View view){
        ReviewFragment reviewFragment = new ReviewFragment();
        // Show DialogFragment
        reviewFragment.show(getActivity().getSupportFragmentManager(), "Dialog Fragment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.MessageEvent event) {
        if (event.getAction().equals(UPDATED_USER_PROFILE)) {
            User user = (User) event.getObject();
            AddImgCircle(user, FirebaseAuth.getInstance());
            tvName.setText(user.getFirstName() + " " + user.getLastName());
            tvPhoneNumber.setText(user.getPhoneNumber());
            tvEmail.setText(user.getEmail());
        }
    }

    private void setupDaggerComponent() {
        DaggerProfileComponent.create().inject(this);
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
        AddImgCircle(user, FirebaseAuth.getInstance());
        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvEmail.setText(user.getEmail());
    }

    private void AddImgCircle(User user, FirebaseAuth mAuth) {
        String img = getImg(user, mAuth);
        if (img.isEmpty())
            ivProfileImage.setImageResource(R.drawable.ic_without_picture);
        else
            Picasso.with(getContext()).load(img).into(ivProfileImage);
    }

    @Override
    public void parseUserReviews(Map<String, Review> reviewList) {

    }

    @Override
    public void throwError(DatabaseError error) {
        Toast.makeText(getActivity(), R.string.GET_USER_FAIL, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void operationSuccess(String operation) {

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //TODO segment view by user/viewer

    //TODO get profile information for signed in user

    //TODO edit/add profile information

    //TODO get review information for user

    //TODO write review for user
}
