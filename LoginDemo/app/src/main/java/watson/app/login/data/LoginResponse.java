package watson.app.login.data;

/**
 * Created by axel on 7/19.
 */
public class LoginResponse {

    public String message;
    public String documentation_url;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", documentation_url='" + documentation_url + '\'' +
                '}';
    }
}
