package com.k1.mvprxandroidsample;

import android.app.Application;

import com.k1.mvprxandroidsample.dependency.ApiComponent;
import com.k1.mvprxandroidsample.dependency.DaggerApiComponent;
import com.k1.mvprxandroidsample.dependency.DaggerNetworkComponent;
import com.k1.mvprxandroidsample.dependency.NetworkComponent;
import com.k1.mvprxandroidsample.dependency.NetworkModule;


/**
 * Next Step is the container for your component, Depending on your application, you may choose to store
 * your components in more complicated ways, but for the sake of this example, a single component stored
 * in application class will suffix.
 * <a href="https://blog.gouline.net/dagger-2-even-sharper-less-square-b52101863542#.axqtn7xd5>Read More</a>
 * <p>
 * {@link #mApplicationComponent} field of {@link ApplicationComponent } that injects {@link ApplicationModule}
 * with defined targets
 * <p>
 * Nothing particularly new here: instead of the usual ObjectGraph, you build and instantiate the corresponding component.
 * <p/>
 * Note: Chances are you will wonder about this at some point, so I will explain ahead of time.
 * The
 * , which only appears once the project has been rebuilt since the last changes to the component.
 * So if you are not finding it, hit "Rebuild Project" in your IDE and ensure that you don't have any
 * compile errors,
 * </p>
 * Created by K1 on 7/14/16.
 */
public class MainApplication extends Application {

    public static final String BASE_URL = "https://api.github.com/";
    private ApiComponent mApiComponent;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        resolveDependency();

        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
    }


    /**
     * To resolve dependency
     */
    private void resolveDependency() {
        mApiComponent = DaggerApiComponent.builder().networkComponent(getNetworkComponent()).build();

    }

    /**
     * @return
     */
    private NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder().networkModule(new NetworkModule(BASE_URL)).build();
    }

    /**
     * @return
     */
    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

    /**
     * @return
     */
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
