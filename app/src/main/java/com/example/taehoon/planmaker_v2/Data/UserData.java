package com.example.taehoon.planmaker_v2.Data;

import java.io.Serializable;

/**
 * Created by TAEHOON on 2017-05-23.
 */

public class UserData implements Serializable{
    private int id;
    private String userid;
    private String password;

    public UserData(int id, String userid, String password) {
        this.id = id;
        this.userid = userid;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}