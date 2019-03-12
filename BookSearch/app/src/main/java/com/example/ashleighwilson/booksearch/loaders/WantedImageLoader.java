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

public class WantedImageLoader extends AsyncTask<Void, Void, List<Item>> {

    private static final String TAG = WantedImageLoader.class.getSimpleName();

    private WantedImageListener listener;
    private String mTitle;
    @Inject
    GoogleBooksApi googleBooksApi;

    public interface WantedImageListener {
        void OnWantedImageLoaded(List<Item> googleImages);
    }

    public WantedImageLoader(String title, WantedImageListener listener) {
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
            return images != null ? images.getItems() : null;
        } catch (IOException e) {
            Log.i(TAG, "error on response");
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Item> googleImages) {
        if (googleImages != null) {
            listener.OnWantedImageLoaded(googleImages);
        } else {
            listener.OnWantedImageLoaded(null);
        }
    }
}
