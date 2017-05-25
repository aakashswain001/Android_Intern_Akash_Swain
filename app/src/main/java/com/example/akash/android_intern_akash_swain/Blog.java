package com.example.akash.android_intern_akash_swain;

/**
 * Created by aakas on 5/18/2017.
 */

public class Blog {

    String title;
    String desc;

    public String getTitle() {
        return title;
    }

    public Blog() {
    }

    public Blog(String title, String desc) {
        this.title = title;
        this.desc = desc;
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
