package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
import com.example.ashleighwilson.booksearch.models.Search;
import com.example.ashleighwilson.booksearch.models.Work;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder>
{
    private static final String TAG = SearchBookAdapter.class.getSimpleName();
    public ArrayList<BestBook> searches;
    private Context context;
    BookDbHelper database;
    boolean starred = false;
    OnSearchItemClickedListener listener;

    public SearchBookAdapter(Context context, ArrayList<BestBook> searches,
                             OnSearchItemClickedListener listener) {
        this.searches = searches;
        this.context = context;
        this.listener = listener;
        this.database = BookDbHelper.getInstance();
    }

    public interface OnSearchItemClickedListener {
        void OnSearchItemClicked(String id, String title);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.book_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final BestBook currentSearch = searches.get(position);

        holder.titleView.setText(currentSearch.getTitle());
        holder.authorView.setText(currentSearch.getAuthor().getName());
        Glide.with(context)
                .load(currentSearch.getImageUrl())
                .into(holder.bookCover);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSearchItemClicked(currentSearch.getId(), currentSearch.getTitle());
            }
        });

        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!starred) {
                    Drawable favFilled = ResourcesCompat.getDrawable(v.getResources(),
                            R.drawable.ic_action_favorite, null);
                    holder.toggleButton.setBackground(favFilled);
                    holder.toggleButton.setTag("filled");

                    database.addBook(searches.get(position));
                } else {
                    Drawable favEmpty = ResourcesCompat.getDrawable(v.getResources(),
                            R.drawable.ic_action_favorite_border, null);
                    holder.toggleButton.setBackground(favEmpty);
                    holder.toggleButton.setTag("empty");

                    database.removeBook(searches.get(position));
                }
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.book_image)
        ImageView bookCover;
        @BindView(R.id.book_title)
        TextView titleView;
        @BindView(R.id.book_author)
        TextView authorView;
        @BindView(R.id.toggle_button)
        ToggleButton toggleButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount()
    {
        return searches.size();
    }
    public void add(List<BestBook> bestBook) {
        searches.clear();
        searches.addAll(bestBook);
        notifyDataSetChanged();
    }

    private byte[] convertToBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }
}



