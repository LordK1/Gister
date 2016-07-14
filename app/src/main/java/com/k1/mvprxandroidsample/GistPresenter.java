package com.k1.mvprxandroidsample;

import com.k1.mvprxandroidsample.model.Gist;
import com.k1.mvprxandroidsample.service.GistViewInterface;

import java.util.List;

import rx.Observer;

/**
 * To present {@link com.k1.mvprxandroidsample.model.Gist} as extends {@link BasePresenter}
 * Created by K1 on 7/14/16.
 */
public class GistPresenter extends BasePresenter implements Observer<List<Gist>> {

    private GistViewInterface mViewInterface;

    public GistPresenter(GistViewInterface mViewInterface) {
        this.mViewInterface = mViewInterface;
    }

    @Override
    public void onCompleted() {
        mViewInterface.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mViewInterface.onError(e.getMessage());
    }

    @Override
    public void onNext(List<Gist> gists) {
        mViewInterface.onGists(gists);
    }


    public void fetchGists() {
        unSubscribeAll();
        subscribe(mViewInterface.getGists(), GistPresenter.this);
    }
}
