package com.example.akash.android_intern_akash_swain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private GoogleApiClient mGoogleApiClient;
    private Button signOutButton;
    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signOutButton = (Button)findViewById(R.id.sign_out_button);
        nameTextView = (TextView)findViewById(R.id.name_text_view);
        emailTextView = (TextView)findViewById(R.id.email_text_view);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getDisplayName() != null)
                        nameTextView.setText("HI " + user.getDisplayName().toString());
                        emailTextView.setText(user.getEmail().toString());

                } else {
                    // User is signed out
                 //   Log.d(TAG , "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
       Button nextButton = (Button)findViewById(R.id.next);
       nextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent myIntent = new Intent(MainActivity.this, HomeScreenActivity.class);
               MainActivity.this.startActivity(myIntent);

           }
       });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Intent myIntent = new Intent(MainActivity.this, SignInActivity.class);
                                MainActivity.this.startActivity(myIntent);
                                finish();
                            }
                        });
            }
            // ..
        });


    }
}
