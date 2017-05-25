package com.example.akash.android_intern_akash_swain;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

public class DiscussionsActivity extends AppCompatActivity {
    DatabaseReference mRef;
    RecyclerView mBlogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussions);
        Toolbar mSignInToolBar = (Toolbar) findViewById(R.id.signInToolBar);
        setSupportActionBar(mSignInToolBar);
        getSupportActionBar().setTitle("Discussions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("Posts").child(getIntent().getExtras().getString("city10"));
        final String name = getIntent().getStringExtra("city10");
        mBlogList = (RecyclerView) findViewById(R.id.blog_list2);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        final String city1 = getIntent().getExtras().getString("city10");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(getIntent().getExtras().getString("city10"));
        FirebaseRecyclerAdapter<Comments, DiscussionsActivity.CommentsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comments, CommentsViewHolder>(Comments.class, R.layout.comments_row, DiscussionsActivity.CommentsViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(final CommentsViewHolder viewHolder, Comments model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setDesc(model.getDesc());
                viewHolder.setTitle(model.getTitle());
                viewHolder.b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth m = FirebaseAuth.getInstance();
                        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(getIntent().getExtras().getString("city10")).child(post_key).child("Comments").push();
                        String p = viewHolder.e1.getText().toString();
                        if (!TextUtils.isEmpty(p)) {
                            commentsRef.child("comment").setValue(p);
                            commentsRef.child("uName").setValue(m.getCurrentUser().getDisplayName());
                            Toast.makeText(DiscussionsActivity.this, "New Comment Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DiscussionsActivity.this, "No Comments to Add", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference().child("Posts").child(getIntent().getExtras().getString("city10")).child(post_key).child("Comments");
                mRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String[] k = {""};
                        for (DataSnapshot cmtSnapshot : dataSnapshot.getChildren()) {
                            cmt c = cmtSnapshot.getValue(cmt.class);
                            k[0] = k[0] + "<br><b>" + c.getuName() + "</b> " + c.getComment();
                            viewHolder.tvcom.setText(Html.fromHtml(k[0]));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private Button b1;
        private EditText e1;
        private TextView tvcom;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvcom = (TextView) mView.findViewById(R.id.ttvcom);
            b1 = (Button) mView.findViewById(R.id.button1);
            e1 = (EditText) mView.findViewById(R.id.editText);
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


}
