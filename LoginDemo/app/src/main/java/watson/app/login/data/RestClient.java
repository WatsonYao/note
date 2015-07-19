package watson.app.login.data;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by axel on 7/19.
 */
public class RestClient {

    private static final String ENDPOINT = "https://api.github.com";

    private static RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", "Retrofit-Sample-App");
        }
    };

    private static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setRequestInterceptor(requestInterceptor)
            .setEndpoint(ENDPOINT)
            .build();

    public static RestService api() {
        return restAdapter.create(RestService.class);
    }
}
