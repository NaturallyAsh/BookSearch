package com.example.ashleighwilson.booksearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.adapters.CurrentBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.ReadBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.WantBookAdapter;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements CurrentlyReadingLoader.OnReviewsFetchedListener,
    WantToReadLoader.OnWantedFetchedListener, ReadBookLoader.OnReadFetchedListener {

    private static final String TAG = UserFragment.class.getSimpleName();

    @BindView(R.id.currently_reading_TV)
    TextView currentlyReadingTV;
    @BindView(R.id.want_to_read_TV)
    TextView want_to_read_TV;
    @BindView(R.id.read_TV)
    TextView readTV;
    @BindView(R.id.must_login_TV)
    TextView mustBeLoggedInTV;
    @BindView(R.id.current_RV)
    RecyclerView currentRV;
    @BindView(R.id.to_read_RV)
    RecyclerView want_to_read_RV;
    @BindView(R.id.read_RV)
    RecyclerView readRV;

    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;
    @Inject
    GoodreadsApi goodreadsApi;

    private ArrayList<Review> currentArrayList = new ArrayList<>();
    private ArrayList<Review> wantArrayList = new ArrayList<>();
    private ArrayList<Review> readArrayList = new ArrayList<>();
    private CurrentBookAdapter currentBookAdapter;
    private WantBookAdapter wantBookAdapter;
    private ReadBookAdapter readBookAdapter;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Injector.getInstance().inject(this);

        LinearLayoutManager currentManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        LinearLayoutManager wantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        LinearLayoutManager toReadManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);

        currentRV.setLayoutManager(currentManager);
        currentBookAdapter = new CurrentBookAdapter(getContext(), currentArrayList);
        currentRV.setAdapter(currentBookAdapter);

        want_to_read_RV.setLayoutManager(wantManager);
        wantBookAdapter = new WantBookAdapter(getContext(), wantArrayList);
        want_to_read_RV.setAdapter(wantBookAdapter);

        readRV.setLayoutManager(toReadManager);
        readBookAdapter = new ReadBookAdapter(getContext(), readArrayList);
        readRV.setAdapter(readBookAdapter);


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
            fetchCurrentBooks();
            fetchWantBooks();
            fetchReadBooks();
        }
    }

    private void fetchCurrentBooks() {
        Log.i(TAG, "reviews being fetched");
        new CurrentlyReadingLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchWantBooks() {
        new WantToReadLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchReadBooks() {
        new ReadBookLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    @Override
    public void ReviewsFetched(Reviews reviews) {
        currentlyReadingTV.setVisibility(View.VISIBLE);
        List<Review> reviewList = reviews.getReview();

        for (int i = 0; i < reviewList.size(); i++) {
            currentBookAdapter.add(reviewList);
        }

    }

    @Override
    public void WantedFetched(Reviews reviews) {
        want_to_read_TV.setVisibility(View.VISIBLE);
        List<Review> reviewList = reviews.getReview();

        for (int i = 0; i < reviewList.size(); i++) {
            wantBookAdapter.add(reviewList);
        }
    }

    @Override
    public void ReadFetched(Reviews reviews) {
        readTV.setVisibility(View.VISIBLE);
        List<Review> reviewList = reviews.getReview();

        for (int i = 0; i < reviewList.size(); i++) {
            readBookAdapter.add(reviewList);
        }
    }
}
