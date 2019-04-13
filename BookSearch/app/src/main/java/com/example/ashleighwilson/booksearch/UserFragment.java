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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    @BindView(R.id.user_swipe_to_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.multi_layout)
    MultiRecyclerLayout multiRecyclerLayout;
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
    private ArrayList<Review> reviewList1 = new ArrayList<>();
    private List<Item> newItem1 = new ArrayList<>();
    private ArrayList<Review> reviewList2 = new ArrayList<>();
    private List<Item> newItem2 = new ArrayList<>();
    private ArrayList<Review> reviewList3 = new ArrayList<>();
    private List<Item> newItem3 = new ArrayList<>();


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
            mainActivity.switchToSearch(QUERY_KEY);
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });

        init();

        /*multiRecyclerLayout.setCustomizer(new MaterialLeanBack.Customizer() {
            @Override
            public void customizeTitle(TextView textView) {
                textView.setTypeface(null, Typeface.BOLD);
            }
        });*/

        multiRecyclerLayout.setAdapter1(new MultiRecyclerLayout.MultiAdapter<CurrentViewHolder>() {
            @Override
            public int getLineCount() {
                return 1;
            }

            @Override
            public int getCellsCount(int row) {
                return reviewList1.size();
            }

            @Override
            public String getTitleForRow(int row) {
                return "Currently Reading";
            }

            @Override
            public CurrentViewHolder onCreateViewHolder(ViewGroup viewGroup, int row) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_book_item,
                        viewGroup, false);
                return new CurrentViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CurrentViewHolder holder, int position) {
                Review currentReviews = reviewList1.get(position);

                String noPhoto = "noPhoto";
                if (currentReviews.getBook().getImageUrl() != null ) {
                    Glide.with(getContext())
                            .load(currentReviews.getBook().getImageUrl())
                            .into(holder.bookIV);
                }
                if (newItem1 != null) {
                    Item item;

                    for (int i = 0; i < newItem1.size(); i++) {
                        item = newItem1.get(i);
                        String name = item.getVolumeInfo().getTitle();
                        if (currentReviews.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                                currentReviews.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                            String identifier = "";

                            for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                                identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                            }
                            currentReviews.getBook().setImageUrl(currentReviews.getBook().getAltBookCover(identifier));
                            Glide.with(getContext())
                                    .load(currentReviews.getBook().getImageUrl())
                                    .into(holder.bookIV);
                            if (item.getVolumeInfo().getImageLinks() != null) {
                                currentReviews.getBook().setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                                Glide.with(getContext())
                                        .load(currentReviews.getBook().getImageUrl())
                                        .into(holder.bookIV);
                            }
                        }
                    }
                }

                holder.titleTV.setText(currentReviews.getBook().getTitle());
                holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().get(0).getName());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //listener.OnWantedClicke(currentReviews, holder.getAdapterPosition());
                    }
                });
            }
        });

        multiRecyclerLayout.setOnItemClickListener1(new MultiRecyclerLayout.OnItemClickListener() {
            @Override
            public void onTitleClicked(int row, String text) {

            }

            @Override
            public void onItemClicked(int row, int column) {

            }
        });

        multiRecyclerLayout.setAdapter2(new MultiRecyclerLayout.MultiAdapter<WantViewHolder>() {
            @Override
            public int getLineCount() {
                return 1;
            }

            @Override
            public int getCellsCount(int row) {
                return reviewList2.size();
            }

            @Override
            public String getTitleForRow(int row) {
                return "Want To Read";
            }

            @Override
            public WantViewHolder onCreateViewHolder(ViewGroup viewGroup, int row) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.want_book_item,
                        viewGroup, false);
                return new WantViewHolder(view);
            }

            @Override
            public void onBindViewHolder(WantViewHolder holder, int position) {
                Review currentReviews = reviewList2.get(position);

                String noPhoto = "noPhoto";
                if (currentReviews.getBook().getImageUrl() != null ) {
                    Glide.with(getContext())
                            .load(currentReviews.getBook().getImageUrl())
                            .into(holder.wantIV);
                }
                if (newItem2 != null) {
                    Item item;

                    for (int i = 0; i < newItem2.size(); i++) {
                        item = newItem2.get(i);
                        String name = item.getVolumeInfo().getTitle();
                        if (currentReviews.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                                currentReviews.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                            String identifier = "";

                            for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                                identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                            }
                            currentReviews.getBook().setImageUrl(currentReviews.getBook().getAltBookCover(identifier));
                            Glide.with(getContext())
                                    .load(currentReviews.getBook().getImageUrl())
                                    .into(holder.wantIV);
                            if (item.getVolumeInfo().getImageLinks() != null) {
                                currentReviews.getBook().setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                                Glide.with(getContext())
                                        .load(currentReviews.getBook().getImageUrl())
                                        .into(holder.wantIV);
                            }
                        }
                    }
                }

                holder.titleTV.setText(currentReviews.getBook().getTitle());
                holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().get(0).getName());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //listener.OnWantedClicke(currentReviews, holder.getAdapterPosition());
                    }
                });
            }
        });

        multiRecyclerLayout.setOnItemClickListener2(new MultiRecyclerLayout.OnItemClickListener() {
            @Override
            public void onTitleClicked(int row, String text) {

            }

            @Override
            public void onItemClicked(int row, int column) {

            }
        });

        multiRecyclerLayout.setAdapter3(new MultiRecyclerLayout.MultiAdapter<ReadViewHolder>() {
            @Override
            public int getLineCount() {
                return 1;
            }

            @Override
            public int getCellsCount(int row) {
                return reviewList3.size();
            }

            @Override
            public String getTitleForRow(int row) {
                return "Read";
            }

            @Override
            public ReadViewHolder onCreateViewHolder(ViewGroup viewGroup, int row) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.read_book_item,
                        viewGroup, false);
                return new ReadViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ReadViewHolder holder, int position) {
                Review currentReviews = reviewList3.get(position);

                String noPhoto = "noPhoto";
                if (currentReviews.getBook().getImageUrl() != null ) {
                    Glide.with(getContext())
                            .load(currentReviews.getBook().getImageUrl())
                            .into(holder.readImage);
                }
                if (newItem3 != null) {
                    Item item;

                    for (int i = 0; i < newItem3.size(); i++) {
                        item = newItem3.get(i);
                        String name = item.getVolumeInfo().getTitle();
                        if (currentReviews.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                                currentReviews.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                            String identifier = "";

                            for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                                identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                            }
                            currentReviews.getBook().setImageUrl(currentReviews.getBook().getAltBookCover(identifier));
                            Glide.with(getContext())
                                    .load(currentReviews.getBook().getImageUrl())
                                    .into(holder.readImage);
                            if (item.getVolumeInfo().getImageLinks() != null) {
                                currentReviews.getBook().setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                                Glide.with(getContext())
                                        .load(currentReviews.getBook().getImageUrl())
                                        .into(holder.readImage);
                            }
                        }
                    }
                }

                holder.titleTV.setText(currentReviews.getBook().getTitle());
                holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().get(0).getName());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //listener.OnWantedClicke(currentReviews, holder.getAdapterPosition());
                    }
                });
            }
        });

        multiRecyclerLayout.setOnItemClickListener3(new MultiRecyclerLayout.OnItemClickListener() {
            @Override
            public void onTitleClicked(int row, String text) {

            }

            @Override
            public void onItemClicked(int row, int column) {

            }
        });

        return rootView;
    }

    public class CurrentViewHolder extends MultiRecyclerLayout.MultiViewHolder {

        View view;
        @BindView(R.id.current_book_image)
        ImageView bookIV;
        @BindView(R.id.current_book_title)
        TextView titleTV;
        @BindView(R.id.current_book_author)
        TextView authorTV;

        public CurrentViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class WantViewHolder extends MultiRecyclerLayout.MultiViewHolder {
        View mView;
        @BindView(R.id.want_book_image)
        ImageView wantIV;
        @BindView(R.id.want_book_title)
        TextView titleTV;
        @BindView(R.id.want_book_author)
        TextView authorTV;

        public WantViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class ReadViewHolder extends MultiRecyclerLayout.MultiViewHolder {

        View mView;
        @BindView(R.id.read_book_image)
        ImageView readImage;
        @BindView(R.id.read_book_title)
        TextView titleTV;
        @BindView(R.id.read_book_author)
        TextView authorTV;

        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void init() {
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
        if (!refreshLayout.isRefreshing()) {
            currentPB.setVisibility(View.VISIBLE);
        }
        new CurrentlyReadingLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchWantBooks() {
        if (!refreshLayout.isRefreshing()) {
            wantPB.setVisibility(View.VISIBLE);
        }
        new WantToReadLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    private void fetchReadBooks() {
        if (!refreshLayout.isRefreshing()) {
            readPB.setVisibility(View.VISIBLE);
        }
        new ToReadBookLoader(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user.getId());
    }

    @Override
    public void ReviewsFetched(Reviews reviews) {
        if (reviews != null) {
            String noPhoto = "noPhoto";
            currentPB.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
            currentlyReadingTV.setVisibility(View.VISIBLE);
            currentBookList = reviews.getReview();

            if (currentBookList == null) {
                emptyCurrentTV.setVisibility(View.VISIBLE);
            } else {
                emptyCurrentTV.setVisibility(View.GONE);
                for (int i = 0; i < currentBookList.size(); i++) {

                    currentBookAdapter.add(currentBookList);
                    reviewList1.addAll(currentBookList);
                    multiRecyclerLayout.notifyDataChanged1();
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
                    reviewList2.addAll(wantedBookList);
                    multiRecyclerLayout.notifyDataChanged2();
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
                    reviewList3.addAll(toReadBookList);
                    multiRecyclerLayout.notifyDataChanged3();
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
                newItem2.add(googleImages.get(j));
            }
        }
    }

    @Override
    public void OnReadImageFetched(List<Item> itemList) {
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                readBookAdapter.newImage(itemList.get(i));
                newItem3.add(itemList.get(i));
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

                Intent intent1 = new Intent(getContext(), SearchFragment.class);
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
