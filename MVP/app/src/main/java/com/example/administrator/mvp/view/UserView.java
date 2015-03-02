package com.example.administrator.mvp.view;

/**
 * Created by Administrator on 2/27.
 */
public interface UserView {

    void showLoading();

    void hideLoading();

    void showData(String msg);

    void hideData();

    void showError(String error);

    void hideError();

    void setUser();

}
