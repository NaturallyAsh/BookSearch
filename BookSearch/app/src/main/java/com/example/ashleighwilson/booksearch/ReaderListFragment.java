package com.example.ashleighwilson.booksearch;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

public class ReaderListFragment extends Fragment {

    private static final String TAG = ReaderListFragment.class.getSimpleName();

    public static final String EPUB_LOCATION = "epub_location";
    private ReaderActivity readerActivity;

    private FloatingActionButton addReaderButton;

    public ReaderListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readerActivity = (ReaderActivity) getActivity();
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

                Log.i(TAG, "location: " + location);
                readerActivity.switchToReader(location);
            }
        }
    }
}
