package com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.line;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.MaterialLeanBack;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.MaterialLeanBackSettings;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.OnItemClickListenerWrapper;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.PlaceHolderViewHolder;
import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerAttrs;
import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LineAdapter extends RecyclerView.Adapter {
    public static final int PLACEHOLDER_START = -2000;
    public static final int PLACEHOLDER_START_SIZE = 1;
    public static final int PLACEHOLDER_END_SIZE = 1;
    public static final int PLACEHOLDER_END = -2001;
    public static final int CELL = -2002;

    protected final OnItemClickListenerWrapper onItemClickListenerWrapper;
    protected final MultiRecyclerAttrs settings;
    protected final MultiRecyclerLayout.MultiAdapter adapter;
    protected final MultiRecyclerLayout.Customizer customizer;

    public LineAdapter(@NonNull MultiRecyclerAttrs settings, @NonNull MultiRecyclerLayout.MultiAdapter adapter,
                       MultiRecyclerLayout.Customizer customizer, OnItemClickListenerWrapper onItemClickListenerWrapper) {
        this.settings = settings;
        this.adapter = adapter;
        this.customizer = customizer;
        this.onItemClickListenerWrapper = onItemClickListenerWrapper;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return PLACEHOLDER_START;
        else if (position == getItemCount() - 1)
            return PLACEHOLDER_END;
        else
            return CELL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;
        switch (type) {
            case PLACEHOLDER_START:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, false, settings.paddingTop);
            case PLACEHOLDER_END:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_placeholder, viewGroup, false);
                return new PlaceHolderViewHolder(view, false, settings.paddingBottom);
            case CELL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_row, viewGroup, false);
                return new LineViewHolder(view, adapter, settings, customizer, onItemClickListenerWrapper);
            default:

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int row) {
        if (viewHolder instanceof LineViewHolder)
            ((LineViewHolder) viewHolder).onBind(row - PLACEHOLDER_START_SIZE);

    }

    @Override
    public int getItemCount() {
        return adapter.getLineCount() + PLACEHOLDER_START_SIZE + PLACEHOLDER_END_SIZE;
    }

    public void notifyLineDataChanged() {
        notifyDataSetChanged();
    }
}
