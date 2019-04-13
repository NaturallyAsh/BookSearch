package com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.cell;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.MaterialLeanBack;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.MaterialLeanBackSettings;
import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerAttrs;
import com.example.ashleighwilson.booksearch.views.RecyclerLayout.MultiRecyclerLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CellViewHolder extends RecyclerView.ViewHolder {
    final static float scaleEnlarged = 1.2f;
    final static float scaleReduced = 1.0f;

    protected CardView cardView;
    protected boolean enlarged = false;

    protected final MultiRecyclerLayout.MultiAdapter adapter;
    protected final MultiRecyclerLayout.MultiViewHolder viewHolder;
    protected final MultiRecyclerAttrs settings;
    public final int row;

    protected Animator currentAnimator;

    public CellViewHolder(View itemView, int row, MultiRecyclerLayout.MultiAdapter adapter, MultiRecyclerAttrs settings) {
        super(itemView);
        this.row = row;
        this.adapter = adapter;
        this.settings = settings;

        cardView = (CardView) itemView.findViewById(R.id.cellCardView);
        this.viewHolder = adapter.onCreateViewHolder(cardView, row);
        this.viewHolder.row = row;
        cardView.addView(viewHolder.itemView);
    }

    public void enlarge(boolean withAnimation) {
        if (!enlarged && settings.animateCards) {

            if (currentAnimator != null) {
                currentAnimator.cancel();
                currentAnimator = null;
            }

            int duration = withAnimation ? 300 : 0;

            adapter.itemPosition = getAdapterPosition();

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);

            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleX", scaleEnlarged));
            animatorList.add(ObjectAnimator.ofFloat(cardView, "scaleY", scaleEnlarged));

            if (settings.overlapCards) {
                //animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(settings.elevationEnlarged);
                        currentAnimator = null;
                    }
                });
            }

            animatorSet.playTogether(animatorList);
            currentAnimator = animatorSet;
            animatorSet.start();

            enlarged = true;
        }
    }

    public void reduce(boolean withAnimation) {
        if (enlarged && settings.animateCards) {
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

            if (settings.overlapCards) {
                //animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(settings.elevationReduced);
                        currentAnimator = null;
                    }
                });
            }

            animatorSet.playTogether(animatorList);
            currentAnimator = animatorSet;
            animatorSet.start();

            enlarged = false;
        }
    }

    public void newPosition(int position) {
        if (position == 1)
            enlarge(true);
        else
            reduce(true);
    }

    public void onBind() {
        int cell = getAdapterPosition() - CellAdapter.PLACEHOLDER_START_SIZE;
        viewHolder.cell = cell;
        adapter.onBindViewHolder(viewHolder, cell);
    }

    public boolean isEnlarged() {
        return enlarged;
    }

    public void setEnlarged(boolean enlarged) {
        this.enlarged = enlarged;
    }
}
