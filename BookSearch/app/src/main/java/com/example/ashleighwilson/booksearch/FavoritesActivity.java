package com.example.ashleighwilson.booksearch;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ashleighwilson.booksearch.adapters.FavAdapter;
import com.example.ashleighwilson.booksearch.data.BookDbHelper;
import com.example.ashleighwilson.booksearch.data.ImageUtils;
import com.example.ashleighwilson.booksearch.models.BestBook;
import com.example.ashleighwilson.booksearch.models.Book;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private static final String TAG = FavoritesActivity.class.getSimpleName();

    private RecyclerView favListView;
    private FavAdapter favAdapter;
    public ArrayList<BestBook> allBooks = new ArrayList<>();
    public BookDbHelper dbHelper;
    public TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new BookDbHelper(getApplicationContext());
        //allBooks = dbHelper.getAllBooks();

        emptyView = findViewById(R.id.empty_view);

        favListView = findViewById(R.id.fav_list);
        favListView.setHasFixedSize(true);

        favAdapter = new FavAdapter(this, allBooks);

        favListView.setLayoutManager(new LinearLayoutManager(this));


        //favListView.setAdapter(favAdapter);
        showBooksFromDB();

    }

    public void showBooksFromDB()
    {
        allBooks.clear();

        Cursor cursor = dbHelper.getBooks();

        if (cursor.moveToFirst())
        {
            do {
                BestBook book = new BestBook(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                allBooks.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();

        if (!(allBooks.size()<1))
        {
            favListView.setVisibility(View.VISIBLE);
            favListView.setAdapter(favAdapter);
            favAdapter.notifyDataSetChanged();
        }
        else
        {
            favListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            favListView.setAdapter(favAdapter);
            favAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        showBooksFromDB();
    }

}

