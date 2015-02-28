package com.example.administrator.mvp.model.bean;

/**
 * Created by Administrator on 2/27.
 */
public class User {
    private String name;
    private String pswd;

    public User(){

    }

    public String getName() {
        return name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
