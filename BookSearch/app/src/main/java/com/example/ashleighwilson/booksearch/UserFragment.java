package com.example.ashleighwilson.booksearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.adapters.CurrentBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.ReadBookAdapter;
import com.example.ashleighwilson.booksearch.adapters.WantBookAdapter;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.loaders.CurrentlyReadingLoader;
import com.example.ashleighwilson.booksearch.loaders.ToReadBookLoader;
import com.example.ashleighwilson.booksearch.loaders.ToReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.WantToReadLoader;
import com.example.ashleighwilson.booksearch.loaders.WantedImageLoader;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements CurrentlyReadingLoader.OnReviewsFetchedListener,
    WantToReadLoader.OnWantedFetchedListener, ToReadBookLoader.OnReadFetchedListener,
    WantedImageLoader.WantedImageListener, ToReadImageLoader.OnToReadImageListener,
        CurrentBookAdapter.CurrentBookClickListener {

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
    public static String REVIEW_ITEM = "review_item";
    private MainActivity mainActivity;

    public UserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, rootView);

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
        wantBookAdapter = new WantBookAdapter(getContext(), wantArrayList);
        want_to_read_RV.setAdapter(wantBookAdapter);

        readRV.setLayoutManager(toReadManager);
        readBookAdapter = new ReadBookAdapter(getContext(), readArrayList);
        readRV.setAdapter(readBookAdapter);


        init();

        return rootView;
    }

    private void init() {
        if (user.getId() == -1) {
            mustBeLoggedInTV.setVisibility(View.VISIBLE);
            currentRV.setVisibility(View.GONE);
        } else {
            //Log.i(TAG, "user id: " + user.getId());
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
        String noPhoto = "noPhoto";
        currentPB.setVisibility(View.GONE);
        currentlyReadingTV.setVisibility(View.VISIBLE);
        List<Review> reviewList = reviews.getReview();

        for (int i = 0; i < reviewList.size(); i++) {

            currentBookAdapter.add(reviewList);
        }
    }

    @Override
    public void WantedFetched(Reviews reviews) {
        wantPB.setVisibility(View.GONE);
        want_to_read_TV.setVisibility(View.VISIBLE);
        wantedBookList = reviews.getReview();

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

    @Override
    public void ReadFetched(Reviews reviews) {
        readPB.setVisibility(View.GONE);
        readTV.setVisibility(View.VISIBLE);
        toReadBookList = reviews.getReview();

        for (int i = 0; i < toReadBookList.size(); i++) {
            readBookAdapter.add(toReadBookList);
            Review review = toReadBookList.get(i);
            String noPhoto = "noPhoto";
            if (review.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                String name = review.getBook().getTitle();
                //Log.i(TAG, "name: " + name);
                new ToReadImageLoader(name, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    @Override
    public void OnWantedImageLoaded(List<Item> googleImages) {
        if (googleImages != null) {
            for (int i = 0; i < wantedBookList.size(); i++) {
                Review review = wantedBookList.get(i);
                for (int j = 0; j < googleImages.size(); j++) {
                    Item item = googleImages.get(j);
                    String name = item.getVolumeInfo().getTitle();
                    if (review.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                        item.getVolumeInfo().getImageLinks() != null) {
                        //Log.i(TAG, "adding image: " + name);
                        wantBookAdapter.newImage(item);
                    }
                }
            }
        }
    }

    @Override
    public void OnToReadImageFetched(List<Item> itemList) {
        if (itemList != null) {
            for (int i = 0; i < toReadBookList.size(); i++) {
                Review review = toReadBookList.get(i);
                for (int j = 0; j < itemList.size(); j++) {
                    Item item = itemList.get(j);
                    String name = item.getVolumeInfo().getTitle();
                    if (review.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                        item.getVolumeInfo().getImageLinks() != null) {
                        //Log.i(TAG, "adding image: " + name);
                        readBookAdapter.newImage(item);
                    }
                }
            }
        }
    }

    @Override
    public void OnCurrentBookClicked(Review review, int position) {
        mainActivity.switchToDetail(review);
    }
}
