package com.k1.mvprxandroidsample;

import android.app.Application;

/**
 * Created by K1 on 7/14/16.
 */
public class MainApplication extends Application {

    public static final String BASE_URL = "https://api.github.com/";
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        resolveDependency();
        super.onCreate();
    }

    private void resolveDependency() {
        apiComponent = DaggerApiComponent.builder().networkComponent(getNetworkComponent()).build();

    }

    private NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder().networkModule(new NetworkModule(BASE_URL)).build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
