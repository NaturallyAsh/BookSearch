package com.example.ashleighwilson.booksearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.ashleighwilson.booksearch.data.BookDbHelper;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private static final String TAG = FavoritesActivity.class.getSimpleName();

    private RecyclerView favListView;
    private FavAdapter favAdapter;
    public ArrayList<Book> allBooks = new ArrayList<>();
    public BookDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new BookDbHelper(getApplicationContext());
        allBooks = dbHelper.getAllBooks();

        favListView = findViewById(R.id.fav_list);
        favListView.setLayoutManager(new LinearLayoutManager(this));
        favListView.setHasFixedSize(true);

        //showBooksFromDB();
        favAdapter = new FavAdapter(this, allBooks);

        favListView.setAdapter(favAdapter);

    }
}

    /*public void showBooksFromDB()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + BookDbHelper.BookEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {
                Book book = new Book(
                        ImageUtils.getImage(cursor.getBlob(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                allBooks.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();

        favAdapter = new FavAdapter(this, allBooks);
        favListView.setAdapter(favAdapter);
    } */

