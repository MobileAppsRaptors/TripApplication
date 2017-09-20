package com.example.admin.tripapplication.view.loginview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.data.FirebaseInterface;
import com.example.admin.tripapplication.injection.login.DaggerLoginComponent;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.view.drawerview.DrawerView;
import com.example.admin.tripapplication.view.profileview.ProfileView;
import com.example.admin.tripapplication.view.singupview.SingUpView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FirebaseInterface {

    private static final String TAG = "LoginView";
    private static final int RC_SIGN_IN = 10;

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email, password;

    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    LoginPresenter presenter;
    private GoogleSignInAccount account;
    FirebaseHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setupDaggerComponent();

        mAuth = FirebaseAuth.getInstance();
        fbHelper = new FirebaseHelper(this);

        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FacebookAuth();
        GoogleAuth();

        ContinueIfAuth();
    }

    private void ContinueIfAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    updateUI(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick({R.id.tvForgotPassword, R.id.btnLogIn, R.id.btnSignUp, R.id.btn_facebook, R.id.btn_google})
    public void onViewClicked(View view) {
        email = etUserName.getText().toString();
        password = etPassword.getText().toString();
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                break;
            case R.id.btnLogIn:
                EmailAuth(email, password);
                break;
            case R.id.btnSignUp:
                SignUp(email, password);
                break;
            case R.id.btn_facebook:
                FacebookAuth(email, password);
                break;
            case R.id.btn_google:
                GoogleAuth(email, password);
                break;
        }
    }

    private void setupDaggerComponent() {
        DaggerLoginComponent.create().inject(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public void FacebookAuth(String email, String password){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    private void FacebookAuth() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = new LoginButton(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_friends"));
        // If using in a fragment
//        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                FirebaseUser user = mAuth.getCurrentUser();
                //updateUI(user);
            }


            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginView.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void GoogleAuth(String email, String password){
        signIn();
    }

    public void GoogleAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getIdToken());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginView.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //TODO permissions check (internet, location)

    //TODO regular authentication

    public void EmailAuth(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()) {
                                Toast.makeText(LoginView.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else{
                                Toast.makeText(LoginView.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void SignUp(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                if (task.isSuccessful()){
                                    Toast.makeText(LoginView.this, "Account created", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(LoginView.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            } catch (Exception ex) {
                Toast.makeText(this, "Not a valid user/password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        fbHelper.GetUserData(currentUser.getUid());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        switch (requestCode) {
            //Google
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    // Google Sign In failed, update UI appropriately
                    // ...
                }
                break;
            default:
                //Facebook
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }
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
        if(user == null){
            Log.d(TAG, "parseUserData: ");
            Intent intent = new Intent(this, SingUpView.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "parseUserData: start drawerview");
            Intent intent = new Intent(this, DrawerView.class);
            startActivity(intent);
        }
    }

    @Override
    public void throwError(DatabaseError error) {
        Toast.makeText(getApplicationContext(), R.string.GET_USER_FAIL + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void operationSuccess(String operation) {
        Toast.makeText(getApplicationContext(), R.string.GET_USER_FAIL, Toast.LENGTH_SHORT).show();
    }

}
