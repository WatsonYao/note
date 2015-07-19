package watson.app.login;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class UserA extends AppCompatActivity {

    ////////////////////
    // View层
    ////////////////////
    @Bind(R.id.name)
    TextView name;

    ////////////////////
    // 数据层
    ////////////////////
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usera);
        ButterKnife.bind(this);

        user = DataClient.getUser(this);
        name.setText(user.name);
        // view 是不能直接与数据进行沟通的！
        //name.setText(DataClient.getUser(this).name);
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

}
