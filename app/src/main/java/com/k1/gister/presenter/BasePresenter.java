package com.k1.gister.presenter;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * abstract implemented {@link Presenter} as Presenter layer in MVP architecture
 * Created by K1 on 7/14/16.
 */
public abstract class BasePresenter implements Presenter {

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        configureSubscription();
    }

    /**
     * @return
     */
    private CompositeSubscription configureSubscription() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new
                    CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    @Override
    public void onDestroy() {
        unSubscribeAll();
    }

    /**
     * To call {@link CompositeSubscription#unsubscribe()}
     * for #mCompositeSubscription
     */
    protected void unSubscribeAll() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
//            mCompositeSubscription = null;
        }
    }

    /**
     * To subscribe {@link Observable} of generic types into Presenter
     *
     * @param observable
     * @param observer
     * @param <G>
     */
    protected <G> void subscribe(Observable<G> observable, Observer<G> observer) {
        Subscription subscriptions = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(observer);
        configureSubscription().add(subscriptions);
    }


}
