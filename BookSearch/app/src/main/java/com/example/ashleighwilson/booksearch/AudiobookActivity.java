package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AudiobookActivity extends AppCompatActivity {

    private static final String TAG = AudiobookActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;
    public static final String FRAGMENT_AUDIOPLAYER_LIST_TAG = "audiobook_player";
    public static final String FRAGMENT_AUDIOPLAYER_TAG = "audiobook_player";
    public Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiobook_activity_layout);
        toolbar = findViewById(R.id.audio_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_AUDIOPLAYER_LIST_TAG) == null) {
            FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
            transaction.add(R.id.audiobook_frag_container, new AudiobookListFragment(), FRAGMENT_AUDIOPLAYER_LIST_TAG).commit();
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

    public void switchToPlayer() {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        AudiobookPlayerFragment playerFragment = new AudiobookPlayerFragment();
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_AUDIOPLAYER_TAG) == null) {
            transaction.replace(R.id.audiobook_frag_container, playerFragment, FRAGMENT_AUDIOPLAYER_TAG)
                    .addToBackStack(FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    .commit();
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.audiobook_frag_container, playerFragment, FRAGMENT_AUDIOPLAYER_TAG)
                    .addToBackStack(FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    .commit();
        }
    }

    public void switchToList() {
        FragmentTransaction transaction = getFragmentManagerInstance().beginTransaction();
        AudiobookListFragment listFragment = new AudiobookListFragment();
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_AUDIOPLAYER_LIST_TAG) == null) {
            transaction.replace(R.id.audiobook_frag_container, listFragment, FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    //.addToBackStack(FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    .commit();
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            transaction.replace(R.id.audiobook_frag_container, listFragment, FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    //.addToBackStack(FRAGMENT_AUDIOPLAYER_LIST_TAG)
                    .commit();
        }
    }

    public Toolbar getToolbar () {
        return this.toolbar;
    }

}
