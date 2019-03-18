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
import com.example.ashleighwilson.booksearch.models.UserBook;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarBookAdapter extends RecyclerView.Adapter<SimilarBookAdapter.ViewHolder> {

    private static final String TAG = SimilarBookAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<UserBook> bookList;
    private OnSimilarBookClickedListener listener;
    private List<Item> newItem = new ArrayList<>();

    public SimilarBookAdapter(Context context, ArrayList<UserBook> userBooks, OnSimilarBookClickedListener
                              listener) {
        this.bookList = userBooks;
        this.mContext = context;
        this.listener = listener;
    }

    public interface OnSimilarBookClickedListener {
        void OnSimilarBookClicked(UserBook book, int position);
    }

    @NonNull
    @Override
    public SimilarBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.similar_book_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarBookAdapter.ViewHolder holder, int position) {
        UserBook currentSimilarBooks = bookList.get(position);

        String noPhoto = "noPhoto";
        if (currentSimilarBooks.getImageUrl() != null) {
            Glide.with(mContext)
                    .load(currentSimilarBooks.getImageUrl())
                    .into(holder.similarImage);
        }
        if (newItem != null) {
            Item item;
            for (int i = 0; i < newItem.size(); i++) {
                item = newItem.get(i);
                String itemTitle = item.getVolumeInfo().getTitle();
                if (currentSimilarBooks.getImageUrl().toLowerCase().indexOf(noPhoto
                        .toLowerCase()) >= 0) {
                    String identifier = "";
                    for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                        identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                    }
                    currentSimilarBooks.setImageUrl(currentSimilarBooks.getAltBookCover(identifier));
                    Glide.with(mContext)
                            .load(currentSimilarBooks.getImageUrl())
                            .into(holder.similarImage);
                    if (item.getVolumeInfo().getImageLinks() != null) {
                        currentSimilarBooks.setImageUrl(item.getVolumeInfo()
                                .getImageLinks().getSmallThumbnail());
                        Glide.with(mContext)
                                .load(currentSimilarBooks.getImageUrl())
                                .into(holder.similarImage);
                    }
                }
            }
        }


        holder.titleTv.setText(currentSimilarBooks.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSimilarBookClicked(currentSimilarBooks, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.similar_book_image)
        ImageView similarImage;
        @BindView(R.id.similar_book_title)
        TextView titleTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(List<UserBook> userBooks) {
        bookList.clear();
        bookList.addAll(userBooks);
        notifyDataSetChanged();
    }

    public void newImage(Item item) {
        newItem.add(item);
    }
}
