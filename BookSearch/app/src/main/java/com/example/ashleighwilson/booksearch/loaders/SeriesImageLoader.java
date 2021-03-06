package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.GoogleImageResponse;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.service.response.GoogleBooksApi;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class SeriesImageLoader extends AsyncTask<Void, Void, List<Item>> {

    private static final String TAG = ReadImageLoader.class.getSimpleName();

    private OnSeriesImageListener listener;
    private String mId;
    @Inject
    GoogleBooksApi googleBooksApi;

    public interface OnSeriesImageListener {
        void OnSeriesImageFetched(List<Item> itemList);
    }

    public SeriesImageLoader(String id, OnSeriesImageListener listener) {
        this.listener = listener;
        this.mId = id;
        Injector.getInstance().inject(this);
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        if (mId == null) {
            Log.i(TAG, "title is null");
            return null;
        }
        //Log.i(TAG, "title: " + mTitle);
        Call<GoogleImageResponse> data = googleBooksApi.get_series_images(mId, BuildConfig.Googlebooks_Api_Key);
        try {
            Response<GoogleImageResponse> response = data.execute();
            GoogleImageResponse images = response.body();
            //Log.i(TAG, "images: " + images);
            return images != null ? images.getItems() : null;
        } catch (IOException e) {
            Log.i(TAG, "error on response");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Item> googleImages) {
        if (googleImages != null) {
            listener.OnSeriesImageFetched(googleImages);
        } else {
            listener.OnSeriesImageFetched(null);
        }
    }
}
