package com.example.ashleighwilson.booksearch;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.ashleighwilson.booksearch.adapters.AudiobookGridViewAdapter;
import com.example.ashleighwilson.booksearch.loaders.AudiobookImageLoader;
import com.example.ashleighwilson.booksearch.models.AudioBook;
import com.example.ashleighwilson.booksearch.models.Item;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AudiobookListFragment extends Fragment implements AudiobookImageLoader.OnAudiobookImageLoadedListener {

    private static final String TAG = AudiobookListFragment.class.getSimpleName();

    private static final int REQUEST_PERMS = 324;

    @BindView(R.id.audio_add_BT)
    Button addBT;
    @BindView(R.id.audio_now_playing_BT)
    Button nowPlayingBT;
    @BindView(R.id.audio_book_gridlist)
    GridView audioGridView;
    private AudiobookActivity audiobookActivity;
    private AudiobookGridViewAdapter adapter;
    private String path = "";
    private ArrayList<AudioBook> audioBookList = new ArrayList<>();

    public AudiobookListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audiobookActivity = (AudiobookActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audiobook_list_fragment, container,
                false);
        ButterKnife.bind(this, rootView);

        audiobookActivity.getSupportActionBar().setTitle("Audiobook Library");

        adapter = new AudiobookGridViewAdapter(getContext(), audioBookList);
        audioGridView.setAdapter(adapter);

        init();

        return rootView;
    }

    private void init() {
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        nowPlayingBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audiobookActivity.switchToPlayer();
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMS);
        } else {
            Intent addFileIntent = new Intent();
            addFileIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            addFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            addFileIntent.setType("audio/*");
            startActivityForResult(addFileIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1 && data != null) {
                Uri uri = data.getData();
                path = uri.getPath();

                String name = StringUtils.substringAfterLast(path, "/");
                String bookTitle = StringUtils.substringBefore(name, ".");
                Log.i(TAG, "name: " + bookTitle);
                fetchAudiobookDetails(bookTitle);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent addFileIntent = new Intent();
                addFileIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                addFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                addFileIntent.setType("audio/*");
                startActivityForResult(addFileIntent, 1);
            }
        }
    }

    private void fetchAudiobookDetails(String title) {
        new AudiobookImageLoader(title, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void OnAudiobookImageLoaded(List<Item> itemList) {
        if (itemList != null) {
            Item item = itemList.get(0);

            Log.i(TAG, "item: " + item.getVolumeInfo().getTitle());
            String image = "";
            if (item.getVolumeInfo().getImageLinks() != null) {
                image = item.getVolumeInfo().getImageLinks().getSmallThumbnail();
            }
            AudioBook audioBookItem = new AudioBook(
                    item.getVolumeInfo().getTitle(),
                    item.getVolumeInfo().getAuthors().get(0),
                    image,
                    item.getVolumeInfo().getPublishedDate(),
                    path
            );
            adapter.add(audioBookItem);
        }
    }
}
