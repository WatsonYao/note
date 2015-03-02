package com.example.administrator.mvp.model;

import android.os.AsyncTask;

/**
 * Created by Administrator on 3/2.
 */
public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl instance;

    private UserRepositoryImpl() {

    }

    public static UserRepositoryImpl instance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getLoginResult(UserModel userModel, LoginCallback loginCallback) {
        // 需要去网络上面拿数据，异步任务内
        new MyTask(loginCallback).execute(userModel, null, null);
    }

    private class MyTask extends AsyncTask<UserModel, Void, Boolean> {

        private LoginCallback loginCallback;

        public MyTask(LoginCallback loginCallback) {
            this.loginCallback = loginCallback;
        }

        @Override
        protected Boolean doInBackground(UserModel... params) {
            UserModel userModel = params[0];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loginCallback.onLogined(aBoolean);
        }
    }
}
