package com.example.administrator.mvp.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

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
    public void setUser(User user) {
        userModel.setUser(user);
    }

    @Override
    public void login() {
        // submit the user;
        // 拿到usermodel的数据 提交
        userView.setUser();
        Log.i("temp", "submit(" + userModel.getUser().getName() + "," + userModel.getUser().getPswd() + ");");
        userView.showLoading();
        userModel.login();
    }

    @Override
    public void onLoginListener(String flag) {
        userView.showData(flag);
        userView.hideLoading();
    }


}
