package com.example.administrator.mvp.model;

import android.os.AsyncTask;

/**
 * Created by Administrator on 3/2.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private static LoginRepositoryImpl instance;

    private LoginRepositoryImpl() {

    }

    public static LoginRepositoryImpl instance() {
        if (instance == null) {
            instance = new LoginRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getLoginResult(LoginModel loginModel, LoginCallback loginCallback) {
        // 需要去网络上面拿数据，异步任务内
        new MyTask(loginCallback).execute(loginModel, null, null);
    }

    private class MyTask extends AsyncTask<LoginModel, Void, LoginResModel> {

        private LoginCallback loginCallback;

        public MyTask(LoginCallback loginCallback) {
            this.loginCallback = loginCallback;
        }

        @Override
        protected LoginResModel doInBackground(LoginModel... params) {
            LoginModel loginModel = params[0];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LoginResModel loginResModel = new LoginResModel();
            loginResModel.setCode(0);
            loginResModel.setMsg("submit-OK");
            return loginResModel;
        }

        @Override
        protected void onPostExecute(LoginResModel loginResModel) {
            super.onPostExecute(loginResModel);

            if (loginResModel.getCode() == 1) {
                loginCallback.onLogined(loginResModel);
            } else {
                loginCallback.onError(loginResModel);
            }

        }
    }
}
