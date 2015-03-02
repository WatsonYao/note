package com.example.administrator.mvp.presenter;


import com.example.administrator.mvp.model.UserModel;
import com.example.administrator.mvp.model.UserRepository;
import com.example.administrator.mvp.model.UserRepositoryImpl;
import com.example.administrator.mvp.view.UserView;

/**
 * Created by Administrator on 2/27.
 */
public class UserPresenterImpl implements UserPresenter, UserRepository.LoginCallback {

    private UserModel userModel;
    private UserView userView;
    private UserRepository userRepository;

    public UserPresenterImpl(UserView view) {
        userView = view;
        userModel = new UserModel();
        userRepository = UserRepositoryImpl.instance();
    }


    @Override
    public void setUser(String name, String pswd) {
        userModel.setName(name);
        userModel.setPswd(pswd);
    }

    @Override
    public void login() {
        // submit the user;
        userView.setUser();
        userView.showLoading();

        if (checkUser()) {
            userRepository.getLoginResult(userModel, this);
        } else {
            userView.hideLoading();
            userView.showError("submit-Error");
        }
    }


    private boolean checkUser() {
        if ("".equals(userModel.getName())) {
            return false;
        }

        if ("".equals(userModel.getPswd())) {
            return false;
        }

        return true;
    }

    @Override
    public void onLogined(boolean flag) {
        userView.hideLoading();
        userView.showData("submit-OK-" + flag);
    }

    @Override
    public void onError(String error) {
        userView.hideLoading();
        userView.showError(error);
    }


}
