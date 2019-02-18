package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.data.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements
        android.app.LoaderManager.LoaderCallbacks<List<Book>>
{
    private static final String TAG = BookActivity.class.getSimpleName();

    private static final int BOOK_LOADER_ID = 1;

    private TextView mEmptyStateView;
    private BookAdapter mAdapter;
    private String QUERY_KEY;
    private String searchURL;
    private String baseApiUrl;
    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.rvView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());
        recyclerView.setAdapter(mAdapter);

        mEmptyStateView = findViewById(R.id.empty_state);

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

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener()
        {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v)
            {
                Book currentBook = mAdapter.getBook(position);
                String bookLink = currentBook.getmInfoLink();
                Uri bookUri = Uri.parse(bookLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(intent);

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

        if (books != null && !books.isEmpty())
        {
            mEmptyStateView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.setData(books);
        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            mEmptyStateView.setVisibility(View.VISIBLE);
            mEmptyStateView.setText(R.string.no_books);
        }

        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader)
    {
        mAdapter.setData(null);
    }
}
