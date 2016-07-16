package com.k1.mvprxandroidsample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * This is simple sample {@link Module} class to {@link javax.inject.Provider}
 * methods for any injectable dependencies
 * <p>
 * In Other Words : To {@link javax.inject.Inject} {@link SharedPreferences} int {@link MainApplication}
 * </p>
 * <b>
 * Similarly, You can provide anything else :
 * Context
 * System Service (e.g. LocationManager)
 * Database Manager (e.g. Realm)
 * Message Passing (e.g. EventBus)
 * Analytics Tracker (e.g. Google Analytics)
 * <p/>
 * </b>
 * <i>
 * The eagle-eyed readers would have noticed the absence of the injects={} parameter in @Module
 * annotation; That is because Dagger 2 no longer expects it. Instead, the concept of {@link dagger.Component}
 * is used to associate with injection targets !!!
 * </i>
 * <a href="https://blog.gouline.net/dagger-2-even-sharper-less-square-b52101863542#.axqtn7xd5">Read More</a>
 * <i>
 * Suppose you have multiple objects of the name type, e.g. two different {@link SharedPreferences}
 * instances (Potential, pointing to different files). What can you do then ?
 * </i>
 * <p/>
 * Created by K1 on 7/16/16.
 */
@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    /**
     * To make named injection for accessible SharedPreferences
     *
     * @return
     */
    @Provides
    @Named("default")
    SharedPreferences provideDefaultPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    /**
     * To make name injection for accessible SharedPreferences
     *
     * @return
     */
    @Provides
    @Named("secret")
    SharedPreferences provideSecretPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

}
