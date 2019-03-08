package com.example.ashleighwilson.booksearch;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.data.SimpleXmlConverterFactory;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.OkHttpOAuthConsumer2;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.SigningInterceptor;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.ReviewsAndShelfResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WantToReadLoader extends AsyncTask<Long, Void, Reviews> {

    private static final String TAG = CurrentlyReadingLoader.class.getSimpleName();
    @Inject
    GoodreadsApi goodreadsApi;
    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;
    private OnWantedFetchedListener listener;

    public interface OnWantedFetchedListener {
        void WantedFetched(Reviews reviews);
    }

    public WantToReadLoader(OnWantedFetchedListener listener) {
        this.listener = listener;
        Injector.getInstance().inject(this);
    }

    @Override
    protected Reviews doInBackground(Long... params) {

        if (params.length == 0) {
            return null;
        }
        long userId = params[0];

        OkHttpOAuthConsumer2 oAuthConsumer = new OkHttpOAuthConsumer2(
                BuildConfig.Goodreads_Api_Key,
                BuildConfig.Goodreads_Secret
        );

        oAuthConsumer.setTokenWithSecret(
                preferenceUser.getToken(),
                preferenceUser.getSecret()
        );

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(oAuthConsumer))
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.Goodreads_Base_Url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        GoodreadsApi data = retrofit.create(GoodreadsApi.class);
        Call<ReviewsAndShelfResponse> reviewsCall = data.review_list(userId, 2,
                "to-read");
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
            listener.WantedFetched(reviews);
        } else {
            listener.WantedFetched(null);
        }
    }
}
