package com.example.akash.android_intern_akash_swain;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {
    private static String title1, desc1;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar mSignInToolBar = (Toolbar) findViewById(R.id.signInToolBar);
        setSupportActionBar(mSignInToolBar);
        getSupportActionBar().setTitle("New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
// Generate a new push ID for the new post


        mRef = database.getReference("Posts").child(getIntent().getExtras().getString("city10"));

        final com.rey.material.widget.EditText titleText = (com.rey.material.widget.EditText) findViewById(R.id.title1);
        final com.rey.material.widget.EditText descTxt = (com.rey.material.widget.EditText) findViewById(R.id.desc1);
        com.rey.material.widget.Button b1 = (com.rey.material.widget.Button) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc1 = descTxt.getText().toString();
                title1 = titleText.getText().toString();
                if (desc1.length() == 0 || title1.length() == 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewPostActivity.this);
                    alertDialog.setMessage("error");
                    alertDialog.show();

                } else {
                    ProgressDialog progressDialog = new ProgressDialog(NewPostActivity.this);
                    progressDialog.setMessage("Wait...Posting");
                    progressDialog.show();
                    addDataFirebase();
                    progressDialog.hide();
                    Intent myIntent = new Intent(NewPostActivity.this, HomeScreenActivity.class);
                    myIntent.putExtra("city10", getIntent().getExtras().getString("city10"));
                    NewPostActivity.this.startActivity(myIntent);
                    finish();
                }
            }
        });
    }

    private void addDataFirebase() {
        List<postEntry> samplePostEntries = getSamplePostEntries();
        for (postEntry postEntry : samplePostEntries) {
            String key = mRef.push().getKey();
            postEntry.setPostId(key);
            mRef.child(key).setValue(postEntry);
        }
    }

    public static List<postEntry> getSamplePostEntries() {

        List<postEntry> postEnrties = new ArrayList<>();
        //create the dummy journal
        postEntry postEntry1 = new postEntry();
        postEntry1.setTitle(title1);
        postEntry1.setDesc
                (desc1);
        Calendar calendar1 = GregorianCalendar.getInstance();
        postEntry1.setDatePosted(calendar1.getTimeInMillis());
        postEnrties.add(postEntry1);
        return postEnrties;
    }
}
