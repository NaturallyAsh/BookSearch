package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.adapters.SeriesBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.SimilarBookAdapter;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.loaders.AddToShelfLoader;
import com.example.ashleighwilson.booksearch.loaders.BookDetailsLoader;
import com.example.ashleighwilson.booksearch.loaders.ReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SimilarImageLoader;
import com.example.ashleighwilson.booksearch.models.Book;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Series;
import com.example.ashleighwilson.booksearch.models.SeriesWork;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.taufiqrahman.reviewratings.Bar;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailFragment extends Fragment implements BookDetailsLoader.OnBookDetailsListener,
        ReadImageLoader.OnReadImageListener, SeriesBookAdapter.OnSeriesClickedListener,
        SeriesBookLoader.OnSeriesLoadedListener, SeriesImageLoader.OnSeriesImageListener,
        SimilarImageLoader.OnSimilarImageListener, SimilarBookAdapter.OnSimilarBookClickedListener {

    private static final String TAG = BookDetailFragment.class.getSimpleName();

    public static final String BOOK_ARG_ITEM = "book_arg_item";
    public static final String SERIES_ARG_ITEM = "series_arg_item";
    public static final String SEARCH_ARG_ID = "search_arg_id";
    public static final String SEARCH_ARG_TITLE = "search_arg_title";

    @Inject
    GoodreadsApiRetro2 goodreadsApi;
    private Review mReviewIntent;
    private UserBook mUserBookIntent;
    private SeriesWork mSeriesBookIntent;
    private UserBook book;
    private String searchId;
    private String searchTitle;
    private MainActivity mainActivity;
    private Item item;
    private SeriesBookAdapter seriesBookAdapter;
    private SimilarBookAdapter similarBookAdapter;
    private ArrayList<SeriesWork> seriesArrayList = new ArrayList<>();
    private ArrayList<UserBook> similarArrayList = new ArrayList<>();
    @BindView(R.id.detail_CT)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.detail_author)
    TextView authorTV;
    @BindView(R.id.detail_published)
    TextView publishedTV;
    @BindView(R.id.detail_imageView)
    ImageView bookCoverIV;
    ExpandableTextView expandTV;
    @BindView(R.id.detail_series_recyclerview)
    RecyclerView seriesRV;
    @BindView(R.id.detail_similar_recyclerview)
    RecyclerView similarRV;
    //@BindView(R.id.rating_bar)
    //RatingBar ratingBar;
    @BindView(R.id.detail_rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.rating_float)
    TextView ratingFloatTV;
    @BindView(R.id.ratings_total)
    TextView totalRatingsTV;
    @BindView(R.id.reviews_total)
    TextView reviewsTotal;
    @BindView(R.id.detail_progress_container)
    RelativeLayout progressContainer;
    @BindView(R.id.detail_progress)
    ProgressBar progressBar;
    @BindView(R.id.details_container)
    NestedScrollView detailsContainer;
    @BindView(R.id.rating_reviews)
    RatingReviews ratingReviewsBar;
    @BindView(R.id.detail_description_TV)
    TextView descriptionTV;
    @BindView(R.id.detail_more_description_BT)
    Button more_description_BT;
    @BindView(R.id.detail_more_reviews_BT)
    Button more_reviews_BT;
    @BindView(R.id.detail_shelf_BT)
    Button shelf_BT;

    public BookDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity)getActivity();
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        if(bundle.containsKey(BookDetailFragment.BOOK_ARG_ITEM)) {
            //mUserBookIntent = bundle.getParcelable(BookDetailFragment.BOOK_ARG_ITEM);
            String userBookJson = bundle.getString(BOOK_ARG_ITEM);
            mUserBookIntent = UserBook.fromJson(userBookJson);
        } else if (bundle.containsKey(UserFragment.REVIEW_ITEM)) {
            //mReviewIntent = bundle.getParcelable(UserFragment.REVIEW_ITEM);
            String reviewJson = bundle.getString(UserFragment.REVIEW_ITEM);
            mReviewIntent = Review.fromJson(reviewJson);
        } else if (bundle.containsKey(BookDetailFragment.SERIES_ARG_ITEM)) {
            //mSeriesBookIntent = bundle.getParcelable(BookDetailFragment.SERIES_ARG_ITEM);
            String seriesJson = bundle.getString(SERIES_ARG_ITEM);
            mSeriesBookIntent = SeriesWork.fromJson(seriesJson);
        } else if (bundle.containsKey(SEARCH_ARG_ID) && bundle.containsKey(SEARCH_ARG_TITLE)) {
            searchId = bundle.getString(SEARCH_ARG_ID);
            searchTitle = bundle.getString(SEARCH_ARG_TITLE);
            Log.i(TAG, "search id: " + searchId + " search title: " + searchTitle);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity.onDirection(MainActivity.Direction.CHILDREN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.detail_book_fragment, container,
                false);
        ButterKnife.bind(this, rootView);
        Injector.getInstance().inject(this);
        mainActivity.getSupportActionBar();
        //mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        LinearLayoutManager seriesManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        LinearLayoutManager similarManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);

        seriesRV.setLayoutManager(seriesManager);
        seriesBookAdapter = new SeriesBookAdapter(getContext(), seriesArrayList, this);
        seriesRV.setAdapter(seriesBookAdapter);

        similarRV.setLayoutManager(similarManager);
        similarBookAdapter = new SimilarBookAdapter(getContext(), similarArrayList, this);
        similarRV.setAdapter(similarBookAdapter);

        //mReviewIntent = getArguments().getParcelable(UserFragment.REVIEW_ITEM);
        if (mReviewIntent != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails(mReviewIntent.getBook().getId().getTextValue());
            //fetchVolumeDetails(mReviewIntent.getBook().getTitle());
            //init();
        } else {
            Log.i(TAG, "review intent is null");
        }
        //mUserBookIntent = getArguments().getParcelable(BOOK_ARG_ITEM);
        if (mUserBookIntent != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails(mUserBookIntent.getId().getTextValue());
            //fetchVolumeDetails(mUserBookIntent.getTitle());
        } else {
            Log.i(TAG, "book intent is null");
        }

        if (mSeriesBookIntent != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails(mSeriesBookIntent.getWork().getBestBook().getId());
            //fetchVolumeDetails(mSeriesBookIntent.getWork().getBestBook().getTitle());
        } else {
            Log.i(TAG, "series intent is null");
        }

        if (searchId != null && searchTitle != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails(searchId);
            //fetchVolumeDetails(searchTitle);
        }

        shelf_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (book != null) {
                    /*new AddToShelfLoader("read", book.getId().getTextValue()).executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR);*/
                    getShelfPopup(v, book);
                }
            }
        });

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (book != null && mReviewIntent != null || book != null && mUserBookIntent != null
            || book != null && mSeriesBookIntent != null || book != null && searchId != null
                || book != null && searchTitle != null) {
            //float stars = Float.parseFloat(mReviewIntent.getBook().getAverageRating());
            float stars = Float.parseFloat(book.getAverageRating());
            mainActivity.getSupportActionBar().setTitle(book.getTitle());
            ratingBar.setRating(stars);
            ratingFloatTV.setText(book.getAverageRating());
            totalRatingsTV.setText(book.getRatingsCount() + " " + getString(R.string.ratings));
            reviewsTotal.setText(book.getWork().getText_reviews_count() + " " + getString(R.string.reviews));
            authorTV.setText(book.getAuthor().getAuthor().get(0).getName());
            if (mReviewIntent != null) {
                for (int i = 0; i < mReviewIntent.getShelves().size(); i++) {
                    Log.i(TAG, "shelf: " + mReviewIntent.getShelves().get(i).getShelf().get(i).getName());
                    shelf_BT.setText(mReviewIntent.getShelves().get(i).getShelf().get(i).getName());
                }
            }
            if (searchTitle != null) {
                shelf_BT.setText("ADD_TO_SHELF");
            }
            String pubDay = book.getPublicationDay();
            String pubMonth = book.getPublicationMonth();
            String pubYear = book.getPublicationYear();
            String publication = pubMonth + "/" + pubDay + "/" + pubYear;
            if (publication.contains("null")) {
                publishedTV.setText("No publication available");
            } else if (item != null) {
                if (item.getVolumeInfo().getPublishedDate() != null ) {
                    publishedTV.setText(item.getVolumeInfo().getPublishedDate());
                }
            } else {
                publishedTV.setText(publication);
            }
            more_reviews_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMoreReviews(book.getReviewsWidget());
                }
            });
            //loadCover();
            getRatings();
            getDescription();
        }
    }

    private void getShelfPopup(View view, UserBook mBook) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenu().clear();

        popupMenu.inflate(R.menu.menu_shelf_popup);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_current_shelf:
                        new AddToShelfLoader("currently-reading", mBook.getId().getTextValue())
                                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        shelf_BT.setText("Currently_Reading");
                        return true;
                    case R.id.menu_read_shelf:
                        new AddToShelfLoader("read", mBook.getId().getTextValue())
                                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        shelf_BT.setText("Read");
                        return true;
                    case R.id.menu_want_shelf:
                        new AddToShelfLoader("to-read", mBook.getId().getTextValue())
                                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        shelf_BT.setText("Want_To_Read");
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void loadCover() {
        String noPhoto = "noPhoto";
        if (item != null && book.getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
            String identifier = "";
            for (int i = 0; i < item.getVolumeInfo().getIndustryIdentifiers().size(); i++) {
                //Log.i(TAG, "indus: " + item.getVolumeInfo().getIndustryIdentifiers().get(i).getIdentifier());
                identifier = item.getVolumeInfo().getIndustryIdentifiers().get(i).getIdentifier();
            }
            if (item.getVolumeInfo().getImageLinks() != null) {
                //Log.i(TAG, "image links not null");
                Glide.with(getContext())
                        .load(item.getVolumeInfo().getImageLinks().getSmallThumbnail())
                        .into(bookCoverIV);
            } else {
                Glide.with(getContext())
                        .load(book.getAltBookCover(identifier))
                        .into(bookCoverIV);
            }

        } else {
            {
                Glide.with(getContext())
                        .load(book.getImageUrl())
                        .into(bookCoverIV);
            }


            /*book.setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
            Glide.with(getContext())
                    .load(book.getImageUrl())
                    .into(bookCoverIV);

            if (item.getVolumeInfo().getImageLinks() != null) {
                Log.i(TAG, "image links not null");
                book.setImageUrl(book.getAltBookCover(identifier));
                Glide.with(getContext())
                        .load(book.getImageUrl())
                        .into(bookCoverIV);

            } else {
                Glide.with(getContext())
                        .load(book.getAltBookCover(identifier))
                        .into(bookCoverIV);
            }

        } else {
            {
                Glide.with(getContext())
                        .load(book.getImageUrl())
                        .into(bookCoverIV);
            }*/
        }
    }

    private void getDescription() {
        /*if (mReviewIntent.getBook().getDescription() != null) {
            descriptionTV.setText(Html.fromHtml(mReviewIntent.getBook().getDescription()));
        } */
        if (book.getDescription() != null) {
            //Log.i(TAG, "book descript called");
            descriptionTV.setText(Html.fromHtml(book.getDescription()));
        } else if (item.getVolumeInfo().getDescription() != null){
            descriptionTV.setText(Html.fromHtml(item.getVolumeInfo().getDescription()));
        } else {
            descriptionTV.setText("No description available");
        }
        int linesCount = descriptionTV.getLayout().getLineCount();
        if (linesCount > 14) {
            more_description_BT.setVisibility(View.VISIBLE);
            descriptionTV.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            more_description_BT.setVisibility(View.GONE);
        }

        more_description_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescriptionDialog();
            }
        });
    }

    private void getMoreReviews(String reviewsWidget) {
        //Log.i(TAG, "reviews widget: " + reviewsWidget);
        String widget = reviewsWidget;
        String[] remove = StringUtils.substringsBetween(widget, "src=", " width");
        String widgetUrl = remove[0];
        String iFrameUrl = StringUtils.replace(widgetUrl, "\"", "");

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View reviewsView = inflater.inflate(R.layout.reviews_webview, null, false);
        WebView webView = reviewsView.findViewById(R.id.reviews_webView);
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        webView.loadUrl(iFrameUrl);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setView(webView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDescriptionDialog() {
        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View descriptView = inflater.inflate(R.layout.description_view, null, false);
        TextView descript = descriptView.findViewById(R.id.description_view_text);
        descript.setText(descriptionTV.getText());

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setView(descriptView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchBookDetails(String id) {
        new BookDetailsLoader(id, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void fetchVolumeDetails(String title) {
        //Log.i(TAG, "title: " + title);
        new ReadImageLoader(title, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void fetchSeriesDetails(UserBook seriesBook) {
        if (seriesBook != null) {
            //Log.i(TAG, "series work: " + seriesBook.getSeriesWorks().get(0).getSeries().getId());
            String id = "";

            for (int i = 0; i < seriesBook.getSeriesWorks().size(); i++) {
                if (Integer.valueOf(seriesBook.getSeriesWorks().get(0).getSeries().getId()) >= 5) {
                    //Log.i(TAG, "series work 1: " + seriesBook.getSeriesWorks().get(0).getSeries().getId());
                    id = seriesBook.getSeriesWorks().get(0).getSeries().getId();
                } else if (seriesBook.getSeriesWorks().size() > 1 &&
                        Integer.valueOf(seriesBook.getSeriesWorks().get(1).getSeries().getId()) >= 5){
                    //Log.i(TAG, "series work 2: " + seriesBook.getSeriesWorks().get(1).getSeries().getId());
                    id = seriesBook.getSeriesWorks().get(1).getSeries().getId();
                }
            }
            if (!id.equals("")){
                new SeriesBookLoader(id, this)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    private void fetchSimilarImages(String id) {
        if (id != null) {
            new SimilarImageLoader(id, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void getRatings() {
        String ratingDist = book.getWork().getRatingDist();
        String[] remove = StringUtils.substringsBetween(ratingDist, ":", "|");

        String star5 = remove[0];
        String star4 = remove[1];
        String star3 = remove[2];
        String star2 = remove[3];
        String star1 = remove[4];

        int star_5 = Integer.parseInt(star5);
        int star_4 = Integer.parseInt(star4);
        int star_3 = Integer.parseInt(star3);
        int star_2 = Integer.parseInt(star2);
        int star_1 = Integer.parseInt(star1);

        int maxRater = (int)findMax(star_1, star_2, star_3, star_4, star_5);
        //Log.i(TAG, "max: " + maxRater);

        int raters[] = new int[] {
                star_5,
                star_4,
                star_3,
                star_2,
                star_1
        };

        int colors[] = new int[] {
                ContextCompat.getColor(getContext(), R.color.DarkBlue),
                ContextCompat.getColor(getContext(), R.color.DarkGreen),
                ContextCompat.getColor(getContext(), R.color.DarkOrange),
                ContextCompat.getColor(getContext(), R.color.DarkRed),
                ContextCompat.getColor(getContext(), R.color.MediumPurple)
        };

        ratingReviewsBar.createRatingBars(maxRater, BarLabels.STYPE1, colors, raters);
        ratingReviewsBar.setOnBarClickListener(new RatingReviews.OnBarClickListener() {
            @Override
            public void onBarClick(Bar bar) {
                Toast.makeText(getContext(),bar.getStarLabel() + ": "
                        + bar.getRaters() + " ratings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double findMax(double... values) {
        double max = Double.NEGATIVE_INFINITY;
        for (double d : values) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public boolean goHome() {
        if (mainActivity != null && mainActivity.getSupportFragmentManager() != null) {
            if (searchTitle != null) {
                mainActivity.getSupportFragmentManager().popBackStack();
            } else {
                mainActivity.switchToList();
                if (mainActivity.toggle != null) {
                    mainActivity.toggle.setDrawerIndicatorEnabled(true);
                }
                mainActivity.onDirection(MainActivity.Direction.PARENT);
            }
        }
        return true;
    }

    @Override
    public void OnBookDetailsFetched(UserBook bookDetails) {
        if (bookDetails != null) {
            book = bookDetails;
            progressContainer.setVisibility(View.GONE);
            detailsContainer.setVisibility(View.VISIBLE);
            Log.i(TAG, "book details id: " + book.getId().getTextValue());
            fetchVolumeDetails(book.getTitle());
            fetchSeriesDetails(book);
            fetchSimilarImages(book.getId().getTextValue());
            init();
        }
    }

    @Override
    public void OnReadImageFetched(List<Item> itemList) {
        if (itemList != null) {
            if (book != null) {
                for (int i = 0; i < itemList.size(); i++) {
                    if (book.getTitle().toLowerCase().contains(itemList.get(i).getVolumeInfo().getTitle().toLowerCase())) {
                        item = itemList.get(i);
                        Log.i(TAG, "items: " + item.getVolumeInfo().getTitle());
                        loadCover();
                    }
                }
            }

        }
    }

    @Override
    public void OnSeriesFetched(Series series) {
        if (series != null) {
            List<SeriesWork> seriesList = new ArrayList<>();
            seriesList = series.getSeriesWorks();
            for (int i = 0; i < seriesList.size(); i++) {
                seriesBookAdapter.add(seriesList);
                new SeriesImageLoader(series.getSeriesWorks().get(i).getWork().getBestBook()
                        .getId(), this)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    @Override
    public void OnSeriesImageFetched(List<Item> itemList) {
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                //Log.i(TAG, "series: " + itemList.get(i).getVolumeInfo().getTitle());
                seriesBookAdapter.newImage(itemList.get(i));
            }
        }
    }

    @Override
    public void OnSimilarImageFetched(List<Item> itemList) {
        if (book != null) {
            List<UserBook> bookList = book.getSimilarBooks();
            for (int j = 0; j < bookList.size(); j++) {
                similarBookAdapter.add(bookList);
            }
        }
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                similarBookAdapter.newImage(itemList.get(i));
            }
        }
    }

    @Override
    public void OnSeriesClicked(SeriesWork series, int position) {
        mainActivity.seriesSwitchBackToDetail(series);
    }

    @Override
    public void OnSimilarBookClicked(UserBook book, int position) {
        mainActivity.switchBackToDetail(book);
    }
}
