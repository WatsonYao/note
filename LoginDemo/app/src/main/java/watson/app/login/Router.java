package watson.app.login;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by axel on 7/19.
 */
public class Router {

    public static void toUserA(Activity activity) {
        Intent intent = new Intent(activity, UserA.class);
        activity.startActivity(intent);
    }
}
