package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.models.BestBook;
import com.example.ashleighwilson.booksearch.models.Book;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder>
{
    public List<BestBook> booksInfo;
    public BookDbHelper database;
    public Bitmap bp;
    Context context;
    boolean starred = false;
    static ClickListener clickListener;

    public FavAdapter(Context context, ArrayList<BestBook> bookArrayList)
    {
        this.context = context;
        this.booksInfo = bookArrayList;
        this.database = BookDbHelper.getInstance();
    }

    @Override
    public FavAdapter.FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavAdapter.FavViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fav_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final FavAdapter.FavViewHolder holder, final int position) {

        final BestBook currentBook = booksInfo.get(position);

        holder.titleView.setText(currentBook.getDb_title());
        holder.authorView.setText(currentBook.getDb_author());
        Glide.with(context)
                .load(currentBook.getDb_image())
                .into(holder.bookCover);

    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        ImageView bookCover;
        TextView titleView;
        TextView authorView;
        TextView descriptionView;
        TextView infoLink;
        ToggleButton toggleButton;
        //boolean starred = false;


        public FavViewHolder(View itemView) {
            super(itemView);
            this.bookCover = itemView.findViewById(R.id.book_image2);
            this.titleView = itemView.findViewById(R.id.book_title2);
            this.authorView = itemView.findViewById(R.id.book_author2);
            this.descriptionView = itemView.findViewById(R.id.book_description2);
            this.infoLink = itemView.findViewById(R.id.info2);
            this.toggleButton = itemView.findViewById(R.id.toggle_button2);

            itemView.setOnClickListener(this);
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database.removeBook(booksInfo.get(getAdapterPosition()));
                    booksInfo.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), booksInfo.size());

                    /*if (!starred) {
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
                    } */
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.itemClicked(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount()
    {
        return booksInfo.size();
    }

    public boolean checkFavoriteItem(Book checkStarredItem)
    {
        boolean check = false;

        ArrayList<BestBook> itemsInDB = database.getAllBooks();

        if (itemsInDB != null)
        {
            for (BestBook book : itemsInDB)
            {
                if (book.getDb_author().equals(checkStarredItem.getmAuthors()))
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public interface ClickListener
    {
        void itemClicked(View view, int position);
    }

    public void setListener(ClickListener clicked)
    {
        FavAdapter.clickListener = clicked;
    }
}
