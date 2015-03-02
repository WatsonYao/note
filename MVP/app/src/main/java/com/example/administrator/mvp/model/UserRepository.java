package com.example.administrator.mvp.model;

/**
 * 和数据层打交道的
 * Created by Administrator on 3/2.
 */
public interface UserRepository {

    void getLoginResult(UserModel userModel,LoginCallback loginCallback);

    interface LoginCallback {
        void onLogined(boolean flag);

        void onError(String error);
    }
}
