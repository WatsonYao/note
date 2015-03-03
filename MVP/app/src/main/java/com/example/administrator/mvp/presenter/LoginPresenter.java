package com.example.administrator.mvp.presenter;

import com.example.administrator.mvp.model.LoginResModel;

/**
 * Created by Administrator on 2/27.
 */
public interface LoginPresenter {

    void setUser(String name, String pswd);

    LoginResModel getLoginRes();

    // 业务逻辑 登录
    void login();
}
