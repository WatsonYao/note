package watson.app.login;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import watson.app.login.data.DataClient;
import watson.app.login.data.DataConst;
import watson.app.login.data.LoginResponse;
import watson.app.login.data.RestClient;
import watson.app.login.data.User;

/**
 * 显然Activity、Fragment就是控制器
 * 所有数据都应该持久化放到SP 或者 SQLite 或者 网络服务器上面
 * Activity可以直接控制view的效果，拿view的数据
 * Activity可以直接控制Data的获取，Data的保存
 * 但View和Data之间不应该有任何业务上面的逻辑
 */
public class MainActivity extends AppCompatActivity {

    ////////////////////
    // View层
    ////////////////////
    @Bind(R.id.name)
    EditText name;

    @Bind(R.id.pswd)
    EditText pswd;

    @Bind(R.id.namelayout)
    TextInputLayout nameLayout;

    @Bind(R.id.pswdlayout)
    TextInputLayout pswdLayout;

    @Bind(R.id.outlayout)
    RelativeLayout outlayout;

    @Bind(R.id.pb)
    ProgressBar pb;

    ////////////////////
    // 数据层
    ////////////////////
    private User user;

    ////////////////////
    // Activity层
    ////////////////////
    @OnClick(R.id.submit)
    void submit() {
        submitUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitUser() {
        // make user
        user = new User();
        user.name = name.getText().toString();
        user.pswd = pswd.getText().toString();

        // check user
        int checkout = user.checkField();
        if (checkout == DataConst.USER_NAME_NULL) {
            nameLayout.setError("不能为空!");
            return;
        } else if (checkout == DataConst.USER_PSWD_NULL) {
            pswdLayout.setError("不能为空!");
            return;
        } else if (checkout == DataConst.USER_CHECK_OK) {
            nameLayout.setError(null);
            pswdLayout.setError(null);
        }

        dataLoading(true);

        // login use
        RestClient.api().login(user.name, user.pswd, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                handleOKResponse(loginResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                // 这个测试api会返回401
                handleErrorResponse(error);
            }
        });
    }

    // 虽然是View才有的效果，但相应的环节肯定还会绑定一些非View的逻辑
    private void dataLoading(boolean flag) {
        // 数据加载中true 加载结束false
        if (flag) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    private void handleErrorResponse(RetrofitError error) {
        dataLoading(false);
        Snackbar.make(outlayout, "Net Error", Snackbar.LENGTH_LONG)
                .setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
        // 测试环境
        DataClient.setUser(this, user);
        Router.toUserA(this);
    }

    private void handleOKResponse(LoginResponse loginResponse) {
        dataLoading(false);
        DataClient.setUser(this, user);
        //Toast.makeText(MainActivity.this, "login OK -" + loginResponse.message, Toast.LENGTH_SHORT).show();
        Router.toUserA(this);
    }
}
