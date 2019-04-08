package com.example.ashleighwilson.booksearch;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.SeriesWork;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;
    public static final String FRAGMENT_USERFRAG_TAG = "fragment_user";
    public static final String FRAGMENT_BOOKDETAILS_TAG = "fragment_book_details";
    public static final String FRAGMENT_SEARCH_TAG = "fragment_search";
    public final int TRANSITION_VERTICAL = 0;
    public final int TRANSITION_HORIZONTAL = 1;
    public Direction direction;
    static final int BURGER = 0;
    static final int ARROW = 1;
    public NavigationView mNavigationView;
    private Button authenticat_BT;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    View navHeaderView;
    CircleImageView circleImageView;
    TextView headerTV;
    public DrawerLayout drawer;
    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser currentUser;
    UserBook userBook;
    Review userReview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Injector.getInstance().inject(this);


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        authenticat_BT = findViewById(R.id.authenticate_button);
        authenticat_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mNavigationView = findViewById(R.id.nav_view);
        //mNavigationView.bringToFront();
        mNavigationView.setNavigationItemSelectedListener(this);
        navHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header_main_nav);
        circleImageView = navHeaderView.findViewById(R.id.nav_header_imageView);
        headerTV = navHeaderView.findViewById(R.id.nav_header_tv);
        if (currentUser != null) {
            //Log.i(TAG, "current user: " + currentUser);
            authenticat_BT.setVisibility(View.GONE);
            if (currentUser.getImage_url() != null) {
                Glide.with(this)
                        .load(currentUser.getImage_url())
                        .into(circleImageView);
                //headerTV.setVisibility(View.GONE);
                headerTV.setText(currentUser.getName());
            } else {
                authenticat_BT.setVisibility(View.VISIBLE);
            }
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init();

    }

    public FragmentManager getFragmentManagerInstance() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
            Log.i(TAG, "getFragInstance null");
        }
        return mFragmentManager;
    }

    private void init() {
        getFragmentManagerInstance();
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_USERFRAG_TAG) == null) {
            FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
            transaction.add(R.id.main_frag_container, new UserFragment(), FRAGMENT_USERFRAG_TAG).commit();
        }
    }

    private Fragment checkFragmentInstance(int id, Object instanceClass)
    {
        Fragment result = null;
        Fragment fragment = getFragmentManagerInstance().findFragmentById(id);
        if (fragment != null && instanceClass.equals(fragment.getClass()))
        {
            result = fragment;
        }
        return result;
    }

    public void OnBackPressed() {
        Fragment f = checkFragmentInstance(R.id.main_frag_container, BookDetailFragment.class);
        if (f != null) {
            //((ReaderFragment) f).goHome();
        }
        super.onBackPressed();
    }

    public void switchToDetail(Review review) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        BookDetailFragment detailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(UserFragment.REVIEW_ITEM, review);
        bundle.putString(UserFragment.REVIEW_ITEM, review.toJson());
        detailFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_BOOKDETAILS_TAG) == null) {
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_USERFRAG_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_BOOKDETAILS_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void switchBackToDetail(UserBook book) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        BookDetailFragment detailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(BookDetailFragment.BOOK_ARG_ITEM, book);
        bundle.putString(BookDetailFragment.BOOK_ARG_ITEM, book.toJson());
        detailFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_BOOKDETAILS_TAG) == null) {
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_USERFRAG_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_BOOKDETAILS_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void searchToDetail(String bookId, String title) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        BookDetailFragment detailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(BookDetailFragment.SERIES_ARG_ITEM, seriesWork);
        bundle.putString(BookDetailFragment.SEARCH_ARG_ID, bookId);
        bundle.putString(BookDetailFragment.SEARCH_ARG_TITLE, title);
        detailFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_BOOKDETAILS_TAG) == null) {
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_SEARCH_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_BOOKDETAILS_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void seriesSwitchBackToDetail(SeriesWork seriesWork) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        BookDetailFragment detailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(BookDetailFragment.SERIES_ARG_ITEM, seriesWork);
        bundle.putString(BookDetailFragment.SERIES_ARG_ITEM, seriesWork.toJson());
        detailFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_BOOKDETAILS_TAG) == null) {
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_USERFRAG_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.main_frag_container, detailFragment, FRAGMENT_BOOKDETAILS_TAG)
                    .addToBackStack(FRAGMENT_BOOKDETAILS_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void switchToSearch(String query) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(BookDetailFragment.SERIES_ARG_ITEM, seriesWork);
        bundle.putString(SearchFragment.SEARCH_ARG_QUERY, query);
        searchFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_SEARCH_TAG) == null) {
            transaction.replace(R.id.main_frag_container, searchFragment, FRAGMENT_SEARCH_TAG)
                    .addToBackStack(FRAGMENT_USERFRAG_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.main_frag_container, searchFragment, FRAGMENT_SEARCH_TAG)
                    .addToBackStack(FRAGMENT_SEARCH_TAG)
                    .commit();
            if (toggle != null) {
                toggle.setDrawerIndicatorEnabled(false);
            }
        }
    }

    public void switchToList() {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        UserFragment mUserFragment = new UserFragment();
        transaction.replace(R.id.main_frag_container, mUserFragment, FRAGMENT_USERFRAG_TAG)
                .addToBackStack
                (FRAGMENT_BOOKDETAILS_TAG).commitAllowingStateLoss();
        if (toggle != null) {
            toggle.setDrawerIndicatorEnabled(false);
        }
        getFragmentManagerInstance().getFragments();
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }


    @Override
    public void onBackPressed() {
        Log.i(TAG, "back pressed");
        //drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public DrawerLayout getDrawerLayout() {
        return drawer;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case R.id.favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_reader:
                Intent readerIntent = new Intent(this, ReaderActivity.class);
                startActivity(readerIntent);
                break;
            case R.id.nav_logout:
                if (preferenceUser.getCurrentUser() != null) {
                    preferenceUser.setCurrentUser(null);
                    Intent logoutIntent = new Intent(this, MainActivity.class);
                    startActivity(logoutIntent);
                }
        }
        item.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void animateTransition(FragmentTransaction transaction, int direction)
    {
        if (direction == TRANSITION_HORIZONTAL)
        {
            transaction.setCustomAnimations(R.anim.fade_in_support, R.anim.fade_out_support,
                    R.anim.fade_in_support, R.anim.fade_out_support);
        }
        if (direction == TRANSITION_VERTICAL)
        {
            transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_pop,
                    R.anim.anim_out_pop);
        }
    }

    public enum Direction {
        CHILDREN, PARENT
    }

    public void onDirection(Direction direction) {
        switch (direction) {
            case CHILDREN:
                animateBurger(ARROW);
                break;
            default:
                animateBurger(BURGER);
        }
    }

    void animateBurger(int targetShape) {
        if (toggle != null) {
            if (targetShape != BURGER && targetShape != ARROW)
                return;
            ValueAnimator anim = ValueAnimator.ofFloat((targetShape + 1) % 2, targetShape);
            anim.addUpdateListener(valueAnimator -> {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(drawer, slideOffset);
            });
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(500);
            anim.start();
        }
    }
}
