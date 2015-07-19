package watson.app.login.data;

import android.content.Context;

/**
 * Created by axel on 7/19.
 */
public class DataClient {

    public static void setUser(Context context, User user) {
        context.getSharedPreferences(DataConst.SP, Context.MODE_MULTI_PROCESS).edit().putString(DataConst.SP_USER_NAME, user.name).commit();
        context.getSharedPreferences(DataConst.SP, Context.MODE_MULTI_PROCESS).edit().putString(DataConst.SP_USER_PSWD, user.pswd).commit();
    }

    public static User getUser(Context context) {
        User user = new User();
        user.name = context.getSharedPreferences(DataConst.SP, Context.MODE_MULTI_PROCESS).getString(DataConst.SP_USER_NAME, "");
        user.pswd = context.getSharedPreferences(DataConst.SP, Context.MODE_MULTI_PROCESS).getString(DataConst.SP_USER_PSWD, "");
        return user;
    }

}
