package com.example.ashleighwilson.booksearch;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ashleighwilson.booksearch.adapters.AudiobookGridViewAdapter;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
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
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

public class AudiobookListFragment extends Fragment implements AudiobookImageLoader.OnAudiobookImageLoadedListener,
        AudiobookGridViewAdapter.OnAudiobookClickListener {

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
    private BookDbHelper dbHelper;
    private ExFilePicker exFilePicker;


    public AudiobookListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audiobookActivity = (AudiobookActivity) getActivity();
        dbHelper = BookDbHelper.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audiobook_list_fragment, container,
                false);
        ButterKnife.bind(this, rootView);

        exFilePicker = new ExFilePicker();

        audiobookActivity.getSupportActionBar().setTitle("Audiobook Library");

        adapter = new AudiobookGridViewAdapter(getContext(), audioBookList, this);
        audioGridView.setAdapter(adapter);

        initButtons();

        loadAudiobooksFromDb();

        return rootView;
    }

    private void initButtons() {
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        nowPlayingBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audiobookActivity.switchToPlayer(null);
            }
        });
    }

    private void loadAudiobooksFromDb() {
        audioBookList.clear();
        Cursor cursor = dbHelper.getAudioBooks();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String cover = cursor.getString(3);
            String published = cursor.getString(4);
            int currentPosition = cursor.getInt(5);
            String path = cursor.getString(6);

            AudioBook model = new AudioBook(id, title, author, cover,
                    published, currentPosition, path);

            audioBookList.add(model);

            updateUI();
        }
    }

    private void updateUI() {
        if (adapter.getCount() == 0) {
            audioGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            audioGridView.setAdapter(adapter);
            adapter.setData(audioBookList);
            adapter.notifyDataSetChanged();
        }
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMS);
        } else {
            exFilePicker.setCanChooseOnlyOneItem(false);
            //exFilePicker.setStartDirectory("/Download");
            exFilePicker.start(this, 1);
            /*Intent addFileIntent = new Intent();
            addFileIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            addFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            addFileIntent.setType("audio/*");*/
            //startActivityForResult(addFileIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1 && data != null) {
                ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
                //Uri uri = data.getData();
                //path = uri.getPath();
                path = result.getPath() + result.getNames().get(0);
                String testPath = result.getPath();
                Log.i(TAG, "path: " + path);

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
                exFilePicker.setCanChooseOnlyOneItem(false);
                //exFilePicker.setStartDirectory("/Download");
                exFilePicker.start(this, 1);
                /*Intent addFileIntent = new Intent();
                addFileIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                addFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                addFileIntent.setType("audio/*");*/

                //startActivityForResult(addFileIntent, 1);
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
            AudioBook audioBookItem = new AudioBook();/*(
                    item.getVolumeInfo().getTitle(),
                    item.getVolumeInfo().getAuthors().get(0),
                    image,
                    item.getVolumeInfo().getPublishedDate(),
                    path
            );*/
            String title = item.getVolumeInfo().getTitle();
            String author = item.getVolumeInfo().getAuthors().get(0);
            String mImage = image;
            String published = item.getVolumeInfo().getPublishedDate();
            String mPath = path;

            audioBookItem.setmName(title);
            audioBookItem.setmAuthor(author);
            audioBookItem.setmImage(mImage);
            audioBookItem.setmPublished(published);
            audioBookItem.setmFilePath(mPath);

            adapter.add(audioBookItem);

            dbHelper.addAudiobook(audioBookItem);
        }
    }

    @Override
    public void OnAudiobookClicked(AudioBook audioBook) {
        audiobookActivity.switchToPlayer(audioBook);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
