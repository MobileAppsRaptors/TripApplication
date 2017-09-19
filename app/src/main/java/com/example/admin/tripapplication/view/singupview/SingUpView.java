package com.example.admin.tripapplication.view.singupview;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.injection.singup.DaggerSingUpComponent;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SingUpView extends AppCompatActivity {
    private static final String TAG = "SingUpView";
    private static final int PICK_IMAGE = 11;

    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
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
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setTitle("Register data");

        setupDaggerComponent();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
        getImg();
    }

    private void getImg() {
        Intent intent = getIntent();
        account = (GoogleSignInAccount) intent.getExtras().get("google");
        //Image from fb
        if (Profile.getCurrentProfile() != null) {
            Log.i(TAG, "getImg: " + "graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large");
            Picasso.with(this).load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large").into(profileImage);
        } else if (account != null) {
            Log.i(TAG, "getImg: " + account.getPhotoUrl());
            Picasso.with(this).load(account.getPhotoUrl()).into(profileImage);
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
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @OnClick({R.id.btnCancel, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                break;
            case R.id.btnSubmit:
                break;
        }
    }

    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if(resultCode == Activity.RESULT_OK && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    Log.d(TAG, "onActivityResult: " + uri.getPath());
                    profileImage.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
