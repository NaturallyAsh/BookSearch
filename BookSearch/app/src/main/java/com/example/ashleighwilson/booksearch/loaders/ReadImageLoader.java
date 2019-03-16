package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.GoogleImageResponse;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.service.response.GoogleBooksApi;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class ReadImageLoader extends AsyncTask<Void, Void, List<Item>> {

    private static final String TAG = ReadImageLoader.class.getSimpleName();

    private OnReadImageListener listener;
    private String mTitle;
    @Inject
    GoogleBooksApi googleBooksApi;

    public interface OnReadImageListener {
        void OnReadImageFetched(List<Item> itemList);
    }

    public ReadImageLoader(String title, OnReadImageListener listener) {
        this.listener = listener;
        this.mTitle = title;
        Injector.getInstance().inject(this);
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        if (mTitle == null) {
            return null;
        }
        Call<GoogleImageResponse> data = googleBooksApi.get_images(1, mTitle);
        try {
            Response<GoogleImageResponse> response = data.execute();
            GoogleImageResponse images = response.body();
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
            listener.OnReadImageFetched(googleImages);
        } else {
            listener.OnReadImageFetched(null);
        }
    }
}
