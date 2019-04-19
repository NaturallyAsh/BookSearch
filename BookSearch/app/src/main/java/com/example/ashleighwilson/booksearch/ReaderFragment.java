package com.example.ashleighwilson.booksearch;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.models.Reader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.fragment.app.FragmentManager;
import in.nashapp.epublibdroid.EpubReaderView;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class ReaderFragment extends Fragment {

    private static final String TAG = ReaderFragment.class.getSimpleName();

    private EpubReaderView epubReaderView;
    private ImageView chaptersIV;
    private ImageView themeIV;
    private BookDbHelper dbHelper;
    private ReaderActivity readerActivity;
    private Reader readerItem;

    public ReaderFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = BookDbHelper.getInstance();
        readerActivity = (ReaderActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.reader_fragment, container, false);

        readerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        readerActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        epubReaderView = rootView.findViewById(R.id.epub_reader);
        chaptersIV = rootView.findViewById(R.id.reader_chapsIV);
        themeIV = rootView.findViewById(R.id.reader_themeIV);

        readerItem = getArguments().getParcelable(ReaderListFragment.EPUB_LOCATION);
        if (readerItem != null) {
            initReader(readerItem);
        }
        return rootView;
    }

    private void initReader(Reader reader) {
        //String epub_location = getArguments().getString(ReaderListFragment.EPUB_LOCATION);
        String epub_location = reader.getPathLocation();
        if (epub_location != null) {
            Log.i(TAG, "epub not null: " + epub_location);
            epubReaderView.OpenEpubFile(epub_location);

            epubReaderView.GotoPosition(0, (float)0);
            epubReaderView.setEpubReaderListener(new EpubReaderView.EpubReaderListener() {
                @Override
                public void OnPageChangeListener(int ChapterNumber, int PageNumber, float ProgressStart, float ProgressEnd) {

                }

                @Override
                public void OnChapterChangeListener(int ChapterNumber) {

                }

                @Override
                public void OnTextSelectionModeChangeListner(Boolean mode) {

                }

                @Override
                public void OnLinkClicked(String url) {

                }

                @Override
                public void OnBookStartReached() {

                }

                @Override
                public void OnBookEndReached() {

                }

                @Override
                public void OnSingleTap() {

                }
            });
        }
        initViews();
    }

    private void initViews() {
        chaptersIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epubReaderView.ListChaptersDialog(epubReaderView.GetTheme());
            }
        });
        themeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (epubReaderView.GetTheme() == epubReaderView.THEME_LIGHT) {
                    epubReaderView.SetTheme(epubReaderView.THEME_DARK);
                } else {
                    epubReaderView.SetTheme(epubReaderView.THEME_LIGHT);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_nav, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                readerActivity.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean goHome() {
        if (readerActivity != null && readerActivity.getSupportFragmentManager() != null) {
            readerActivity.getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    public void onBackPressed()
    {
        FragmentManager manager = readerActivity.getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0)
        {
            manager.popBackStack();
        }
    }
}
