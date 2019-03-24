package com.example.ashleighwilson.booksearch.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.BookDetails;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.example.ashleighwilson.booksearch.service.response.BookShowResponse;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;

import java.io.IOException;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Response;

public class BookDetailsLoader extends AsyncTask<Void, Void, UserBook> {

    private static final String TAG = BookDetailsLoader.class.getSimpleName();

    @Inject
    GoodreadsApiRetro2 goodreadsApi;

    private OnBookDetailsListener listener;
    private String mId;

    public interface OnBookDetailsListener {
        void OnBookDetailsFetched(UserBook bookDetails);
    }

    public BookDetailsLoader(String bookId, OnBookDetailsListener listener) {
        this.mId = bookId;
        this.listener = listener;
        Injector.getInstance().inject(this);
    }

    @Override
    protected UserBook doInBackground(Void... voids) {

        if (mId == null) {
            return null;
        }
        Call<BookShowResponse> bookCall = goodreadsApi.book_show(mId, BuildConfig.Goodreads_Api_Key);
        try {
            Response<BookShowResponse> response = bookCall.execute();
            BookShowResponse bookDetails = response.body();
            return bookDetails.getBook();
        } catch (IOException e) {
            Log.i(TAG, "error on response");
        }
        return null;
    }

    @Override
    protected void onPostExecute(UserBook bookDetails) {
        if (bookDetails != null) {
            listener.OnBookDetailsFetched(bookDetails);
        } else {
            listener.OnBookDetailsFetched(null);
        }
    }
}
