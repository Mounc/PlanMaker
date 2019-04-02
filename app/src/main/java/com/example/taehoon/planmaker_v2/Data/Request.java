package com.example.taehoon.planmaker_v2.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TAEHOON on 2017-05-18.
 */

public class Request implements Serializable {
    private Date topTime;
    private Date bottomTime;
    private int term;
    private ArrayList<String> userids = new ArrayList<>();

    public ArrayList<String> getUserids() {
        return userids;
    }
    public void setUserids(ArrayList<String> userids) {
        this.userids = userids;
    }
    public Date getTopTime() {
        return topTime;
    }
    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }
    public Date getBottomTime() {
        return bottomTime;
    }
    public void setBottomTime(Date bottomTime) {
        this.bottomTime = bottomTime;
    }
    public int getTerm() {
        return term;
    }
    public void setTerm(int term) {
        this.term = term;
    }

    public Request(Date topTime, Date bottomTime, int term, ArrayList<String> userids) {
        this.topTime = topTime;
        this.bottomTime = bottomTime;
        this.term = term  * 60 * 60 * 1000;
        this.userids = userids;
    }



}

