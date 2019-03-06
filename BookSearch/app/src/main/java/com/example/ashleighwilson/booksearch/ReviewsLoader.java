package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.ReviewListResponse;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import retrofit.RetrofitError;

public class ReviewsLoader extends AsyncTaskLoader<Reviews> {

    private static final String TAG = ReviewsLoader.class.getSimpleName();
    @Inject
    GoodreadsApi goodreadsApi;

    @Inject
    AuthUser user;

    Reviews reviews = null;

    public ReviewsLoader(@NonNull Context context) {
        super(context);

        Injector.getInstance().inject(this);
    }

    @Nullable
    @Override
    public Reviews loadInBackground() {
        Reviews reviews = new Reviews();
        try {
            ReviewListResponse response = goodreadsApi.review_list(
                    user.getId());
            System.out.println(response);
            reviews = response.getReviews();
            Log.i(TAG, "response: " + reviews);
        } catch (RetrofitError error) {
            Log.i(TAG, "error getting reviews");
            //error.printStackTrace();
        }
        return reviews;
    }

    @Override
    public void deliverResult(Reviews reviews) {
        this.reviews = reviews;
        if (isStarted()) {
            super.deliverResult(reviews);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
