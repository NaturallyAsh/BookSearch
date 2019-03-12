package com.example.ashleighwilson.booksearch.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.joanzapata.android.iconify.Iconify;

import androidx.appcompat.widget.AppCompatTextView;

public class RatingStar extends AppCompatTextView {
    public RatingStar(Context context) {
        super(context);
        init();
    }

    public RatingStar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode())
            Iconify.addIcons(this);
        else
            this.setText(this.getText());
        //Iconify.addIcons(this);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(Iconify.compute(text), type);
    }
}
