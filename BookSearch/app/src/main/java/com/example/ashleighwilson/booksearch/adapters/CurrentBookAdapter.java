package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentBookAdapter extends RecyclerView.Adapter<CurrentBookAdapter.ViewHolder> {

    private static final String TAG = CurrentBookAdapter.class.getSimpleName();

    private ArrayList<Review> reviewList;
    Context mContext;
    private CurrentBookClickListener listener;


    public CurrentBookAdapter(Context context, ArrayList<Review> reviewsArrayList,
                              CurrentBookClickListener listener) {
        this.mContext = context;
        this.reviewList = reviewsArrayList;
        this.listener = listener;
    }

    public interface CurrentBookClickListener {
        void OnCurrentBookClicked(Review review, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.current_book_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review currentReviews = reviewList.get(position);

        if (currentReviews.getBook().getImageUrl() != null) {
            Glide.with(mContext)
                    .load(currentReviews.getBook().getImageUrl())
                    .into(holder.bookIV);
        }
        holder.titleTV.setText(currentReviews.getBook().getTitle());
        holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnCurrentBookClicked(currentReviews, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        @BindView(R.id.current_book_image)
        ImageView bookIV;
        @BindView(R.id.current_book_title)
        TextView titleTV;
        @BindView(R.id.current_book_author)
        TextView authorTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(List<Review> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
        notifyDataSetChanged();
    }
}
