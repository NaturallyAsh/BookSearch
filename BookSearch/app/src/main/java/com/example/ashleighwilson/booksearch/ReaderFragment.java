package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import in.nashapp.epublibdroid.EpubReaderView;

public class ReaderFragment extends Fragment {

    private static final String TAG = ReaderFragment.class.getSimpleName();

    private EpubReaderView epubReaderView;
    private ImageView chaptersIV;
    private ImageView themeIV;

    public ReaderFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.reader_fragment, container, false);

        epubReaderView = rootView.findViewById(R.id.epub_reader);
        chaptersIV = rootView.findViewById(R.id.reader_chapsIV);
        themeIV = rootView.findViewById(R.id.reader_themeIV);

        initReader();

        return rootView;
    }

    private void initReader() {
        String epub_location = getArguments().getString(ReaderListFragment.EPUB_LOCATION);
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
}
