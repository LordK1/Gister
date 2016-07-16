package com.k1.mvprxandroidsample;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.k1.mvprxandroidsample.model.Gist;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * To Load one single item with {@link Observable} and {@link Subscriber}
 * from API
 * <p/>
 * In this activity I don't have use MVP and  also just used RxJava to handle some new features
 * <p/>
 * <p>
 * Finally, we come to using the injection setup that we put together in the previous sections,
 * in an activity that needs access to {@link SharedPreferences}
 * <p/>
 * As Expected nothing has changed in the @Inject annotation and little has changed in the injection itself.
 * </p>
 * <i>
 * Lazy Injection means, will not actually get injected until the first call to get(). From then
 * on it will remain injected regardless of how many times it gets called after that.
 * </i>
 * <a href="https://blog.gouline.net/dagger-2-even-sharper-less-square-b52101863542#.8y9bbm8g9">Read More</a>
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // injection target with named default
    @Inject
    @Named("default")
    SharedPreferences mDefaultPreferences;

    // Named Injection target with named secret
    @Inject
    @Named("secret")
    SharedPreferences mSecretPreferences;

    // Lazy injection
/*
    @Inject
    Lazy<SharedPreferences> mPreferencesLazy;
*/


    private TextView mTitleTextView;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // To inject {@link MainApplication#getApplicationComponent}
        ((MainApplication) getApplication()).getApplicationComponent().inject(this);
        mDefaultPreferences.edit().putString("status", "Success!!").apply();

        mSecretPreferences.edit().putString("Secret", "Something At Somewhere !!!").apply();

        mTitleTextView = (TextView) findViewById(R.id.main_title_text_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDefaultPreferences
                        .edit()
                        .putString("Gist", mSubscription.toString())
                        .apply();

            }
        });
        /**
         * To Subscribe on Observable item
         * Just for locate the process in the background thread you need to define .subscribeOn(Schedulers.io())
         * And Also to define return result on mainUiThread need to define .observeOn(AndroidSchedulers.mainThread())
         * That means observing process runs i background thread and then callback result in mainUiThread
         * This is specifically called for RxAndroid.
         * Please NOTE : One or the other of (onCompleted/onError) will get called. NOT BOTH.
         * */
        mSubscription = getGistObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gist>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called with: " + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                        Log.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Gist gist) {
                        Log.d(TAG, "onNext() called with: " + "gist = [" + gist + "]");
                        Toast.makeText(MainActivity.this, gist.toString(), Toast.LENGTH_SHORT).show();
                        mTitleTextView.setText(gist.toString()
                                + " mDefaultPreferences : " + mDefaultPreferences.getString("status", "Sexxxy")
                                + " mSecretPreferences : " + mSecretPreferences.getString("Secret", "Sexxxy")
                        );
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Nullable // this states that method can have nullable return value
    private Gist getGist() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/0a6ca6dc1c8aae9cd9f8")
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG, "Code : " + response.code()
                + " Message : " + response.message()
                + " isSuccessful : " + response.isSuccessful()
        );
        if (response.isSuccessful()) {
            Gist gist = new Gson().fromJson(response.body().string(), Gist.class);
            Log.i(TAG, " BODY : " + gist);
            return gist;
        }

        return null;
    }

    /**
     * @return
     */
    public Observable<Gist> getGistObservable() {
        return Observable.defer(new Func0<Observable<Gist>>() {
            @Override
            public Observable<Gist> call() {
                try {
                    return Observable.just(getGist());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetGistsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            // Go get this Gist: https://gist.github.com/donnfelker/db72a05cc03ef523ee74 via the Github API
            Request request = new Request.Builder()
                    .url("https://api.github.com/gists/5e919828c889e5e82173929cb53969be")
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
