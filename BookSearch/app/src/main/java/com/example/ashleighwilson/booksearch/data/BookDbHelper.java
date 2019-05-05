package com.example.ashleighwilson.booksearch.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ashleighwilson.booksearch.MyApp;
import com.example.ashleighwilson.booksearch.models.AudioBook;
import com.example.ashleighwilson.booksearch.models.BestBook;
import com.example.ashleighwilson.booksearch.models.Reader;

import java.util.ArrayList;

public class BookDbHelper extends SQLiteOpenHelper
{
    private static final String TAG = BookDbHelper.class.getSimpleName();
    private Context context;
    byte[] blob;
    private static BookDbHelper dbHelper = null;

    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 30;
    public static final String CONTENT_AUTHORITY = "com.example.ashleighwilson.booksearch";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "booksearch";

    public BookDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized BookDbHelper getInstance() {
        return getDBHelper(MyApp.getInstance());
    }

    private static BookDbHelper getDBHelper(Context context)
    {
        if (dbHelper == null)
            dbHelper = new BookDbHelper(context);

        return dbHelper;
    }

    String[] allColumns = new String[] {
            BookDbHelper.BookEntry._ID,
            BookEntry.COLUMN_BOOK_ID,
            BookDbHelper.BookEntry.COLUMN_BOOK_TITLE,
            BookDbHelper.BookEntry.COLUMN_BOOK_AUTHOR,
            BookDbHelper.BookEntry.COLUMN_BOOK_IMAGE,
    };

    String[] readerColumns = new String[] {
          ReaderEntry._ID,
          ReaderEntry.COLUMN_TITLE,
          ReaderEntry.COLUMN_AUTHOR,
          ReaderEntry.COLUMN_COVER,
          ReaderEntry.COLUMN_PATH
    };

    String[] audiobookColumns = new String[] {
            AudiobookEntry._ID,
            AudiobookEntry.COLUMN_TITLE,
            AudiobookEntry.COLUMN_AUTHOR,
            AudiobookEntry.COLUMN_COVER,
            AudiobookEntry.COLUMN_PUBLISHED,
            AudiobookEntry.COLUMN_CURRENT_POSITION,
            AudiobookEntry.COLUMN_PATH
    };

