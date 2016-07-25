package com.k1.gister.service;

import com.k1.gister.model.Gist;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by K1 on 7/14/16.
 */
public interface GistService {

    @GET("/users/donnfelker/gists")
    Observable<List<Gist>> getGists();

}
