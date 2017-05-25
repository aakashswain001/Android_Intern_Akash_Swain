package com.example.akash.android_intern_akash_swain;

/**
 * Created by aakas on 5/25/2017.
 */

public class cmt {
    String uName, comment;

    public cmt() {
    }

    public cmt(String uName, String comment) {
        this.uName = uName;
        this.comment = comment;

    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
