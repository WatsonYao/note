package com.example.administrator.mvp.presenter;


import com.example.administrator.mvp.model.LoginModel;
import com.example.administrator.mvp.model.LoginRepository;
import com.example.administrator.mvp.model.LoginRepositoryImpl;
import com.example.administrator.mvp.model.LoginResModel;
import com.example.administrator.mvp.view.LoginView;

/**
 * Created by Administrator on 2/27.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginRepository.LoginCallback {

    private LoginModel loginModel;
    private LoginResModel loginResModel;
    private LoginView loginView;
    private LoginRepository loginRepository;

    public LoginPresenterImpl(LoginView view) {
        loginView = view;
        loginModel = new LoginModel();
        loginResModel = new LoginResModel();
        loginRepository = LoginRepositoryImpl.instance();
    }


    @Override
    public void setUser(String name, String pswd) {
        loginModel.setName(name);
        loginModel.setPswd(pswd);
    }

    @Override
    public LoginResModel getLoginRes() {
        return loginResModel;
    }


    @Override
    public void login() {
        // submit the user;
        loginView.setLogin();
        loginView.showLoading();

        if (checkUser()) {
            loginRepository.getLoginResult(loginModel, this);
        } else {
            loginView.hideLoading();
            loginView.showError();
        }
    }


    private boolean checkUser() {
        if ("".equals(loginModel.getName())) {
            loginResModel.setCode(1);
            loginResModel.setMsg("submit-Name-error");
            return false;
        }

        if ("".equals(loginModel.getPswd())) {
            loginResModel.setCode(1);
            loginResModel.setMsg("submit-pswd-error");
            return false;
        }

        return true;
    }

    @Override
    public void onLogined(LoginResModel loginResModel) {
        this.loginResModel = loginResModel;
        loginView.hideLoading();
        loginView.showData();
    }

    @Override
    public void onError(LoginResModel loginResModel) {
        this.loginResModel = loginResModel;
        loginView.hideLoading();
        loginView.showError();
    }


}
