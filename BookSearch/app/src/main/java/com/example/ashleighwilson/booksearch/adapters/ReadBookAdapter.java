package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Review;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadBookAdapter extends RecyclerView.Adapter<ReadBookAdapter.ViewHolder> {

    private static final String TAG = ReadBookAdapter.class.getSimpleName();

    Context mContext;
    private ArrayList<Review> reviewList;
    private int limit = 10;
    private List<Item> newItem = new ArrayList<>();

    public ReadBookAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.mContext = context;
        this.reviewList = reviewArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.read_book_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review currentReviews = reviewList.get(position);

        if (currentReviews.getBook().getImageUrl() != null) {
            Glide.with(mContext)
                    .load(currentReviews.getBook().getImageUrl())
                    .into(holder.readImage);
        }
        if (newItem != null) {
            Item item;
            for (int i = 0; i < newItem.size(); i++) {
                item = newItem.get(i);
                String name = item.getVolumeInfo().getTitle();
                if (currentReviews.getBook().getTitle().toLowerCase().contains(name.toLowerCase())) {
                    currentReviews.getBook().setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                    Glide.with(mContext)
                            .load(currentReviews.getBook().getImageUrl())
                            .into(holder.readImage);
                }
            }
        }
        holder.titleTV.setText(currentReviews.getBook().getTitle());
        holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().getName());
    }

    @Override
    public int getItemCount() {
        if (reviewList.size() > limit) {
            return limit;
        } else {
            return reviewList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.read_book_image)
        ImageView readImage;
        @BindView(R.id.read_book_title)
        TextView titleTV;
        @BindView(R.id.read_book_author)
        TextView authorTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(List<Review> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
        notifyDataSetChanged();
    }

    public void newImage(Item item) {
        newItem.add(item);
    }
}
