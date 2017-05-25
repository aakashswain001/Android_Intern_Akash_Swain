package com.example.akash.android_intern_akash_swain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int posn;
    public static String name1, uid1, email1;
    public DatabaseReference mRef;
    private TextView nameTextView;
    private TextView emailTextView;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
// Generate a new push ID for the new post
        mRef = database.getReference("Users");

        nameTextView = (TextView) findViewById(R.id.name_text_view);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (user.getDisplayName() != null)
                        nameTextView.setText("HI " + user.getDisplayName().toString());

                } else {
                    // User is signed out
                    //   Log.d(TAG , "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        name1 = user1.getDisplayName();
        uid1 = user1.getUid();
        email1 = user1.getEmail();
        nameTextView.setText("Hello ," + name1);

       /* final List<UserEntry> mUserEntries = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    UserEntry note = noteSnapshot.getValue(UserEntry.class);
                    mUserEntries.add(note);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Log.d(LOG_TAG, databaseError.getMessage());
            }
        });

      */
        final Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataFirebase();
                Intent i = new Intent(MainActivity.this, HomeScreenActivity.class);
                i.putExtra("city10", "city" + Integer.toString(posn));
                MainActivity.this.startActivity(i);
                finish();

            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select City");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                posn = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addDataFirebase() {
        List<UserEntry> sampleUserEntries = getSampleUserEntries();
        for (UserEntry userEntry : sampleUserEntries) {
            String key = mRef.push().getKey();
            userEntry.setUid(key);
            mRef.child(key).setValue(userEntry);
        }
    }

    public static List<UserEntry> getSampleUserEntries() {

        List<UserEntry> userEnrties = new ArrayList<>();
        //create the dummy journal
        UserEntry userEntry1 = new UserEntry();
        userEntry1.setName(name1);
        userEntry1.setUid(uid1);
        userEntry1.setCity("city" + Integer.toString(posn));
        userEntry1.setEmail(email1);
        userEnrties.add(userEntry1);
        return userEnrties;
    }
}
