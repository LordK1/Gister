package com.k1.gister.dependency;

import com.k1.gister.service.GistService;

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
