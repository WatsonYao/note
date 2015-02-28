package com.example.administrator.mvp.presenter;

import android.util.Log;

import com.example.administrator.mvp.model.UserModel;
import com.example.administrator.mvp.model.bean.User;
import com.example.administrator.mvp.view.UserView;

/**
 * Created by Administrator on 2/27.
 */
public class UserPresenterImpl implements UserPresenter, UserModel.LoginListener {

    private UserModel userModel;
    private UserView userView;

    public UserPresenterImpl(UserView view) {
        userModel = new UserModel(this);
        userView = view;
    }


    @Override
    public void setUser(String name, String pswd) {
        User user = new User();
        user.setName(name);
        user.setPswd(pswd);
        userModel.setUser(user);
    }

    @Override
    public void login() {
        // submit the user;
        userView.setUser();
        userView.showLoading();
        userModel.login();
    }

    @Override
    public void onLoginListener(String msg) {
        userView.showData(msg);
        userView.hideLoading();
    }


}
