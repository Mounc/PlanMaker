package com.example.taehoon.planmaker_v2.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TAEHOON on 2017-05-18.
 */

public class Response implements Serializable {
    private Date startTime = new Date();
    private Date endTime = new Date();

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    private String place;


    public Response() {
        startTime = new Date();
        endTime = new Date();
    }

    public Response(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}