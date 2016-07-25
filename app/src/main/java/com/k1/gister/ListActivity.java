package com.k1.gister;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.k1.gister.model.Gist;
import com.k1.gister.presenter.GistPresenter;
import com.k1.gister.service.GistService;
import com.k1.gister.service.GistViewInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

/**
 * To load some {@link Gist} as list items
 * in {@link android.support.v7.widget.RecyclerView}
 * as implemented {@link GistViewInterface}
 * you can call it the View layer in MVP architecture
 *
 * @author K1
 */
public class ListActivity extends AppCompatActivity implements GistViewInterface {

    private static final String TAG = ListActivity.class.getSimpleName();
    @Inject
    GistService mGistService;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private GistPresenter mPresenter;
    private GistAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enthusiastic);

        ButterKnife.setDebug(true);
        final Unbinder bind = ButterKnife.bind(ListActivity.this);
        Log.i(TAG, "onCreate: mRecyclerView : "+mRecyclerView);
        setSupportActionBar(mToolbar);

        configViews();
        resolveDependency();
        //
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mPresenter = new GistPresenter(this);
        mPresenter.onCreate();
    }

    /**
     * To resolve or inject some Dependencies into Activity
     */
    private void resolveDependency() {
        ((MainApplication) getApplication())
                .getApiComponent()
                .inject(ListActivity.this);
    }

    /**
     * Config for {@link #mRecyclerView}
     */
    private void configViews() {
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GistAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        mPresenter.fetchGists();
        mProgressDialog = new ProgressDialog(ListActivity.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Downloading ...");
        mProgressDialog.setMessage("Please wait ... ");
        mProgressDialog.show();
    }

    @Override
    public void onCompleted() {
        Toast.makeText(getApplicationContext(), "Downloading ", Toast.LENGTH_SHORT).show();
        mProgressDialog.dismiss();
    }

    @Override
    public void onError(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(ListActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGists(List<Gist> gists) {
        mAdapter.addGists(gists);
    }

    @Override
    public Observable<List<Gist>> getGists() {
        return mGistService.getGists();
    }

    /**
     *
     */
    private class GistAdapter extends RecyclerView.Adapter<GistViewHolder> {

        private final ArrayList<Gist> mOldList;
        private final ArrayList<Gist> mGists;

        public GistAdapter() {
            mGists = new ArrayList<>();
            mOldList = new ArrayList<>();

        }

        @Override
        public GistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gist_item, parent, false));
        }

        @Override
        public void onBindViewHolder(GistViewHolder holder, int position) {
            // TODO: 7/14/16 make it complete soon
            holder.onBind(getItem(position));
        }

        private Gist getItem(int position) {
            return this.mGists.get(position);
        }

        @Override
        public int getItemCount() {
            return this.mGists.size();
        }

        public void addGists(List<Gist> gists) {
            mGists.addAll(gists);
            notifyDataSetChanged();
        }
    }


    private class GistViewHolder extends RecyclerView.ViewHolder {

        private final TextView mIdTextView;
        private final ImageView mImageView;
        private final TextView mIsPublicTextView;
        private final TextView mUrlTextView;

        public GistViewHolder(View view) {
            super(view);
            mIdTextView = (TextView) view.findViewById(R.id.gist_id);
            mUrlTextView = (TextView) view.findViewById(R.id.gist_url);
            mIsPublicTextView = (TextView) view.findViewById(R.id.gist_is_public);
            mImageView = (ImageView) view.findViewById(R.id.gist_image);


        }

        public void onBind(Gist item) {
            mIdTextView.setText(item.toString());
        }
    }
}
