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

public class AudiobookImageLoader extends AsyncTask<Void, Void, List<Item>> {

    private static final String TAG = AudiobookImageLoader.class.getSimpleName();

    private OnAudiobookImageLoadedListener listener;
    private String mTitle;
    @Inject
    GoogleBooksApi googleBooksApi;

    public interface OnAudiobookImageLoadedListener {
        void OnAudiobookImageLoaded(List<Item> itemList);
    }

    public AudiobookImageLoader(String title, OnAudiobookImageLoadedListener listener) {
        this.mTitle = title;
        this.listener = listener;
        Injector.getInstance().inject(this);
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        if (mTitle == null) {
            Log.i(TAG, "title is null");
            return null;
        }
        Call<GoogleImageResponse> data = googleBooksApi.get_images(mTitle, BuildConfig.Googlebooks_Api_Key);
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
    public void onPostExecute(List<Item> itemList) {
        if (listener != null) {
            listener.OnAudiobookImageLoaded(itemList);
        } else {
            Log.i(TAG, "listener is null");
        }
    }
}
