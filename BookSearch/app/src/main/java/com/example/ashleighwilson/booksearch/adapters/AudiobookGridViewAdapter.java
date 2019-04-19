package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.models.AudioBook;

import java.util.List;

public class AudiobookGridViewAdapter extends BaseAdapter {

    private static final String TAG = AudiobookGridViewAdapter.class.getSimpleName();

    private List<AudioBook> audioBookList;
    private Context mContext;
    private OnAudiobookClickListener listener;

    public AudiobookGridViewAdapter(Context context, List<AudioBook> audioBooks, OnAudiobookClickListener listener) {
        this.audioBookList = audioBooks;
        this.mContext = context;
        this.listener = listener;
    }

    public interface OnAudiobookClickListener {
        void OnAudiobookClicked(AudioBook audioBook);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AudioBook currentBook = audioBookList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.audiobook_item, null);
        }
        ImageView coverIV = convertView.findViewById(R.id.audiobook_cover_image);
        TextView title = convertView.findViewById(R.id.audiobook_title);
        TextView author = convertView.findViewById(R.id.audiobook_author);

        Glide.with(mContext)
                .load(currentBook.getmImage())
                .into(coverIV);
        title.setText(currentBook.getmName());
        author.setText(currentBook.getmAuthor());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnAudiobookClicked(currentBook);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return audioBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void add(AudioBook audioBook) {
        audioBookList.clear();
        audioBookList.add(audioBook);
        notifyDataSetChanged();
    }

    public void setData(List<AudioBook> data) {
        this.audioBookList = data;
        notifyDataSetChanged();
    }
}
