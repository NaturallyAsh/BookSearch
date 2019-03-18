package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.models.Item;
import com.example.ashleighwilson.booksearch.models.Series;
import com.example.ashleighwilson.booksearch.models.SeriesWork;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SeriesBookAdapter extends RecyclerView.Adapter<SeriesBookAdapter.ViewHolder> {

    private static final String TAG = SeriesBookAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SeriesWork> seriesList;
    private OnSeriesClickedListener listener;
    private List<Item> newItem = new ArrayList<>();

    public SeriesBookAdapter(Context context, ArrayList<SeriesWork> seriesArrayList, OnSeriesClickedListener
                             listener) {
        this.mContext = context;
        this.seriesList = seriesArrayList;
        this.listener = listener;
    }

    public interface OnSeriesClickedListener {
        void OnSeriesClicked(SeriesWork series, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.series_book_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeriesWork currentSeries = seriesList.get(position);

        String noPhoto = "noPhoto";
        if (currentSeries.getWork().getBestBook().getImageUrl() != null) {
            Glide.with(mContext)
                    .load(currentSeries.getWork().getBestBook().getImageUrl())
                    .into(holder.seriesImage);
        }
        if (newItem != null) {
            Item item;
            for (int i = 0; i < newItem.size(); i++) {
                item = newItem.get(i);
                String itemTitle = item.getVolumeInfo().getTitle();
                if (currentSeries.getWork().getBestBook().getImageUrl().toLowerCase().indexOf(noPhoto
                    .toLowerCase()) >= 0) {
                    String identifier = "";
                    for (int j = 0; j < item.getVolumeInfo().getIndustryIdentifiers().size(); j++) {
                        identifier = item.getVolumeInfo().getIndustryIdentifiers().get(j).getIdentifier();
                    }
                    currentSeries.getWork().getBestBook().setImageUrl(currentSeries.getWork()
                        .getBestBook().getAltBookCover(identifier));
                    Glide.with(mContext)
                            .load(currentSeries.getWork().getBestBook().getImageUrl())
                            .into(holder.seriesImage);
                    if (item.getVolumeInfo().getImageLinks() != null) {
                        currentSeries.getWork().getBestBook().setImageUrl(item.getVolumeInfo()
                        .getImageLinks().getSmallThumbnail());
                        Glide.with(mContext)
                                .load(currentSeries.getWork().getBestBook().getImageUrl())
                                .into(holder.seriesImage);
                    }
                }
            }
        }


        holder.titleTv.setText(currentSeries.getWork().getBestBook().getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSeriesClicked(currentSeries, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.series_book_image)
        ImageView seriesImage;
        @BindView(R.id.series_book_title)
        TextView titleTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(List<SeriesWork> series) {
        seriesList.clear();
        seriesList.addAll(series);
        notifyDataSetChanged();
    }

    public void newImage(Item item) {
        newItem.add(item);
    }
}
