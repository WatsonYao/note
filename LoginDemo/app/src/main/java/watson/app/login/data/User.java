package watson.app.login.data;

/**
 * Created by axel on 7/19.
 */
public class User {
    public String name;
    public String pswd;

    public int checkField() {
        if ("".equals(name)) {
            return DataConst.USER_NAME_NULL;
        }

        if ("".equals(pswd)) {
            return DataConst.USER_PSWD_NULL;
        }

        return DataConst.USER_CHECK_OK;
    }
}
