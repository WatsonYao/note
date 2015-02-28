package com.example.administrator.mvp.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.mvp.model.bean.User;
import com.example.administrator.mvp.presenter.UserPresenter;

/**
 * Created by Administrator on 2/27.
 */
public class UserModel {

    private User user;
    private Handler handler;
    private LoginListener loginListener;


    public UserModel(LoginListener loginListener){
        handler = new UserHandler();
        this.loginListener = loginListener;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    };

    public void login(){
        handler.sendEmptyMessageDelayed(0x123, 2000);
    }

    private class UserHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("temp", "handleMessage(" + msg.what + ");");

            switch (msg.what) {
                case 0x123:
                    loginListener.onLoginListener("submit-OK");
                    break;
                case 0x124:
                    loginListener.onLoginListener("submit-Error");
                    break;
            }
        }
    }

    public interface LoginListener{
        void onLoginListener(String flag);
    }
}
