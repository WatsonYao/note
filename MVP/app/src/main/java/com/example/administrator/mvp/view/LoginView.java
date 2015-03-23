package com.example.administrator.mvp.view;

/**
 * Created by Administrator on 2/27.
 */
public interface LoginView {

    // 这些方法名字过于抽象
    // 应该基于Use Case的逻辑起一个有意义的名称

    void showLoading();

    void hideLoading();

    void showData();

    void hideData();

    void showError();

    void hideError();

    void setLogin();

}
