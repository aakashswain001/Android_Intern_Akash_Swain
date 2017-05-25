package com.example.akash.android_intern_akash_swain;

/**
 * Created by aakas on 5/24/2017.
 */

public class Comments {
    String title, desc;

    public Comments() {
    }

    public Comments(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
