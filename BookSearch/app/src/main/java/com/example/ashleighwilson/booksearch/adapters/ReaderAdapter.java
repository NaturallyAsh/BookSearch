package com.example.ashleighwilson.booksearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.R;
import com.example.ashleighwilson.booksearch.ReaderActivity;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.models.Reader;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ViewHolder> {

    private static final String TAG = ReaderAdapter.class.getSimpleName();

    private Context mContext;
    private BookDbHelper dbHelper;
    private Reader readerModel;
    private ReaderActivity readerActivity;
    public static String ARG_ITEM = "reader_item";

    public ReaderAdapter(Context context, ReaderActivity activity) {
        this.mContext = context;
        dbHelper = BookDbHelper.getInstance();
        this.readerActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.reader_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        readerModel = getItem(position);

        holder.titleTV.setText(readerModel.getmTitle());
        holder.authorTV.setText(readerModel.getmAuthor());
        holder.coverIV.setImageBitmap(readerModel.getmCoverImage());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readerActivity.switchToReader(readerModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbHelper.getReaderCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView titleTV;
        TextView authorTV;
        ImageView coverIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            titleTV = view.findViewById(R.id.reader_title_item);
            authorTV = view.findViewById(R.id.reader_author_item);
            coverIV = view.findViewById(R.id.reader_cover_item);
        }
    }

    public Reader getItem(int position) {
        return dbHelper.getReaderAt(position);
    }

    public void deleteReader(int position) {
        File file = new File(getItem(position).getPathLocation());
        file.delete();

        dbHelper.removeReader(getItem(position).getmId());
        notifyItemRemoved(position);
    }
}
