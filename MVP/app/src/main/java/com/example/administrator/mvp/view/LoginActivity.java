package com.example.administrator.mvp.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.administrator.mvp.R;
import com.example.administrator.mvp.presenter.LoginPresenter;
import com.example.administrator.mvp.presenter.LoginPresenterImpl;


public class LoginActivity extends ActionBarActivity implements LoginView {

    private EditText name, pswd;
    private Button submit;
    private ProgressBar pb;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        mLoginPresenter = new LoginPresenterImpl(this);
    }

    private void initWidget() {
        name = (EditText) findViewById(R.id.name);
        pswd = (EditText) findViewById(R.id.pswd);
        submit = (Button) findViewById(R.id.submit);
        pb = (ProgressBar) findViewById(R.id.pb);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login();
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
    public void showData() {
        Log.i("temp", "showData");
        submit.setText(mLoginPresenter.getLoginRes().getMsg() + "");
    }

    @Override
    public void hideData() {
        Log.i("temp", "hideData");
    }

    @Override
    public void showError() {
        Log.i("temp", "showError");
        submit.setText(mLoginPresenter.getLoginRes().getMsg() + "");
    }

    @Override
    public void hideError() {
        Log.i("temp", "hideError");
    }

    @Override
    public void setLogin() {
        mLoginPresenter.setUser(name.getText().toString(), pswd.getText().toString());
    }


}
