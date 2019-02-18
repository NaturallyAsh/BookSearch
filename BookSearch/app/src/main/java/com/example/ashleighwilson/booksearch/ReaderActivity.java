package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReaderActivity extends AppCompatActivity {

    private static final String TAG = ReaderActivity.class.getSimpleName();

    public Toolbar toolbar;
    private FragmentManager mFragmentManager;
    public static final String FRAGMENT_EREADER_LIST_TAG = "fragment_ereader_list";
    public static final String FRAGMENT_EREADER_TAG = "fragment_ereader";
    public final int TRANSITION_VERTICAL = 0;
    public final int TRANSITION_HORIZONTAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);

        toolbar = findViewById(R.id.reader_main_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private FragmentManager getFragmentManagerInstance() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        return mFragmentManager;
    }

    private void init() {
        getFragmentManagerInstance();
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_EREADER_LIST_TAG) == null) {
            FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
            transaction.add(R.id.reader_container, new ReaderListFragment(), FRAGMENT_EREADER_LIST_TAG).commit();
        }
    }

    public void switchToList() {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        ReaderListFragment mListFrag = new ReaderListFragment();
        transaction.replace(R.id.reader_container, mListFrag, FRAGMENT_EREADER_LIST_TAG)
                .addToBackStack(FRAGMENT_EREADER_TAG)
                .commit();
        getFragmentManagerInstance().getFragments();
    }

    public void switchToReader(String location) {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(transaction, TRANSITION_HORIZONTAL);
        ReaderFragment readerFragment = new ReaderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ReaderListFragment.EPUB_LOCATION, location);
        readerFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_EREADER_TAG) == null) {
            transaction.replace(R.id.reader_container, readerFragment, FRAGMENT_EREADER_TAG)
                    .addToBackStack(FRAGMENT_EREADER_LIST_TAG)
                    .commit();
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.reader_container, readerFragment, FRAGMENT_EREADER_TAG)
                    .addToBackStack(FRAGMENT_EREADER_TAG)
                    .commit();
        }
    }

    public void onBackPressed() {

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

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
}
