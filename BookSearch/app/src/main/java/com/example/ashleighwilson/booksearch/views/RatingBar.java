package com.example.ashleighwilson.booksearch.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.ashleighwilson.booksearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingBar extends LinearLayout {
    private float rating;
    private int ratingColor;

    @BindView(R.id.star1)
    RatingStar star1;
    @BindView(R.id.star2)
    RatingStar star2;
    @BindView(R.id.star3)
    RatingStar star3;
    @BindView(R.id.star4)
    RatingStar star4;
    @BindView(R.id.star5)
    RatingStar star5;


    public RatingBar(Context context) {
        super(context);
        init();
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ratingColor = getContext().getResources().getColor(R.color.star_enabled);
    }

    public void setRating(float rating) {
        this.rating = rating;

        if (rating >= 1) {
            star1.setTextColor(ratingColor);
        }
        if (rating >= 2) {
            star2.setTextColor(ratingColor);
        }
        if (rating >= 3) {
            star3.setTextColor(ratingColor);
        }
        if (rating >= 4) {
            star4.setTextColor(ratingColor);
        }
        if (rating >= 5) {
            star5.setTextColor(ratingColor);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
