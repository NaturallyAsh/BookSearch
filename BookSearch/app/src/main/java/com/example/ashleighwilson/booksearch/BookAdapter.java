package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book>
{
    private static final String TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Context context, ArrayList<Book> booksInfo)
    {
        super(context, 0, booksInfo);
    }

    private static class ViewHolder
    {
        ImageView bookCover;
        TextView titleView;
        TextView authorView;
        TextView descriptionView;
        View textContainer;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,
                    parent, false);

            viewHolder.bookCover = convertView.findViewById(R.id.book_image);
            viewHolder.titleView = convertView.findViewById(R.id.book_title);
            viewHolder.authorView = convertView.findViewById(R.id.book_author);
            viewHolder.descriptionView = convertView.findViewById(R.id.book_description);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book currentBook = getItem(position);

        viewHolder.textContainer = convertView.findViewById(R.id.list_bucket);

        viewHolder.titleView.setText(currentBook.getmTitle());

        viewHolder.authorView.setText(currentBook.getmAuthors());

        viewHolder.descriptionView.setText(currentBook.getmDescription());

        String bookCoverUrl = currentBook.getmBookCover();

        Picasso.get().load(Uri.parse(bookCoverUrl)).error(R.drawable.no_book_cover).into(viewHolder.bookCover);

        return convertView;
    }
}
