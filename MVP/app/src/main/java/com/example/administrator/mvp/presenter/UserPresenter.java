package com.example.administrator.mvp.presenter;

import com.example.administrator.mvp.model.bean.User;

/**
 * Created by Administrator on 2/27.
 */
public interface UserPresenter {

    void setUser(String name, String pswd);

    // 业务逻辑 登录
    void login();
}
