package com.example.ashleighwilson.booksearch.views.RecyclerLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.ashleighwilson.booksearch.R;

public class MultiRecyclerAttrs {
    public Integer titleColor;
    public int titleSize;
    public boolean animateCards;
    public boolean overlapCards;
    public int elevationReduced;
    public int elevationEnlarged;

    public Integer backgroundId;
    public Float backgroundOverlay;
    public Integer backgroundOverlayColor;

    public Integer paddingTop;
    public Integer paddingBottom;
    public Integer paddingLeft;
    public Integer paddingRight;

    public Integer lineSpacing;

    protected void attainAttributes(Context context, AttributeSet attrs) {
        try {
            //Make sure the name of the styleable is the same name as the class
            //rebuild project if it doesn't work at first
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MultiRecyclerLayout);
            {
                if (styledAttrs.hasValue(R.styleable.MultiRecyclerLayout_multi_titleColor))
                    titleColor = styledAttrs.getColor(R.styleable.MultiRecyclerLayout_multi_titleColor, -1);
                titleSize = styledAttrs.getDimensionPixelSize(R.styleable.MultiRecyclerLayout_multi_titleSize, -1);
                animateCards = styledAttrs.getBoolean(R.styleable.MultiRecyclerLayout_multi_animateCards, true);
                overlapCards = styledAttrs.getBoolean(R.styleable.MultiRecyclerLayout_multi_overlapCards, true);
                elevationEnlarged = styledAttrs.getInteger(R.styleable.MultiRecyclerLayout_multi_cardElevationEnlarged, 8);
                elevationReduced = styledAttrs.getInteger(R.styleable.MultiRecyclerLayout_multi_cardElevationReduced, 5);
                backgroundId = styledAttrs.getResourceId(R.styleable.MultiRecyclerLayout_multi_background, -1);
                lineSpacing = styledAttrs.getDimensionPixelOffset(R.styleable.MultiRecyclerLayout_multi_lineSpacing, -1);
                if (styledAttrs.hasValue(R.styleable.MultiRecyclerLayout_multi_paddingTop))
                    paddingTop = styledAttrs.getDimensionPixelOffset(R.styleable.MultiRecyclerLayout_multi_paddingTop, -1);
                if (styledAttrs.hasValue(R.styleable.MultiRecyclerLayout_multi_paddingBottom))
                    paddingBottom = styledAttrs.getDimensionPixelOffset(R.styleable.MultiRecyclerLayout_multi_paddingBottom, -1);
                if (styledAttrs.hasValue(R.styleable.MultiRecyclerLayout_multi_paddingLeft))
                    paddingLeft = styledAttrs.getDimensionPixelOffset(R.styleable.MultiRecyclerLayout_multi_paddingLeft, -1);
                if (styledAttrs.hasValue(R.styleable.MultiRecyclerLayout_multi_paddingRight))
                    paddingRight = styledAttrs.getDimensionPixelOffset(R.styleable.MultiRecyclerLayout_multi_paddingRight, -1);
            }
            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
