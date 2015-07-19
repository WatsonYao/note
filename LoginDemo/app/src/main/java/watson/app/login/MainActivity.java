package watson.app.login;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import watson.app.login.data.User;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.name)
    EditText name;

    @Bind(R.id.pswd)
    EditText pswd;

    @Bind(R.id.namelayout)
    TextInputLayout nameLayout;

    @Bind(R.id.pswdlayout)
    TextInputLayout pswdLayout;

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
        User user = new User();
        user.name = name.getText().toString();
        user.pswd = pswd.getText().toString();

        // check user
        switch (user.checkField()) {
            case 1:
                nameLayout.setError("不能为空!");
                break;
            case 2:
                pswdLayout.setError("不能为空!");
                break;
            case 0:
                nameLayout.setError(null);
                pswdLayout.setError(null);
                break;
            default:
                break;
        }


        // login use
    }
}
