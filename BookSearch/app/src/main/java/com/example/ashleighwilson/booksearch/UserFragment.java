package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.models.UserShelf;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.ReviewListResponse;
import com.example.ashleighwilson.booksearch.service.response.UserShowResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.RetrofitError;

public class UserFragment extends Fragment implements LoaderManager.LoaderCallbacks<Reviews> {

    private static final String TAG = UserFragment.class.getSimpleName();
    private static final int REVIEWS_LOADER_ID = 2;

    @BindView(R.id.must_login_TV)
    TextView mustBeLoggedInTV;
    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;
    @Inject
    GoodreadsApi goodreadsApi;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Injector.getInstance().inject(this);

        init();

        return rootView;
    }

    private void init() {
        if (user.getId() == -1) {
            Log.i(TAG, "user id: " + user.getId());
            mustBeLoggedInTV.setVisibility(View.VISIBLE);
        } else {
            Log.i(TAG, "user id: " + user.getId());
            mustBeLoggedInTV.setVisibility(View.GONE);
            getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, this).forceLoad();
        }
    }

    @NonNull
    @Override
    public Loader<Reviews> onCreateLoader(int id, @Nullable Bundle args) {
        return new ReviewsLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Reviews> loader, Reviews data) {
        Log.i(TAG, "data: " + data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Reviews> loader) {

    }
}
