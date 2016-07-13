package com.k1.mvprxandroidsample;

import com.k1.mvprxandroidsample.service.GistService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by K1 on 7/14/16.
 */
@Module
public class ApiModule {

    @Provides
    @CustomScope
    GistService providesGistService(Retrofit retrofit) {
        return retrofit.create(GistService.class);
    }
}
