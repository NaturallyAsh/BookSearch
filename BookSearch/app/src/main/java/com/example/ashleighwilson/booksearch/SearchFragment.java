package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import nl.siegmann.epublib.epub.Main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.adapters.SearchBookAdapter;
import com.example.ashleighwilson.booksearch.loaders.SearchBookLoader;
import com.example.ashleighwilson.booksearch.models.BestBook;
import com.example.ashleighwilson.booksearch.models.Search;
import com.example.ashleighwilson.booksearch.models.Work;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchBookLoader.OnSearchBookListener,
        SearchBookAdapter.OnSearchItemClickedListener
{
    private static final String TAG = SearchFragment.class.getSimpleName();
    public static final String SEARCH_ARG_QUERY = "query_key";

    private SearchBookAdapter mAdapter;
    private String QUERY_KEY;
    private String searchURL;
    private ArrayList<BestBook> bestBookArrayList = new ArrayList<>();
    private MainActivity mainActivity;
    @BindView(R.id.empty_state)
    TextView mEmptyStateView;
    @BindView(R.id.rvView)
    RecyclerView recyclerView;

    public SearchFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getArguments();
        if (bundle.containsKey(SEARCH_ARG_QUERY)) {
            QUERY_KEY = bundle.getString(SEARCH_ARG_QUERY);
            searchURL = QUERY_KEY;

        } else {
            Log.i(TAG, "bundle query null");
        }
        //QUERY_KEY = mainActivity.getIntent().getStringExtra("key").trim();
        //Log.i(TAG, " QUERY_KEY passed = " + QUERY_KEY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_book_page, container, false);
        ButterKnife.bind(this, rootView);

        mainActivity.getSupportActionBar();
        mainActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new SearchBookAdapter(getContext(), bestBookArrayList, this);
        recyclerView.setAdapter(mAdapter);


        ConnectivityManager cm = (ConnectivityManager)mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && searchURL!= null)
        {
            searchBooks(searchURL);
        }
        else
        {
            View loadingIndicator = mainActivity.findViewById(R.id.loading_progress);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateView.setText(R.string.no_connection);
        }

        /*ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener()
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
        });*/

        return rootView;
    }

    private void searchBooks(String searchString) {
        new SearchBookLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, searchString);
    }

    @Override
    public void OnSearchBook(Search search) {
        Log.i(TAG, "search results: " + search);
        List<Work> works = search.getResults();
        if (works != null) {
            mEmptyStateView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            List<BestBook> bestBooks = new ArrayList<>();
            for (int i = 0; i < works.size(); i++) {
                bestBooks.add(works.get(i).getBestBook());
            }
            mAdapter.add(bestBooks);
        } else {
            mEmptyStateView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        View loadingIndicator = mainActivity.findViewById(R.id.loading_progress);
        loadingIndicator.setVisibility(View.GONE);

    }

    @Override
    public void OnSearchItemClicked(String id, String title) {
        mainActivity.searchToDetail(id, title);
    }

    public boolean goHome() {
        if (mainActivity != null && mainActivity.getSupportFragmentManager() != null) {
            //mainActivity.switchToList();
            mainActivity.getSupportFragmentManager().popBackStack();
            if (mainActivity.toggle != null) {
                mainActivity.toggle.setDrawerIndicatorEnabled(true);
            }
            mainActivity.onDirection(MainActivity.Direction.PARENT);
        }
        return true;
    }
}
