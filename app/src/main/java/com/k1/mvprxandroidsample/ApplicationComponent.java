package com.k1.mvprxandroidsample;

import com.k1.mvprxandroidsample.dependency.CustomScope;

import dagger.Component;

/**
 * IS USED TO ASSOCIATE MODULES WITH INJECTION TARGETS.
 * To {@link javax.inject.Inject} {@link ApplicationModule} into {@link MainApplication} and {@link MainActivity}
 * and also {@link MainActivityFragment}
 * <i>
 *     As a result, you still need to define all your injection targets, except now forgetting to do
 *     so results in a simple "Cannot find Method" compile error, rather than a cryptic runtime one.
 * </i>
 * Created by K1 on 7/16/16.
 */
@CustomScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    /**
     * To inject {@link ApplicationModule} into {@link MainApplication}
     *
     * @param application
     */
    void inject(MainApplication application);


    /**
     * to inject {@link ApplicationModule} into {@link MainActivity}
     *
     * @param activity
     */
    void inject(MainActivity activity);


    /**
     * To inject {@link ApplicationModule} into {@link MainActivityFragment}
     *
     * @param fragment
     */
    void inject(MainActivityFragment fragment);
}
