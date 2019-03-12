package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.loaders.BookDetailsLoader;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.example.ashleighwilson.booksearch.views.RatingBar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailFragment extends Fragment implements BookDetailsLoader.OnBookDetailsListener {

    private static final String TAG = BookDetailFragment.class.getSimpleName();

    private Review mReview;
    private UserBook book;
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


    public BookDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.detail_book_fragment, container,
                false);
        ButterKnife.bind(this, rootView);

        expandTV = rootView.findViewById(R.id.expand_detail_view).findViewById(R.id.expand_text_view);

        mReview = getArguments().getParcelable(UserFragment.REVIEW_ITEM);
        if (mReview != null) {
            progressContainer.setVisibility(View.VISIBLE);
            detailsContainer.setVisibility(View.INVISIBLE);
            fetchBookDetails();
            //init();
            Log.i(TAG, "isbn: " + mReview.getBook().getId().getTextValue());
        } else {
            Log.i(TAG, "review intent is null");
        }

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (book != null) {
            float stars = Float.parseFloat(mReview.getBook().getAverageRating());
            ratingBar.setRating(stars);
            ratingFloatTV.setText(mReview.getBook().getAverageRating());
            totalRatingsTV.setText(mReview.getBook().getRatingsCount() + " " + getString(R.string.ratings));
            reviewsTotal.setText(book.getWork().getText_reviews_count() + " " + getString(R.string.reviews));
            loadCover();

            authorTV.setText(mReview.getBook().getAuthor().getAuthor().getName());
            getDescription();
        }
    }

    private void loadCover() {
        Glide.with(getContext())
                .load(mReview.getBook().getImageUrl())
                .into(bookCoverIV);
    }

    private void getDescription() {
        expandTV.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {

            }
        });
        expandTV.setText(Html.fromHtml(mReview.getBook().getDescription()));
    }

    private void fetchBookDetails() {
        //long id = Long.parseLong(mReview.getBook().getId().getTextValue());
        new BookDetailsLoader(mReview.getBook().getId().getTextValue(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void OnBookDetailsFetched(UserBook bookDetails) {
        if (bookDetails != null) {
            book = bookDetails;
            progressContainer.setVisibility(View.GONE);
            detailsContainer.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);
            //Log.i(TAG, "book ratings: " + book.getWork().getText_reviews_count());
            init();
        }
    }
}
