package com.example.ashleighwilson.booksearch.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ashleighwilson.booksearch.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDbHelper extends SQLiteOpenHelper
{
    private static final String TAG = BookDbHelper.class.getSimpleName();
    private Context context;
    byte[] blob;
    private static BookDbHelper dbHelper = null;
    String[] allColumns = new String[] {
            BookDbHelper.BookEntry._ID,
            BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE,
            BookDbHelper.BookEntry.COLUMN_BOOK_TITLE,
            BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR,
            BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION,
            BookEntry.COLUMN_BOOK_INFOLINK};


    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 4;
    public static final String CONTENT_AUTHORITY = "com.example.ashleighwilson.booksearch";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "booksearch";

    public static final class BookEntry implements BaseColumns
    {

        public final static String TABLE_NAME = "books";

        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        //Type: BLOB
        public final static String COLUMN_BOOK_IMAGE = "image";

        //Type: TEXT
        public final static String COLUMN_BOOK_TITLE = "title";

        //Type: TEXT
        public final static String COLUMN_BOOK_AUTHOR = "author";

        //Type: TEXT
        public final static String COLUMN_BOOK_DESCRIPTION = "description";

        //Type: TEXT
        public final static String COLUMN_BOOK_INFOLINK = "info";
    }


    public BookDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static BookDbHelper getDBHelper(Context context)
    {
        if (dbHelper == null)
            dbHelper = new BookDbHelper(context.getApplicationContext());

        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME +
                " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_IMAGE + " BLOB NOT NULL, "
                + BookEntry.COLUMN_BOOK_TITLE + " TEXT, "
                + BookEntry.COLUMN_BOOK_AUTHOR + " TEXT, "
                + BookEntry.COLUMN_BOOK_DESCRIPTION + " TEXT, "
                + BookEntry.COLUMN_BOOK_INFOLINK + " TEXT);";

        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addBook(Book book)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE,
                ImageUtils.getImageBytes(book.getmBookCover()));
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_TITLE, book.getmTitle());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR, book.getmAuthors());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION, book.getmDescription());
        values.put(BookEntry.COLUMN_BOOK_INFOLINK, book.getmInfoLink());

        db.insert(BookDbHelper.BookEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addByteBook(Book book)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE, ImageUtils.getImageBytes(book.getmBookCover()));
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_TITLE, book.getmTitle());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR, book.getmAuthors());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION, book.getmDescription());
        values.put(BookEntry.COLUMN_BOOK_INFOLINK, book.getmInfoLink());

        db.insert(BookDbHelper.BookEntry.TABLE_NAME, null, values);
        db.close();

    }

    public Cursor getBooks()
    {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(BookEntry.TABLE_NAME, allColumns, null, null,
                null, null, null);
    }

    public ArrayList<Book> getAllBooks()
    {
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BookDbHelper.BookEntry.TABLE_NAME, allColumns, null,
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Book book = new Book(
                        ImageUtils.getImage(cursor.getBlob(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                bookList.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    public void removeBook(Book book)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BookDbHelper.BookEntry.TABLE_NAME,
                BookDbHelper.BookEntry._ID + "=?",
                null);
        db.close();
    }

}
