package com.example.ashleighwilson.booksearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ashleighwilson.booksearch.data.BookDatabase;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>
{
    private static final String TAG = BookAdapter.class.getSimpleName();
    public List<Book> booksInfo;
    private Context context;
    BookDbHelper database;
    boolean starred = false;
    Book book;

    public BookAdapter(Context context, ArrayList<Book> booksInfo) {
        this.booksInfo = booksInfo;
        this.context = context;
        this.database = new BookDbHelper(context);
    }

    //Easy access to the context object in the RV
    public Context getContext() {
        return context;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.book_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {

        Book currentBook = booksInfo.get(position);

        holder.titleView.setText(currentBook.getmTitle());
        holder.authorView.setText(currentBook.getmAuthors());
        holder.descriptionView.setText(currentBook.getmDescription());
        String bookCoverUrl = currentBook.getmBookCover();
        Picasso.get().load(Uri.parse(bookCoverUrl)).error(R.drawable.no_book_cover).into(holder.bookCover);

        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!starred) {
                    Drawable favFilled = ResourcesCompat.getDrawable(v.getResources(),
                            R.drawable.ic_action_favorite, null);
                    holder.toggleButton.setBackground(favFilled);
                    holder.toggleButton.setTag("filled");

                    //database.addBook(currentBook);
                    database.addLongBook(booksInfo.get(position));
                } else {
                    Drawable favEmpty = ResourcesCompat.getDrawable(v.getResources(),
                            R.drawable.ic_action_favorite_border, null);
                    holder.toggleButton.setBackground(favEmpty);
                    holder.toggleButton.setTag("empty");

                    database.removeBook(booksInfo.get(position));
                }
            }
        });

    }

    public class BookViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView bookCover;
        TextView titleView;
        TextView authorView;
        TextView descriptionView;
        ToggleButton toggleButton;
        boolean starred = false;


        public BookViewHolder(View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.cardView);
            this.bookCover = itemView.findViewById(R.id.book_image);
            this.titleView = itemView.findViewById(R.id.book_title);
            this.authorView = itemView.findViewById(R.id.book_author);
            this.descriptionView = itemView.findViewById(R.id.book_description);
            this.toggleButton = itemView.findViewById(R.id.toggle_button);
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!starred) {
                        Drawable favFilled = ResourcesCompat.getDrawable(v.getResources(),
                                R.drawable.ic_action_favorite, null);
                        toggleButton.setBackground(favFilled);
                        toggleButton.setTag("filled");

                        database.addBook(booksInfo.get(getAdapterPosition()));
                    } else {
                        Drawable favEmpty = ResourcesCompat.getDrawable(v.getResources(),
                                R.drawable.ic_action_favorite_border, null);
                        toggleButton.setBackground(favEmpty);
                        toggleButton.setTag("empty");

                        database.removeBook(booksInfo.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return booksInfo.size();
    }

    public void setData(List<Book> books) {
        if (books != null && !books.isEmpty()) {
            this.booksInfo.clear();
            this.booksInfo.addAll(books);
            notifyDataSetChanged();
        } else {
            this.booksInfo.clear();
        }
    }

    public Book getBook(int position) {
        return booksInfo.get(position);
    }
}



