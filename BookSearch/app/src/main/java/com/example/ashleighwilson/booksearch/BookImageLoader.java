package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.data.SearchUtils;
import com.example.ashleighwilson.booksearch.models.GoogleImage;
import com.example.ashleighwilson.booksearch.models.GoogleImageResponse;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.service.response.GoogleBooksApi;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class BookImageLoader extends AsyncTask<Void, Void, List<Item>> {

    private static final String TAG = BookImageLoader.class.getSimpleName();

    private GoogleImageListener listener;
    private String mTitle;
    @Inject
    GoogleBooksApi googleBooksApi;

    public interface GoogleImageListener {
        void OnGoogleImageLoaded(List<Item> googleImages);
    }

    public BookImageLoader(String title, GoogleImageListener listener) {
        this.listener = listener;
        this.mTitle = title;
        Injector.getInstance().inject(this);
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {

        if (mTitle == null) {
            return null;
        }
        //List<GoogleImage> googleImages = SearchUtils.fetchImageData(mUrl);
        Call<GoogleImageResponse> data = googleBooksApi.get_images(2, mTitle);
        try {
            Response<GoogleImageResponse> response = data.execute();
            GoogleImageResponse images = response.body();
            return images.getItems();
        } catch (IOException e) {
            Log.i(TAG, "error on response");
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Item> googleImages) {
        if (googleImages != null) {
            listener.OnGoogleImageLoaded(googleImages);
        } else {
            listener.OnGoogleImageLoaded(null);
        }
    }
}
