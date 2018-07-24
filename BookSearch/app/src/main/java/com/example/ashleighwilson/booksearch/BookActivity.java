package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements
        android.app.LoaderManager.LoaderCallbacks<List<Book>>
{
    private static final String TAG = BookActivity.class.getSimpleName();

    private static final int BOOK_LOADER_ID = 1;

    private TextView mEmptyStateView;
    private BookAdapter mAdapter;
    private ListView bookListView;
    private String QUERY_KEY;
    private String searchURL;
    private String baseApiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        QUERY_KEY = getIntent().getStringExtra("key").trim();
        Log.i(TAG, " QUERY_KEY passed = " + QUERY_KEY);

        baseApiUrl = getString(R.string.GOOGLE_API_SEARCH);
        searchURL = String.format(baseApiUrl + QUERY_KEY);
        Log.i(TAG, "Search URL = " + searchURL);

        bookListView = findViewById(R.id.list_view);


        mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        mEmptyStateView = findViewById(R.id.empty_state);
        bookListView.setEmptyView(mEmptyStateView);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
        else
        {
            View loadingIndicator = findViewById(R.id.loading_progress);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateView.setText(R.string.no_connection);
        }

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                long l)
            {
                Book currentBook = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(String.valueOf(currentBook.getmInfoLink()));
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

    }

    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int i, Bundle bundle)
    {
       return new BookLoader(this, searchURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books)
    {
        View loadingIndicator = findViewById(R.id.loading_progress);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateView.setText(R.string.no_books);
        mAdapter.clear();

        if (books != null && !books.isEmpty())
        {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader)
    {
        mAdapter.clear();
    }
}
