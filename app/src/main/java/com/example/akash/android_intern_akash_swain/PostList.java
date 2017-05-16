package com.example.akash.android_intern_akash_swain;

/**
 * Created by aakas on 5/16/2017.
 */
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class PostList {
    public String postText;
    public String postCreationDate;
    public String user1;
    public String getPostText() {
        return postText;
    }
    public void setUser1(String user1)
    {
        this.user1 = user1;
    }
    public void setPostText(String listItemText) {
        this.postText = listItemText;
    }

    public void setPostCreationDate(String listItemCreationDate) {
        this.postCreationDate = listItemCreationDate;
    }

    @Override
    public String toString() {
        return this.postText +"\n" + this.postCreationDate+"\n" +this.user1;
    }

    public String getPostCreationDate() {
        return postCreationDate;
    }
    public String getUser1(){
        return user1;
    }
    public PostList() {
        // Default constructor required for calls to DataSnapshot.getValue(ListItem.class)
    }

    public PostList(String listItemText,String user2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.postCreationDate = sdf.format(new Date());
        this.postText = listItemText;
        this.user1 = user2;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("postText", postText);
        result.put("postCreationDate", postCreationDate);
        result.put("user",user1);
        return result;
    }
}
