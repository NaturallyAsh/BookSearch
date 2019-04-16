package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class AudiobookPlayerFragment extends Fragment {

    private static final String TAG = AudiobookPlayerFragment.class.getSimpleName();

    private AudiobookActivity audiobookActivity;


    public AudiobookPlayerFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audiobookActivity = (AudiobookActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audiobook_player, container,
                false);

        audiobookActivity.getSupportActionBar().setTitle("Now Playing");
        audiobookActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        return rootView;
    }

    private void goHome() {
        if (audiobookActivity != null && audiobookActivity.getSupportActionBar() != null) {
            audiobookActivity.switchToList();
        }
    }
}
