package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.adapters.SeriesBookAdapter;
import com.example.ashleighwilson.booksearch.loaders.BookDetailsLoader;
import com.example.ashleighwilson.booksearch.loaders.ReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesImageLoader;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Series;
import com.example.ashleighwilson.booksearch.models.SeriesWork;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.example.ashleighwilson.booksearch.views.RatingBar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailFragment extends Fragment implements BookDetailsLoader.OnBookDetailsListener,
        ReadImageLoader.OnReadImageListener, SeriesBookAdapter.OnSeriesClickedListener,
        SeriesBookLoader.OnSeriesLoadedListener, SeriesImageLoader.OnSeriesImageListener {

    private static final String TAG = BookDetailFragment.class.getSimpleName();

    private Review mReview;
    private UserBook book;
    private MainActivity mainActivity;
    private Item item;
    private SeriesBookAdapter seriesBookAdapter;
    private ArrayList<SeriesWork> seriesArrayList = new ArrayList<>();
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
    @BindView(R.id.rating_bar)
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


    public BookDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity)getActivity();
        setHasOptionsMenu(true);
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

        mReview = getArguments().getParcelable(UserFragment.REVIEW_ITEM);
        if (mReview != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails();
            fetchVolumeDetails(mReview.getBook().getTitle());
            //init();
        } else {
            Log.i(TAG, "review intent is null");
        }

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (book != null && mReview != null) {
            float stars = Float.parseFloat(mReview.getBook().getAverageRating());
            mainActivity.getSupportActionBar().setTitle(mReview.getBook().getTitle());
            ratingBar.setRating(stars);
            ratingFloatTV.setText(mReview.getBook().getAverageRating());
            totalRatingsTV.setText(mReview.getBook().getRatingsCount() + " " + getString(R.string.ratings));
            reviewsTotal.setText(book.getWork().getText_reviews_count() + " " + getString(R.string.reviews));
            authorTV.setText(mReview.getBook().getAuthor().getAuthor().getName());
            String pubDay = mReview.getBook().getPublicationDay();
            String pubMonth = mReview.getBook().getPublicationMonth();
            String pubYear = mReview.getBook().getPublicationYear();
            String publication = pubMonth + "/" + pubDay + "/" + pubYear;
            if (publication.contains("null")) {
                publishedTV.setText("No publication available");
            } else if (item != null) {
                if (item.getVolumeInfo().getPublishedDate() != null &&
                    publication.contains("null")) {
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
            loadCover();
            getRatings();
            getDescription();
        }
    }

    private void loadCover() {
        String noPhoto = "noPhoto";
        if (item != null && mReview.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
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
                        .load(mReview.getBook().getAltBookCover(identifier))
                        .into(bookCoverIV);
            }

        } else {
            {
                Glide.with(getContext())
                        .load(mReview.getBook().getImageUrl())
                        .into(bookCoverIV);
            }
        }
    }

    private void getDescription() {
        if (mReview.getBook().getDescription() != null) {
            //Log.i(TAG, "review descript called");
            descriptionTV.setText(Html.fromHtml(mReview.getBook().getDescription()));
        } else if (book.getDescription() != null) {
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

        //WebView webView = new WebView(getContext());
        //webView.loadUrl(iFrameUrl);
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

    private void fetchBookDetails() {
        new BookDetailsLoader(mReview.getBook().getId().getTextValue(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void fetchVolumeDetails(String title) {
        //Log.i(TAG, "title: " + mReview.getBook().getTitle());
        new ReadImageLoader(title, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void fetchSeriesDetails() {
        if (book != null) {
            //Log.i(TAG, "series id: " + book.getSeriesWorks().getSeriesWork().getSeries().getId());
            new SeriesBookLoader(book.getSeriesWorks().getSeriesWork().getSeries().getId(), this)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public void OnBookDetailsFetched(UserBook bookDetails) {
        if (bookDetails != null) {
            book = bookDetails;
            //Log.i(TAG, "book asin: " + book.getAsin());
            progressContainer.setVisibility(View.GONE);
            detailsContainer.setVisibility(View.VISIBLE);
            fetchSeriesDetails();

            init();
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
            mainActivity.getSupportFragmentManager().popBackStack();
            if (mainActivity.toggle != null) {
                mainActivity.toggle.setDrawerIndicatorEnabled(true);
            }
            mainActivity.onDirection(MainActivity.Direction.PARENT);
        }
        return true;
    }

    @Override
    public void OnReadImageFetched(List<Item> itemList) {
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                if (mReview.getBook().getTitle().toLowerCase().contains(itemList.get(i).getVolumeInfo().getTitle().toLowerCase())) {
                    item = itemList.get(i);
                    //Log.i(TAG, "items: " + item.getVolumeInfo().getTitle());
                }
            }
        }
    }

    @Override
    public void OnSeriesClicked(SeriesWork series, int position) {

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
}
