package com.example.administrator.mvp.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.mvp.model.UserModel;
import com.example.administrator.mvp.model.bean.User;
import com.example.administrator.mvp.view.UserView;

/**
 * Created by Administrator on 2/27.
 */
public class UserPresenterImpl implements UserPresenter {

    private UserModel userModel;
    private UserView userView;
    private Handler handler;

    public UserPresenterImpl(UserView view) {
        userModel = new UserModel();
        userView = view;
        handler = new UserHandler();
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
        //handler.sendEmptyMessageDelayed(0x123, 2000);
        handler.sendEmptyMessageDelayed(0x124, 2000);
    }


    private class UserHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("temp", "handleMessage(" + msg.what + ");");

            switch (msg.what) {
                case 0x123:
                    userView.showData("showdata");
                    userView.hideLoading();
                    break;
                case 0x124:
                    userView.showError("submit(error!)");
                    userView.hideLoading();
                    break;

            }
        }
    }

}
