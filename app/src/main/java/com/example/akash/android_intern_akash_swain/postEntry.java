package com.example.akash.android_intern_akash_swain;

/**
 * Created by aakas on 5/16/2017.
 */

public class postEntry {
    String title;
    String desc;
    long datePosted;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    String postId;

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
