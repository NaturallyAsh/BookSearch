package com.example.ashleighwilson.booksearch.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.data.SearchUtils;
import com.example.ashleighwilson.booksearch.models.Book;
import com.example.ashleighwilson.booksearch.models.Search;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.example.ashleighwilson.booksearch.service.response.SearchResponse;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class SearchBookLoader extends AsyncTask<String, Void, Search>//AsyncTaskLoader<List<Book>>
{
    //private String mUrl;
    private static final String TAG = SearchBookLoader.class.getSimpleName();
    @Inject
    GoodreadsApiRetro2 goodreadsApi;

    private OnSearchBookListener listener;

    public interface OnSearchBookListener {
        void OnSearchBook(Search search);
    }

    public SearchBookLoader(OnSearchBookListener listener) {
        this.listener = listener;
        Injector.getInstance().inject(this);
    }

    @Override
    protected Search doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String query = params[0];

        Call<SearchResponse> searchCall = goodreadsApi.search_index(BuildConfig.Goodreads_Api_Key,
                query);

        try{
            Response<SearchResponse> response = searchCall.execute();
            SearchResponse searchResponse = response.body();
            return searchResponse.getSearch();
        } catch (IOException e) {
            Log.i(TAG, "error on search response");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Search search) {
        if (search != null) {
            listener.OnSearchBook(search);
        } else {
            Log.i(TAG, "search post exec null");
            listener.OnSearchBook(null);
        }
    }

    /*public SearchBookLoader(Context context)
    {
        super(context);
    }

    public SearchBookLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }*/

    /*@Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground()
    {
        if (mUrl == null)
        {
            return null;
        }

        List<Book> books = SearchUtils.fetchBookData(mUrl);
        return books;
    }*/
}
