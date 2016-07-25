package com.k1.gister.service;

import com.k1.gister.model.Gist;

import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by K1 on 7/14/16.
 */
public interface GistViewInterface {

    /**
     * bind {@link Observer#onCompleted()}
     */
    void onCompleted();

    /**
     * bind {@link Observer#onError(Throwable)}
     *
     * @param message
     */
    void onError(String message);

    /**
     * bind {@link Observer#onNext(Object)}
     *
     * @param gists
     */
    void onGists(List<Gist> gists);

    Observable<List<Gist>> getGists();
}
