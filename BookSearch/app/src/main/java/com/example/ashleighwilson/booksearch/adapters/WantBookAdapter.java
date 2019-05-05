package com.example.ashleighwilson.booksearch.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WantBookAdapter extends RecyclerView.Adapter<WantBookAdapter.ViewHolder> {

    private static final String TAG = WantBookAdapter.class.getSimpleName();

    Context mContext;
    private ArrayList<Review> reviewList;
    private int limit = 40;
    private List<Item> newItem = new ArrayList<>();
    private OnWantedClickedListener listener;
    public static int itemPosition = 0;
    public static final int PLACEHOLDER_START_SIZE = 1;
    public static final int PLACEHOLDER_END_SIZE = 1;


    public WantBookAdapter(Context context, ArrayList<Review> reviewArrayList, OnWantedClickedListener listener) {
        this.mContext = context;
        this.reviewList = reviewArrayList;
        this.listener = listener;
    }

    public interface OnWantedClickedListener {
        void OnWantedClicke(Review review, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.want_book_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review currentReviews = reviewList.get(position);

        if (position == 1) {
            Log.i(TAG, "position reduced: " + position);
            holder.setEnlarged(false);
        } else {
            Log.i(TAG, "position enlarged: " + position);
            holder.setEnlarged(true);
        }
        holder.newPosition(position - PLACEHOLDER_START_SIZE);

        String noPhoto = "noPhoto";
        if (currentReviews.getBook().getImageUrl() != null ) {
            Glide.with(mContext)
                    .load(currentReviews.getBook().getImageUrl())
                    .into(holder.wantIV);
        }
        if (newItem != null) {
            Item item;

            for (int i = 0; i < newItem.size(); i++) {
                item = newItem.get(i);
                String name = item.getVolumeInfo().getTitle();
                if (currentReviews.getBook().getTitle().toLowerCase().contains(name.toLowerCase()) &&
                        currentReviews.getBook().getImageUrl().toLowerCase().indexOf(noPhoto.toLowerCase()) >= 0) {
                    String identifier = "";

                    for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                        identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                    }
                    //Log.i(TAG, "item image url: " + currentReviews.getBook().getAltBookCover(identifier));
                    currentReviews.getBook().setImageUrl(currentReviews.getBook().getAltBookCover(identifier));
                    Glide.with(mContext)
                            .load(currentReviews.getBook().getImageUrl())
                            .into(holder.wantIV);
                    if (item.getVolumeInfo().getImageLinks() != null) {
                        //Log.i(TAG, "image links not null: " + item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                        currentReviews.getBook().setImageUrl(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                        Glide.with(mContext)
                                .load(currentReviews.getBook().getImageUrl())
                                .into(holder.wantIV);
                    }
                }
            }
        }

        holder.titleTV.setText(currentReviews.getBook().getTitle());
        holder.authorTV.setText(currentReviews.getBook().getAuthor().getAuthor().get(0).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnWantedClicke(currentReviews, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reviewList.size() > limit) {
            return limit;
        } else {
            return reviewList.size();
        }
    }

    public int getEnlargedItemPosition(int position){
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.want_cardview)
        CardView cardView;
        @BindView(R.id.want_book_image)
        ImageView wantIV;
        @BindView(R.id.want_book_title)
        TextView titleTV;
        @BindView(R.id.want_book_author)
        TextView authorTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        final static float scaleEnlarged = 1.2f;
        final static float scaleReduced = 1.0f;

        protected boolean enlarged = false;

        public int position;

        public Animator currentAnimator;

        public void enlarge(boolean withAnimation) {
            if (!enlarged) {

                if (currentAnimator != null) {
                    currentAnimator.cancel();
                    currentAnimator = null;
                }

                int duration = withAnimation ? 300 : 0;

                itemPosition = getAdapterPosition();

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(duration);

                List<Animator> animatorList = new ArrayList<>();
                animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleX", scaleEnlarged));
                animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleY", scaleEnlarged));

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(8);
                        currentAnimator = null;
                    }
                });

                animatorSet.playTogether(animatorList);
                currentAnimator = animatorSet;
                animatorSet.start();

                enlarged = true;
            }
        }

        public void reduce(boolean withAnimation) {
            if (enlarged) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                    currentAnimator = null;
                }

                int duration = withAnimation ? 300 : 0;

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(duration);

                List<Animator> animatorList = new ArrayList<>();
                animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleX", scaleReduced));
                animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleY", scaleReduced));

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(5);
                        currentAnimator = null;
                    }
                });

                animatorSet.playTogether(animatorList);
                currentAnimator = animatorSet;
                animatorSet.start();

                enlarged = false;
            }
        }

        public void setEnlarged(boolean enlarged) {
            this.enlarged = enlarged;
        }

        public void newPosition(int position) {
            if (position == 1)
                enlarge(true);
            else
                reduce(true);
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
