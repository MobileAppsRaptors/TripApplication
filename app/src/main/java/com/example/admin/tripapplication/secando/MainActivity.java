package com.example.admin.tripapplication.secando;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.mGooglebtn)
    SignInButton mGooglebtn;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email, password;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }


    @OnClick({R.id.tvForgotPassword, R.id.btnLogIn, R.id.btnSignIn, R.id.mGooglebtn})
    public void onViewClicked(View view) {


        email = etUserName.getText().toString();
        password = etPassword.getText().toString();
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                break;
            case R.id.btnLogIn:
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    if (task.isSuccessful()) {

                                        Intent intentSignUP = new Intent(MainActivity.this, ProfilePage.class);
                                        startActivity(intentSignUP);

                                        Toast.makeText(MainActivity.this, "You are Signed In!", Toast.LENGTH_SHORT).show();
                                    }

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "Not Singed In",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                    break;

//                    mAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                                    // If sign in fails, display a message to the user. If sign in succeeds
//                                    // the auth state listener will be notified and logic to handle the
//                                    // signed in user can be handled in the listener.
//                                    if (!task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Authentication failed",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    // ...
//                                }
//                            });
//
//
//                    break;

                }
            case R.id.btnSignIn:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;

            case R.id.mGooglebtn:



                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(MainActivity.this, "You got an Error", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

                mGooglebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signIn();
                    }
                });



                //mAuth.signOut();

                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            startActivity(new Intent(MainActivity.this, Main2Activity.class));

                            Log.d(TAG, "onAuthStateChanged: " + user.getUid());
                        } else {
                            Log.d(TAG, "onAuthStateChanged: signed_out");

                        }
                    }
                };


        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:success");


                        // Sign in success, update UI with the signed-in user's information

                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
}