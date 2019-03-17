package com.example.ashleighwilson.booksearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashleighwilson.booksearch.adapters.CurrentBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.ReadBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.WantBookAdapter;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.loaders.CurrentlyReadingLoader;
import com.example.ashleighwilson.booksearch.loaders.ToReadBookLoader;
import com.example.ashleighwilson.booksearch.loaders.ReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.WantToReadLoader;
import com.example.ashleighwilson.booksearch.loaders.WantedImageLoader;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements CurrentlyReadingLoader.OnReviewsFetchedListener,
    WantToReadLoader.OnWantedFetchedListener, ToReadBookLoader.OnReadFetchedListener,
    WantedImageLoader.WantedImageListener, ReadImageLoader.OnReadImageListener,
        CurrentBookAdapter.CurrentBookClickListener, WantBookAdapter.OnWantedClickedListener,
        ReadBookAdapter.OnReadClickedListener {

    private static final String TAG = UserFragment.class.getSimpleName();

    @BindView(R.id.currently_reading_TV)
    TextView currentlyReadingTV;
    @BindView(R.id.want_to_read_TV)
    TextView want_to_read_TV;
    @BindView(R.id.read_TV)
    TextView readTV;
    @BindView(R.id.must_login_TV)
    TextView mustBeLoggedInTV;
    @BindView(R.id.current_PB)
    ProgressBar currentPB;
    @BindView(R.id.want_PB)
    ProgressBar wantPB;
    @BindView(R.id.read_PB)
    ProgressBar readPB;
    @BindView(R.id.empty_current_TV)
    TextView emptyCurrentTV;
    @BindView(R.id.empty_to_read_TV)
    TextView emptyToReadTV;
    @BindView(R.id.empty_read_TV)
    TextView emptyReadTV;
    @BindView(R.id.current_RV)
    RecyclerView currentRV;
    @BindView(R.id.to_read_RV)
    RecyclerView want_to_read_RV;
    @BindView(R.id.read_RV)
    RecyclerView readRV;

    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser user;

    private ArrayList<Review> currentArrayList = new ArrayList<>();
    private ArrayList<Review> wantArrayList = new ArrayList<>();
    private ArrayList<Review> readArrayList = new ArrayList<>();
    private CurrentBookAdapter currentBookAdapter;
    private WantBookAdapter wantBookAdapter;
    private ReadBookAdapter readBookAdapter;
    private List<Review> wantedBookList;
    private List<Review> toReadBookList;
    private List<Review> currentBookList;
    public static String REVIEW_ITEM = "review_item";
    private MainActivity mainActivity;
    private String QUERY_KEY;


    public UserFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mainActivity.toggle != null) {
            mainActivity.toggle.setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        setHasOptionsMenu(true);

        Intent searchIntent = mainActivity.getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            QUERY_KEY = searchIntent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "QUERY_KEY = " + QUERY_KEY);
            Intent intent = new Intent(getActivity(), BookActivity.class);
            intent.putExtra("key", QUERY_KEY);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        mainActivity.getSupportActionBar();
        mainActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.drawer.openDrawer(GravityCompat.START);
            }
        });

        Injector.getInstance().inject(this);

        LinearLayoutManager currentManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        LinearLayoutManager wantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        LinearLayoutManager toReadManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);

        currentRV.setLayoutManager(currentManager);
        currentBookAdapter = new CurrentBookAdapter(getContext(), currentArrayList, this);
        currentRV.setAdapter(currentBookAdapter);

        want_to_read_RV.setLayoutManager(wantManager);
        wantBookAdapter = new WantBookAdapter(getContext(), wantArrayList, this);
        want_to_read_RV.setAdapter(wantBookAdapter);

        readRV.setLayoutManager(toReadManager);
        readBookAdapter = new ReadBookAdapter(getContext(), readArrayList, this);
        readRV.setAdapter(readBookAdapter);


        init();

        return rootView;
    }

    private void init() {
        if (user.getId() == -1) {
            mustBeLoggedInTV.setVisibility(View.VISIBLE);
            currentRV.setVisibility(View.GONE);
        } else {
            mustBeLoggedInTV.setVisibility(View.GONE);
            currentRV.setVisibility(View.VISIBLE);

            fetchCurrentBooks();
            fetchWantBooks();
            fetchReadBooks();
        }
    }

    private void fetchCurrentBooks() {
        currentPB.setVisibility(View.VISIBLE);
        new CurrentlyReadingLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchWantBooks() {
        wantPB.setVisibility(View.VISIBLE);
        new WantToReadLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchReadBooks() {
        readPB.setVisibility(View.VISIBLE);
        new ToReadBookLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    @Override
    public void ReviewsFetched(Reviews reviews) {
        if (reviews != null) {
            String noPhoto = "noPhoto";
            currentPB.setVisibility(View.GONE);
            currentlyReadingTV.setVisibility(View.VISIBLE);
            currentBookList = reviews.getReview();

            if (currentBookList == null) {
                emptyCurrentTV.setVisibility(View.VISIBLE);
            } else {
                emptyCurrentTV.setVisibility(View.GONE);
                for (int i = 0; i < currentBookList.size(); i++) {

                    currentBookAdapter.add(currentBookList);
                }
            }
        }
    }

    @Override
    public void WantedFetched(Reviews reviews) {
        if (reviews != null) {
            wantPB.setVisibility(View.GONE);
            want_to_read_TV.setVisibility(View.VISIBLE);
            wantedBookList = reviews.getReview();

            if (wantedBookList == null) {
                emptyToReadTV.setVisibility(View.VISIBLE);
            } else {
                emptyToReadTV.setVisibility(View.GONE);
                for (int i = 0; i < wantedBookList.size(); i++) {
                    wantBookAdapter.add(wantedBookList);
                    Review review = wantedBookList.get(i);
                    String noPhoto = "noPhoto";
                    if (review.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                        String name = review.getBook().getTitle();
                        new WantedImageLoader(name, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
            }
        }
    }

    @Override
    public void ReadFetched(Reviews reviews) {
        if (reviews != null) {
            readPB.setVisibility(View.GONE);
            readTV.setVisibility(View.VISIBLE);
            toReadBookList = reviews.getReview();

            if (toReadBookList == null) {
                emptyReadTV.setVisibility(View.VISIBLE);
            } else {
                emptyReadTV.setVisibility(View.GONE);
                for (int i = 0; i < toReadBookList.size(); i++) {
                    readBookAdapter.add(toReadBookList);
                    Review review = toReadBookList.get(i);
                    String noPhoto = "noPhoto";
                    if (review.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                        String name = review.getBook().getTitle();
                        //String test = "Soulsmith (Cradle, #2)";
                        //Log.i(TAG, "read name: " + name);
                        new ReadImageLoader(name, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
            }
        }
    }

    @Override
    public void OnWantedImageLoaded(List<Item> googleImages) {
        if (googleImages != null) {
            for (int j = 0; j < googleImages.size(); j++) {
                wantBookAdapter.newImage(googleImages.get(j));
            }
        }
    }

    @Override
    public void OnReadImageFetched(List<Item> itemList) {
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                readBookAdapter.newImage(itemList.get(i));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) mainActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(mainActivity.getComponentName()));
        searchView.setIconifiedByDefault(false);

        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.anim.
        switch (item.getItemId()) {
            case R.id.menu_qr:
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.initiateScan();
                Log.i(TAG, "search clicked");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, intent);
        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            if (scanContent != null && scanFormat != null && scanFormat.equalsIgnoreCase("EAN_13"))
            {
                String bookSearchString = "isbn:" + scanContent;
                QUERY_KEY = bookSearchString;

                Intent intent1 = new Intent(getContext(), BookActivity.class);
                intent1.putExtra("key", QUERY_KEY);
                startActivity(intent1);
            }
            else
            {
                Toast.makeText(getContext(), "Not a valid scan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(),"No book scan data" +
                    "received", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnCurrentBookClicked(Review review, int position) {
        mainActivity.switchToDetail(review);
    }

    @Override
    public void OnWantedClicke(Review review, int position) {
        mainActivity.switchToDetail(review);
    }

    @Override
    public void OnReadBookClicked(Review review, int position) {
        mainActivity.switchToDetail(review);
    }
}
