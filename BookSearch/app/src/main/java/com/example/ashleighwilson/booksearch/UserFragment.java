package com.example.ashleighwilson.booksearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.adapters.CurrentBookAdapter;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.models.Shelves;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements ReviewsLoader.OnReviewsFetchedListener {

    private static final String TAG = UserFragment.class.getSimpleName();
    private static final int REVIEWS_LOADER_ID = 2;

    @BindView(R.id.must_login_TV)
    TextView mustBeLoggedInTV;
    @BindView(R.id.current_RV)
    RecyclerView currentRV;
    @BindView(R.id.to_read_RV)
    RecyclerView to_read_RV;
    @BindView(R.id.read_RV)
    RecyclerView readRV;

    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;
    @Inject
    GoodreadsApi goodreadsApi;

    private ArrayList<Review> reviewsArrayList = new ArrayList<>();
    private CurrentBookAdapter currentBookAdapter;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Injector.getInstance().inject(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        currentRV.setLayoutManager(manager);
        currentBookAdapter = new CurrentBookAdapter(getContext(), reviewsArrayList);
        currentRV.setAdapter(currentBookAdapter);


        init();

        return rootView;
    }

    private void init() {
        if (user.getId() == -1) {
            mustBeLoggedInTV.setVisibility(View.VISIBLE);
            currentRV.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "user id: " + user.getId());
            mustBeLoggedInTV.setVisibility(View.GONE);
            currentRV.setVisibility(View.VISIBLE);
            fetchReviews();
        }
    }

    private void fetchReviews() {
        Log.i(TAG, "reviews being fetched");
        new ReviewsLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    @Override
    public void ReviewsFetched(Reviews reviews) {
        List<Review> reviewList = reviews.getReview();
        Log.i(TAG, "reviews total: " + reviewList.size());

        for (int i = 0; i < reviewList.size(); i++) {
            //Log.i(TAG, "list called: " + reviewList.get(i).getId());
            Log.i(TAG, "books: " + reviewList.get(i).getBook().getTitle());
            currentBookAdapter.add(reviewList);

        }

    }
}
