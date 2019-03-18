package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.Series;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.example.ashleighwilson.booksearch.service.response.SeriesResponse;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class SeriesBookLoader extends AsyncTask<Void, Void, Series> {

    private static final String TAG = SeriesBookLoader.class.getSimpleName();

    private String mSeriesId;
    private OnSeriesLoadedListener listener;
    @Inject
    GoodreadsApiRetro2 goodreadsApi;

    public interface OnSeriesLoadedListener {
        void OnSeriesFetched(Series series);
    }

    public SeriesBookLoader(String id, OnSeriesLoadedListener listener) {
        this.listener = listener;
        this.mSeriesId = id;
        Injector.getInstance().inject(this);
    }

    @Override
    protected Series doInBackground(Void... voids) {
        if (mSeriesId == null) {
            Log.i(TAG, "id is null");
            return null;
        }

        Call<SeriesResponse> seriesResponseCall = goodreadsApi.series_show(mSeriesId, BuildConfig.Goodreads_Api_Key);
        try {
            Response<SeriesResponse> response = seriesResponseCall.execute();
            SeriesResponse seriesResponse = response.body();
            return seriesResponse.getSeries();
        } catch (IOException e) {
            Log.i(TAG, "error on series response");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Series series) {
        if (series != null) {
            listener.OnSeriesFetched(series);
        } else {
            listener.OnSeriesFetched(null);
        }
    }
}
