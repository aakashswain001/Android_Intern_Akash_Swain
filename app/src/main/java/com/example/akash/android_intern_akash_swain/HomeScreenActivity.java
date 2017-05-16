package com.example.akash.android_intern_akash_swain;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
      //  FirebaseDatabase database = FirebaseDatabase.getInstance();
    //    DatabaseReference myRef = database.getReference("message/chutiya");
        FloatingActionButton flmenu = (FloatingActionButton)findViewById(R.id.flmenu);
        flmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomeScreenActivity.this, NewPostActivity.class);
                HomeScreenActivity.this.startActivity(myIntent);
            }
        });
  //      myRef.setValue("Hello, World!");

    }
}
