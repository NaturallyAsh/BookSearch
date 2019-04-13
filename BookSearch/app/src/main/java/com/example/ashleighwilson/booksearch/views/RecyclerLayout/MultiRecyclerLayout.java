package com.example.ashleighwilson.booksearch.views.RecyclerLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.OnItemClickListenerWrapper;
import com.example.ashleighwilson.booksearch.views.HorizontalOverlayLayout.line.LineAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultiRecyclerLayout extends FrameLayout {

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

    private RecyclerView RV1;
    private RecyclerView RV2;
    private RecyclerView RV3;
    private TextView RV_title1;
    private TextView RV_title2;
    private TextView RV_title3;

    private LineAdapter adapter1;
    private LineAdapter adapter2;
    private LineAdapter adapter3;
    private MultiAdapter mainAdapter1;
    private MultiAdapter mainAdapter2;
    private MultiAdapter mainAdapter3;
    private Customizer customizer1;
    private Customizer customizer2;
    private Customizer customizer3;
    private OnItemClickListenerWrapper clickListenerWrapper1 = new OnItemClickListenerWrapper();
    private OnItemClickListenerWrapper clickListenerWrapper2 = new OnItemClickListenerWrapper();
    private OnItemClickListenerWrapper clickListenerWrapper3 = new OnItemClickListenerWrapper();
    private MultiRecyclerAttrs attributes = new MultiRecyclerAttrs();

    public MultiRecyclerLayout(@NonNull Context context) {
        super(context);
    }

    public MultiRecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attributes.attainAttributes(context, attrs);
    }

    public MultiRecyclerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attributes.attainAttributes(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addView(LayoutInflater.from(getContext()).inflate(R.layout.multi_layout,
                this, false));

        RV_title1 = findViewById(R.id.multi_RV1_title);
        RV_title2 = findViewById(R.id.multi_RV2_title);
        RV_title3 = findViewById(R.id.multi_RV3_title);

        RV1 = findViewById(R.id.multi_RV1);
        RV2 = findViewById(R.id.multi_RV2);
        RV3 = findViewById(R.id.multi_RV3);

        RV1.setLayoutManager(new LinearLayoutManager(getContext()));
        RV2.setLayoutManager(new LinearLayoutManager(getContext()));
        RV3.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public MultiAdapter getAdapter1() {
        return this.mainAdapter1;
    }

    public MultiAdapter getAdapter2() {
        return this.mainAdapter2;
    }

    public MultiAdapter getAdapter3() {
        return this.mainAdapter3;
    }

    public void setAdapter1(MultiAdapter adapter) {
        this.mainAdapter1 = adapter;
        adapter1 = new LineAdapter(attributes, adapter, customizer1, clickListenerWrapper1);
        RV1.setAdapter(adapter1);
    }

    public void setAdapter2(MultiAdapter adapter) {
        this.mainAdapter2 = adapter;
        adapter2 = new LineAdapter(attributes, adapter, customizer2, clickListenerWrapper2);
        RV2.setAdapter(adapter2);
    }

    public void setAdapter3(MultiAdapter adapter) {
        this.mainAdapter3 = adapter;
        adapter3 = new LineAdapter(attributes, adapter, customizer3, clickListenerWrapper3);
        RV3.setAdapter(adapter3);
    }

    public void notifyDataChanged1() {
        adapter1.notifyDataSetChanged();
    }

    public void notifyDataChanged2() {
        adapter2.notifyDataSetChanged();
    }

    public void notifyDataChanged3() {
        adapter3.notifyDataSetChanged();
    }

    public interface Customizer {
        void customizeTitle(TextView textView);
    }

    public interface OnItemClickListener {
        void onTitleClicked(int row, String text);

        void onItemClicked(int row, int column);
    }

    public void setOnItemClickListener1(OnItemClickListener onItemClickListener) {
        this.clickListenerWrapper1.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemClickListener2(OnItemClickListener onItemClickListener) {
        this.clickListenerWrapper2.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemClickListener3(OnItemClickListener onItemClickListener) {
        this.clickListenerWrapper3.setOnItemClickListener(onItemClickListener);
    }

    public static abstract class MultiAdapter<VH extends MultiViewHolder> {
        public int itemPosition=0;

        public int getLineCount() {
            return 0;
        }

        public int getCellsCount(int row) {
            return 0;
        }

        public VH onCreateViewHolder(ViewGroup viewGroup, int row) {
            return null;
        }

        public void onBindViewHolder(VH viewHolder, int i) {
        }
        public String getTitleForRow(int row) {
            return null;
        }

        public boolean hasRowTitle(int row) {
            return true;
        }

        public int getEnlargedItemPosition(int position){
            return position;
        }

    }

    public static class MultiViewHolder {
        public int row;
        public int cell;
        public View itemView;

        public MultiViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
