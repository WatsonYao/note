package com.example.administrator.mvp.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.administrator.mvp.R;
import com.example.administrator.mvp.model.bean.User;
import com.example.administrator.mvp.presenter.UserPresenter;
import com.example.administrator.mvp.presenter.UserPresenterImpl;


public class MainActivity extends ActionBarActivity implements UserView {

    private EditText name, pswd;
    private Button submit;
    private ProgressBar pb;

    private UserPresenter mUserPresenter;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        mUserPresenter = new UserPresenterImpl(this);
    }

    private void initWidget() {
        name = (EditText) findViewById(R.id.name);
        pswd = (EditText) findViewById(R.id.pswd);
        submit = (Button) findViewById(R.id.submit);
        pb = (ProgressBar)findViewById(R.id.pb);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserPresenter.login();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        Log.i("temp", "showLoading");
        pb.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        Log.i("temp", "hideLoading");
        pb.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(String msg) {
        Log.i("temp", "showData");
        submit.setText(msg);
    }

    @Override
    public void hideData() {
        Log.i("temp", "hideData");
    }

    @Override
    public void showError(String error) {
        Log.i("temp", "showError");
        submit.setText(error);
    }

    @Override
    public void hideError() {
        Log.i("temp", "hideError");
    }

    @Override
    public void setUser() {
        User user = new User();
        user.setName(name.getText().toString());
        user.setPswd(pswd.getText().toString());
        mUserPresenter.setUser(user);
    }



}
