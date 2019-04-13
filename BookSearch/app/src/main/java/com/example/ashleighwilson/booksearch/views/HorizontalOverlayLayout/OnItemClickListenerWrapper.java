package com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout;

import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerLayout;

import androidx.annotation.Nullable;

public class OnItemClickListenerWrapper {
    private MultiRecyclerLayout.OnItemClickListener onItemClickListener;

    @Nullable
    public MultiRecyclerLayout.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(MultiRecyclerLayout.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