    public static final class BookEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "books";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BOOK_ID = "book_id";
        public final static String COLUMN_BOOK_IMAGE = "image";
        public final static String COLUMN_BOOK_TITLE = "title";
        public final static String COLUMN_BOOK_AUTHOR = "author";
    }

    String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME +
            " ("
            + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BookEntry.COLUMN_BOOK_ID + " TEXT, "
            + BookEntry.COLUMN_BOOK_TITLE + " TEXT, "
            + BookEntry.COLUMN_BOOK_AUTHOR + " TEXT, "
            + BookEntry.COLUMN_BOOK_IMAGE + " TEXT);";

    public static final class ReaderEntry implements BaseColumns {
        public static final String TABLE_NAME = "reader";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_COVER = "cover";

    }

    String SQL_CREATE_READER_TABLE = "CREATE TABLE " + ReaderEntry.TABLE_NAME +
            " ("
            + ReaderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReaderEntry.COLUMN_TITLE + " TEXT, "
            + ReaderEntry.COLUMN_AUTHOR + " TEXT, "
            + ReaderEntry.COLUMN_COVER + " BLOB NOT NULL, "
            + ReaderEntry.COLUMN_PATH + " TEXT);";

    public static final class AudiobookEntry implements BaseColumns {
        public static final String TABLE_NAME = "audiobooks";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_COVER = "cover";
        public static final String COLUMN_PUBLISHED = "published";
        public static final String COLUMN_CURRENT_POSITION = "current_position";
        public static final String COLUMN_PATH = "path";
    }

    String SQL_CREATE_AUDIOBOOK_TABLE = "CREATE TABLE " + AudiobookEntry.TABLE_NAME +
            " ("
            + AudiobookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AudiobookEntry.COLUMN_TITLE + " TEXT, "
            + AudiobookEntry.COLUMN_AUTHOR + " TEXT, "
            + AudiobookEntry.COLUMN_COVER + " TEXT, "
            + AudiobookEntry.COLUMN_PUBLISHED + " TEXT, "
            + AudiobookEntry.COLUMN_CURRENT_POSITION + " INTEGER, "
            + AudiobookEntry.COLUMN_PATH + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
        db.execSQL(SQL_CREATE_READER_TABLE);
        db.execSQL(SQL_CREATE_AUDIOBOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReaderEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AudiobookEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addBook(BestBook book)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_ID, book.getId());
        values.put(BookEntry.COLUMN_BOOK_TITLE, book.getTitle());
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, book.getAuthor().getName());
        values.put(BookEntry.COLUMN_BOOK_IMAGE, book.getImageUrl());

        db.insert(BookDbHelper.BookEntry.TABLE_NAME, null, values);
        db.close();

    }

    public Cursor getBooks()
    {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(BookEntry.TABLE_NAME, allColumns, null, null,
                null, null, null);
    }

    public ArrayList<BestBook> getAllBooks()
    {
        ArrayList<BestBook> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BookDbHelper.BookEntry.TABLE_NAME, allColumns, null,
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                BestBook book = new BestBook(
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

    public void removeBook(BestBook book)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BookDbHelper.BookEntry.TABLE_NAME,
                BookDbHelper.BookEntry._ID + "=?",
                null);
        db.close();
    }

    public long addReader(Reader reader) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (reader.getmId() != 0) {
            values.put(ReaderEntry._ID, reader.getmId());
        }
        values.put(ReaderEntry.COLUMN_TITLE, reader.getmTitle());
        values.put(ReaderEntry.COLUMN_AUTHOR, reader.getmAuthor());
        values.put(ReaderEntry.COLUMN_COVER, ImageUtils.getImageBytes(reader.getmCoverImage()));
        values.put(ReaderEntry.COLUMN_PATH, reader.getPathLocation());

        long res = db.insert(ReaderEntry.TABLE_NAME, null, values);
        db.close();

        return res;
    }

    public Reader getReaderAt(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ReaderEntry.TABLE_NAME, readerColumns, null, null,
                null, null, null);

        if (cursor.moveToPosition(position)) {
            Reader reader = new Reader();
            reader.setmId(cursor.getInt(0));
            reader.setmTitle(cursor.getString(1));
            reader.setmAuthor(cursor.getString(2));
            reader.setmCoverImage(ImageUtils.getImage(cursor.getBlob(3)));
            reader.setPathLocation(cursor.getString(4));
            cursor.close();
            return reader;
        }
        return null;
    }

    public long removeReader(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(ReaderEntry.TABLE_NAME, ReaderEntry._ID + " =?",
                new String[]{String.valueOf(id)});
    }

    public int getReaderCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ReaderEntry._ID};

        Cursor cursor = db.query(ReaderEntry.TABLE_NAME, projection, null, null,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean hasPath(String path) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + ReaderEntry.TABLE_NAME + " WHERE " +
                ReaderEntry.COLUMN_PATH + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{path});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public long addAudiobook(AudioBook model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (model.getmId() != 0) {
            values.put(AudiobookEntry._ID, model.getmId());
        }
        values.put(AudiobookEntry.COLUMN_TITLE, model.getmName());
        values.put(AudiobookEntry.COLUMN_AUTHOR, model.getmAuthor());
        values.put(AudiobookEntry.COLUMN_COVER, model.getmImage());
        values.put(AudiobookEntry.COLUMN_PUBLISHED, model.getmPublished());
        values.put(AudiobookEntry.COLUMN_CURRENT_POSITION, model.getmCurrentPosition());
        values.put(AudiobookEntry.COLUMN_PATH, model.getmFilePath());

        long res = db.insertWithOnConflict(AudiobookEntry.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

        return res;
    }

    public void updateAudiobook(AudioBook model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AudiobookEntry.COLUMN_CURRENT_POSITION, model.getmCurrentPosition());
        db.update(AudiobookEntry.TABLE_NAME, values, AudiobookEntry._ID + " = ?",
                new String[]{String.valueOf(model.getmId())});
    }

    public Cursor getAudioBooks() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(AudiobookEntry.TABLE_NAME, audiobookColumns, null, null,
                null, null, null);
    }

}
