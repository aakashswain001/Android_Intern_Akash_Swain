package com.example.akash.android_intern_akash_swain;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckUserActivity extends AppCompatActivity {
    private Toolbar mSignInToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);
        mSignInToolBar = (Toolbar) findViewById(R.id.signInToolBar);
        setSupportActionBar(mSignInToolBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = database.getReference("Users");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        final String email1 = user1.getEmail();
        final ProgressDialog Dialog = new ProgressDialog(CheckUserActivity.this);
        Dialog.setMessage("Loading...");
        Dialog.show();
        final String[] city10 = new String[1];
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    UserEntry user10 = noteSnapshot.getValue(UserEntry.class);
                    String email10 = user10.getEmail();
                    if (email10.equals(email1)) {
                        city10[0] = user10.getCity();
                        Intent myIntent = new Intent(CheckUserActivity.this, HomeScreenActivity.class);
                        myIntent.putExtra("city10", city10[0]);
                        CheckUserActivity.this.startActivity(myIntent);
                        finish();
                    }
                }

                Dialog.hide();
                if (city10[0] == null) {
                    Intent myIntent = new Intent(CheckUserActivity.this, MainActivity.class);
                    CheckUserActivity.this.startActivity(myIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.d(LOG_TAG, databaseError.getMessage());
            }
        });


    }
}
