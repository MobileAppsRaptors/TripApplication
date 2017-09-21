package com.example.admin.tripapplication.view.profileview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.util.NormalButtonIcon;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.admin.tripapplication.util.CONSTANTS.CLEAR_STAR;
import static com.example.admin.tripapplication.util.CONSTANTS.FILLED_STAR;
import static com.example.admin.tripapplication.util.CONSTANTS.PARSE_SUBMITTED_REVIEW;

/**
 * Created by Admin on 9/21/2017.
 */

public class ReviewFragment extends DialogFragment {

    @BindView(R.id.btnOneStar)
    NormalButtonIcon btnOneStar;
    @BindView(R.id.btnTwoStar)
    NormalButtonIcon btnTwoStar;
    @BindView(R.id.btnThreeStar)
    NormalButtonIcon btnThreeStar;
    @BindView(R.id.btnFourStar)
    NormalButtonIcon btnFourStar;
    @BindView(R.id.btnFiveStar)
    NormalButtonIcon btnFiveStar;
    @BindView(R.id.btnSubmit)
    NormalButtonIcon btnSubmit;

    int rating;
    String user_id;
    String userName;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        getDialog().setTitle("Submit a Review");

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        user_id = args.getString("user_id");
        userName = args.getString("name");
        super.onViewCreated(view, savedInstanceState);
    }

    private void ClearStars() {
        btnOneStar.setText(CLEAR_STAR);
        btnTwoStar.setText(CLEAR_STAR);
        btnThreeStar.setText(CLEAR_STAR);
        btnFourStar.setText(CLEAR_STAR);
        btnFiveStar.setText(CLEAR_STAR);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btnOneStar, R.id.btnTwoStar, R.id.btnThreeStar, R.id.btnFourStar, R.id.btnFiveStar, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOneStar:
                ClearStars();
                btnOneStar.setText(FILLED_STAR);
                rating = 1;
                break;
            case R.id.btnTwoStar:
                ClearStars();
                btnOneStar.setText(FILLED_STAR);
                btnTwoStar.setText(FILLED_STAR);
                rating = 2;
                break;
            case R.id.btnThreeStar:
                ClearStars();
                btnOneStar.setText(FILLED_STAR);
                btnTwoStar.setText(FILLED_STAR);
                btnThreeStar.setText(FILLED_STAR);
                rating = 3;
                break;
            case R.id.btnFourStar:
                ClearStars();
                btnOneStar.setText(FILLED_STAR);
                btnTwoStar.setText(FILLED_STAR);
                btnThreeStar.setText(FILLED_STAR);
                btnFourStar.setText(FILLED_STAR);
                rating = 4;
                break;
            case R.id.btnFiveStar:
                ClearStars();
                btnOneStar.setText(FILLED_STAR);
                btnTwoStar.setText(FILLED_STAR);
                btnThreeStar.setText(FILLED_STAR);
                btnFourStar.setText(FILLED_STAR);
                btnFiveStar.setText(FILLED_STAR);
                rating = 5;
                break;
            case R.id.btnSubmit:
                //TODO add edit text
                Review review = new Review(FirebaseAuth.getInstance().getCurrentUser().getUid(), user_id, rating, null);
                Events.MessageEvent event = new Events.MessageEvent(PARSE_SUBMITTED_REVIEW, review);
                EventBus.getDefault().post(event);
                dismiss();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
