package com.example.ashleighwilson.booksearch.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ashleighwilson.booksearch.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDbHelper extends SQLiteOpenHelper
{
    private static final String TAG = BookDbHelper.class.getSimpleName();
    private Context context;
    private static BookDbHelper dbHelper = null;
    String[] allColumns = new String[] {
            BookDbHelper.BookEntry._ID,
            BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE,
            BookDbHelper.BookEntry.COLUMN_BOOK_TITLE,
            BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR,
            BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION};


    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CONTENT_AUTHORITY = "com.example.ashleighwilson.booksearch";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "booksearch";

    public static final class BookEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

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
    }


    public BookDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static BookDbHelper getDBHelper(Context context)
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
                + BookEntry.COLUMN_BOOK_IMAGE + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_TITLE + " TEXT, "
                + BookEntry.COLUMN_BOOK_AUTHOR + " TEXT, "
                + BookEntry.COLUMN_BOOK_DESCRIPTION + " TEXT);";

        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        onCreate(db);
    }

    /*public Cursor fetchAllBooks()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(BookDbHelper.BookEntry.TABLE_NAME, allColumns, null, null,
                null, null, null);

        cursor.close();

        return cursor;
    } */

    public void addBook(Book book)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_TITLE, book.getmTitle());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR, book.getmAuthors());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION, book.getmDescription());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE, book.getmBookCover());

        db.insert(BookDbHelper.BookEntry.TABLE_NAME, null, values);
        db.close();
    }

    public long addLongBook(Book book)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_TITLE, book.getmTitle());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR, book.getmAuthors());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_DESCRIPTION, book.getmDescription());
        values.put(BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE, book.getmBookCover());

        long newRowId = db.insert(BookDbHelper.BookEntry.TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public ArrayList<Book> getAltBooks()
    {
        ArrayList bookDetails = new ArrayList();
        String selectQuery = "SELECT * FROM " + BookEntry.TABLE_NAME + " ORDER BY " +
                BookEntry._ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {
                Book details = new Book();
                details.setID(cursor.getInt(0));
                details.setmTitle(cursor.getString(1));
                details.setmAuthors(cursor.getString(2));
                details.setmDescription(cursor.getString(3));
                details.setmBookCover(cursor.getString(4));
                bookDetails.add(details);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return bookDetails;
    }

    public Cursor getBooks()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(BookEntry.TABLE_NAME, allColumns, null, null,
                null, null, null);

        cursor.close();

        return cursor;
    }

    public Book getSingleBook(int movie_id)
    {
        Book bookDetails = new Book();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BookEntry.TABLE_NAME, allColumns, BookEntry._ID + " =?",
                new String[]{String.valueOf(movie_id)}, null,
                null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
            bookDetails.setID(cursor.getInt(0));
            bookDetails.setmTitle(cursor.getString(1));
            bookDetails.setmAuthors(cursor.getString(2));
            bookDetails.setmDescription(cursor.getString(3));
            bookDetails.setmBookCover(cursor.getString(4));
        }
        db.close();
        return bookDetails;
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
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
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
