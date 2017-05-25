package com.example.akash.android_intern_akash_swain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.Button;


public class HomeScreenActivity extends AppCompatActivity {
    DatabaseReference mRef;
    RecyclerView mBlogList;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener f;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(f);
        mRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(getIntent().getExtras().getString("city10"));
        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(Blog.class, R.layout.blog_row, BlogViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setDesc(model.getDesc());
                viewHolder.setTitle(model.getTitle());
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            com.rey.material.widget.TextView tv1 = (com.rey.material.widget.TextView) mView.findViewById(R.id.tvtitle);
            tv1.setText(title);
        }

        public void setDesc(String desc) {
            com.rey.material.widget.TextView tv2 = (com.rey.material.widget.TextView) mView.findViewById(R.id.tvdesc);
            tv2.setText(desc);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar mSignInToolBar = (Toolbar) findViewById(R.id.signInToolBar);
        setSupportActionBar(mSignInToolBar);
        getSupportActionBar().setTitle("Home");

        mAuth = FirebaseAuth.getInstance();
        f = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (mAuth.getCurrentUser() == null) {
                    Intent i = new Intent(HomeScreenActivity.this, SignInActivity.class);
                    HomeScreenActivity.this.startActivity(i);
                }
            }
        };
        Button b1 = (Button) findViewById(R.id.logout);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
// Generate a new push ID for the new post
        mRef = database.getReference().child("Posts").child(getIntent().getExtras().getString("city10"));
        final String name = getIntent().getStringExtra("city10");
        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        final String city1 = getIntent().getExtras().getString("city10");
        Button newp = (Button) findViewById(R.id.btnnew);
        newp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreenActivity.this, NewPostActivity.class);
                myIntent.putExtra("city10", name);
                HomeScreenActivity.this.startActivity(myIntent);
            }
        });
        Button disc = (Button) findViewById(R.id.dis);
        disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreenActivity.this, DiscussionsActivity.class);
                i.putExtra("city10", city1);
                HomeScreenActivity.this.startActivity(i);
            }
        });

    }


}