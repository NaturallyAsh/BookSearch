package com.example.ashleighwilson.booksearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.ashleighwilson.booksearch.adapters.ReaderAdapter;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.data.ImageUtils;
import com.example.ashleighwilson.booksearch.models.Reader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

public class ReaderListFragment extends Fragment {

    private static final String TAG = ReaderListFragment.class.getSimpleName();

    public static final String EPUB_LOCATION = "epub_location";
    private ReaderActivity readerActivity;
    private BookDbHelper dbHelper;
    private RecyclerView recyclerView;
    private ReaderAdapter adapter;
    private LinearLayoutManager manager;
    private TextView emptyView;

    private FloatingActionButton addReaderButton;

    public ReaderListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readerActivity = (ReaderActivity) getActivity();
        dbHelper = new BookDbHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.reader_listview_fragment, container, false);

        addReaderButton = rootView.findViewById(R.id.reader_add_button);
        addReaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileViewer();
            }
        });
        recyclerView = rootView.findViewById(R.id.reader_recyclerview);
        emptyView = rootView.findViewById(R.id.empty_reader_view);
        manager = new LinearLayoutManager(getActivity());
        adapter = new ReaderAdapter(getContext(), readerActivity);
        recyclerView.setLayoutManager(manager);

        updateUI();

        return rootView;
    }

    public void OpenFileViewer() {
        ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.setCanChooseOnlyOneItem(true);
        exFilePicker.setShowOnlyExtensions("epub");
        exFilePicker.setQuitButtonEnabled(true);
        exFilePicker.setHideHiddenFilesEnabled(true);
        exFilePicker.setNewFolderButtonDisabled(true);
        exFilePicker.start(this, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if (requestCode == 1) {
            ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
            if (result != null && result.getCount() > 0) {
                String location = result.getPath() + result.getNames().get(0);

                //readerActivity.switchToReader(location);
                try{
                    InputStream epubIS = new BufferedInputStream(new FileInputStream(location));
                    Book book = (new EpubReader()).readEpub(epubIS);
                    Reader reader = new Reader();

                    byte[] bytes = book.getCoverImage().getData();

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                            bytes.length);

                    if (!dbHelper.hasPath(location)) {
                        reader.setmTitle(book.getTitle());
                        //reader.setmAuthor(book.getMetadata().);
                        reader.setPathLocation(location);
                        reader.setmCoverImage(bitmap);
                        dbHelper.addReader(reader);
                        readerActivity.switchToReader(reader);
                    } else {
                        Toast.makeText(getContext(), "Book already in library", Toast.LENGTH_SHORT).show();
                    }

                    //Log.i(TAG, "cover: " + book.getCoverImage().getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateUI() {
        if (adapter.getItemCount() < 1) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
