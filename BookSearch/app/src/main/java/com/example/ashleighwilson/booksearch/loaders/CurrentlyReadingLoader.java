package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.data.SimpleXmlConverterFactory;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.OkHttpOAuthConsumer2;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.SigningInterceptor;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.example.ashleighwilson.booksearch.service.response.ReviewsAndShelfResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrentlyReadingLoader extends AsyncTask<Long, Void, Reviews> {

    private static final String TAG = CurrentlyReadingLoader.class.getSimpleName();
    @Inject
    GoodreadsApiRetro2 goodreadsApi;
    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;
    private OnReviewsFetchedListener listener;

    public interface OnReviewsFetchedListener {
        void ReviewsFetched(Reviews shelf);
    }

    public CurrentlyReadingLoader(OnReviewsFetchedListener listener) {
        this.listener = listener;
        Injector.getInstance().inject(this);
    }

    @Override
    protected Reviews doInBackground(Long... params) {

        if (params.length == 0) {
            return null;
        }
        long userId = params[0];

        //GoodreadsApi data = retrofit.create(GoodreadsApi.class);
        Call<ReviewsAndShelfResponse> reviewsCall = goodreadsApi.review_list(userId, 2,
                            "currently-reading");
        try {
            Response<ReviewsAndShelfResponse> response = reviewsCall.execute();
            ReviewsAndShelfResponse reviewsResponse = response.body();
            return reviewsResponse.getReviews();
        } catch (IOException e) {
            Log.i(TAG, "error loading reviews");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Reviews reviews) {
        if (reviews != null) {
            listener.ReviewsFetched(reviews);
        } else {
            listener.ReviewsFetched(null);
        }
    }
}
