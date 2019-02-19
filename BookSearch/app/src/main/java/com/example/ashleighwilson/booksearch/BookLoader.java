package com.example.ashleighwilson.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.ashleighwilson.booksearch.data.SearchUtils;
import com.example.ashleighwilson.booksearch.models.Book;

import java.util.List;

class BookLoader extends AsyncTaskLoader<List<Book>>
{
    private String mUrl;

    public BookLoader(Context context)
    {
        super(context);
    }

    public BookLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }

    @Override
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
    }
}
