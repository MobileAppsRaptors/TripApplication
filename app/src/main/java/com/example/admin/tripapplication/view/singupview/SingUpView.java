package com.example.admin.tripapplication.view.singupview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.singup.DaggerSingUpComponent;
import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.firebase.UserBuilder;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.admin.tripapplication.util.CONSTANTS.*;
import static com.example.admin.tripapplication.util.Functions.*;

public class SingUpView extends AppCompatActivity implements FirebaseInterface {
    private static final String TAG = "SingUpView";
    private static final int PICK_IMAGE = 11;
    @BindView(R.id.rMale)
    RadioButton rMale;
    @BindView(R.id.rFemale)
    RadioButton rFemale;

    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;
    UserBuilder userBuilder = new UserBuilder();

    @BindView(R.id.profileImage)
    CircularImageView profileImage;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.Gender)
    RadioGroup Gender;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.etState)
    EditText etState;
    @BindView(R.id.etZipCode)
    EditText etZipCode;
    @BindView(R.id.etCountry)
    EditText etCountry;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SingUpPresenter presenter;
    private Uri customImage;

    User user, user_new;

    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setTitle("Register data");

        setupDaggerComponent();

        initFirebaseAuth();

    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        AddImgCircle();

        Intent intent = getIntent();
        action = intent.getAction();
        user = intent.getParcelableExtra("user");

        if(user != null) {
            etName.setText(user.getFirstName());
            etLastName.setText(user.getLastName());
            etAddress.setText(user.getAddress());
            etCity.setText(user.getCity());
            etState.setText(user.getState());
            etZipCode.setText(user.getZip());
            etCountry.setText(user.getCountry());
            etEmail.setText(user.getEmail());
            etPhoneNumber.setText(user.getPhoneNumber());
        }
    }

    private void setupDaggerComponent() {
        DaggerSingUpComponent.create().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.log_out:
                if(action == null || action.isEmpty()) {
                    mAuth.signOut();
                    LoginManager.getInstance().logOut();
                    Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("user", user_new);
                    setResult(ACTIVITY_LOG_OUT, intent);
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(action == null || action.isEmpty()) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show();
        }
        finish();
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
    }

    @OnClick({R.id.btnCancel, R.id.btnSubmit, R.id.btnErasePic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                etName.setText("");
                etLastName.setText("");
                etAddress.setText("");
                etCity.setText("");
                etState.setText("");
                etZipCode.setText("");
                etCountry.setText("");
                etEmail.setText("");
                etPhoneNumber.setText("");
                Toast.makeText(getApplicationContext(), R.string.SIGN_UP_CANCEL, Toast.LENGTH_SHORT).show();
                //TODO maybe start splash again
                if(user != null) {
                    user.setImageURL("");
                }
                customImage = null;
                AddImgCircle();

                break;
            case R.id.btnSubmit:
                FirebaseHelper fbHelper = new FirebaseHelper(this);
                FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();

                int radioButtonID = Gender.getCheckedRadioButtonId();
                switch (radioButtonID) {
                    case R.id.rMale:
                        userBuilder.setSex(rMale.getText().toString());
                        break;
                    case R.id.rFemale:
                        userBuilder.setSex(rFemale.getText().toString());
                        break;
                }

                //Validations before continue
                String error = "";
                if(etName.getText().toString().isEmpty()) {
                    error += " Name,";
                    etName.setError("Name Required");
                }
                if(etLastName.getText().toString().isEmpty()) {
                    error += " Last Name,";
                    etLastName.setError("Last Name Required");
                }
                if(etPhoneNumber.getText().toString().isEmpty()) {
                    error += " Phone Number,";
                    etPhoneNumber.setError("Phone Required");
                }
                if(etEmail.getText().toString().isEmpty()) {
                    error += " Email,";
                    etEmail.setError("Email Required");
                }
                if(!error.isEmpty()) {
                    error = error.substring(0,error.length()-1);
                    Toast.makeText(this, "You need to fill the fields: " + error, Toast.LENGTH_SHORT).show();
                    break;
                }

                userBuilder.setUser_id(fb_user.getUid());
                userBuilder.setFirstName(etName.getText().toString());
                userBuilder.setAddress(etAddress.getText().toString());
                userBuilder.setCity(etCity.getText().toString());
                userBuilder.setState(etState.getText().toString());
                userBuilder.setZip(etZipCode.getText().toString());
                userBuilder.setCountry(etCountry.getText().toString());
                userBuilder.setEmail(etEmail.getText().toString());
                userBuilder.setPhoneNumber(etPhoneNumber.getText().toString());
                userBuilder.setLastName(etLastName.getText().toString());
                user_new = userBuilder.createUser();

                fbHelper.UpdateUser(user_new,customImage);
                break;
            case R.id.btnErasePic:
                if(user != null) {
                    user.setImageURL("");
                }
                customImage = null;
                AddImgCircle();

                break;
        }
    }

    private void AddImgCircle() {
        String img = getImg(user, mAuth);
        if(img.isEmpty())
            profileImage.setImageResource(R.drawable.ic_person_add);
        else
            Picasso.with(getApplicationContext()).load(img).into(profileImage);
    }

    @OnClick(R.id.profileImage)
    public void onViewClicked() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data.getData() != null) {
                customImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), customImage);

                    Log.d(TAG, "onActivityResult: " + customImage.getPath());
                    profileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void parseTrip(String trip_key, Trip trip) {

    }

    @Override
    public void parseGeoFireTrip(String trip_key, GeoLocation geoLocation, String source) {

    }

    @Override
    public void geoTripsFullyLoaded() {

    }

    @Override
    public void parseUserData(String user_key, User user) {
    }

    @Override
    public void parseUserReviews(Map<String, Review> reviewList) {

    }

    @Override
    public void throwError(DatabaseError error) {
        Toast.makeText(getApplicationContext(), R.string.ADD_USER_FAIL + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void operationSuccess(String operation) {
        if (operation.equals(ADD_USER_SUCC)){
            Toast.makeText(getApplicationContext(), R.string.ADD_USER_SUCC, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("user", user_new);
            setResult(ACTIVITY_SUCC, intent);
            finish();
        }

    }
}
