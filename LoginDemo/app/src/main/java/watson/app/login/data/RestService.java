package watson.app.login.data;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by axel on 7/19.
 */
public interface RestService {
    @GET("/authorizations")
    void login(
            @Query("name") String name,
            @Query("pswd") String pswd,
            Callback<LoginResponse> callback);
}
