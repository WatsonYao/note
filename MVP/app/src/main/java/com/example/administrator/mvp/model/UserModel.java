package com.example.administrator.mvp.model;

/**
 * Created by Administrator on 2/27.
 */
public class UserModel {

    private String name;
    private String pswd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", pswd='" + pswd + '\'' +
                '}';
    }


}
