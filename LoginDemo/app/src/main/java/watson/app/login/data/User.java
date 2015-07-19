package watson.app.login.data;

/**
 * Created by axel on 7/19.
 */
public class User {
    public String name;
    public String pswd;

    public int checkField() {
        if ("".equals(name)) {
            return 1;
        }

        if ("".equals(pswd)) {
            return 2;
        }

        return 0;
    }
}
