package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioPlayListViewFrag extends Fragment {

    private static final String TAG = AudioPlayListViewFrag.class.getSimpleName();

    private ArrayList<HashMap<String, String>> audioList = new ArrayList<>();
    public static final String PLAYLIST_ARG = "playlist_arg";

    private ListView listView;
    private ListAdapter adapter;
    private AudiobookActivity audiobookActivity;

    AudioPlayListViewFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audiobookActivity = (AudiobookActivity) getActivity();

        Bundle bundle = getArguments();
        if (bundle.containsKey(PLAYLIST_ARG)) {
            audioList = (ArrayList<HashMap<String, String>>) bundle.getSerializable(PLAYLIST_ARG);
            //Log.i(TAG, "audiolist: " + audioList.toString());

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                             savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.playerlist, container, false);

        listView = rootView.findViewById(R.id.playerlist_listview);

        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for (int i = 0; i < audioList.size(); i++) {
            HashMap<String, String> map = audioList.get(i);

            data.add(map);
            //Log.i(TAG, "data: " + data.size());
        }

        int title = R.id.playlist_songtitle;

        adapter = new SimpleAdapter(getActivity(), data, R.layout.playlist_item,
                new String[] { "title" }, new int[] {R.id.playlist_songtitle});

        //adapter = new ArrayAdapter<HashMap<String, String>>(audiobookActivity,
          //      android.R.layout.simple_list_item_1, data);

        listView.setAdapter(adapter);

        return rootView;
    }
}
