package com.example.administrator.mvp.model;

/**
 * 和数据层打交道的
 * Created by Administrator on 3/2.
 */
public interface LoginRepository {

    void getLoginResult(LoginModel loginModel, LoginCallback loginCallback);

    interface LoginCallback {
        void onLogined(LoginResModel loginResModel);

        void onError(LoginResModel loginResModel);
    }
}
