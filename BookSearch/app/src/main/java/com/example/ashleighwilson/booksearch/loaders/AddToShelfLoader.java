package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.service.response.BookShowResponse;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.example.ashleighwilson.booksearch.service.response.Request;
import com.example.ashleighwilson.booksearch.service.response.ReviewsAndShelfResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToShelfLoader extends AsyncTask<Void, Void, Void> {

    private static final String TAG = AddToShelfLoader.class.getSimpleName();
    private String mShelfName;
    private String mId;
    @Inject
    GoodreadsApiRetro2 goodreadsApi;

    public AddToShelfLoader(String shelfName, String id) {
        this.mShelfName = shelfName;
        this.mId = id;
        Injector.getInstance().inject(this);
    }


    @Override
    protected Void doInBackground(Void... voids) {

        if (mId == null || mShelfName == null) {
            return null;
        }

        //Call<ReviewsAndShelfResponse> stringCall = goodreadsApi.add_to_shelf(mShelfName, mId,
          //      BuildConfig.Goodreads_Api_Key, "xml");
        Call<Request> call =  goodreadsApi.add_to_shelf(mShelfName, mId,
                BuildConfig.Goodreads_Api_Key, "xml");
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                Log.i(TAG, "success");
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Log.i(TAG, "failure");
            }
        });

        return null;
    }
}
