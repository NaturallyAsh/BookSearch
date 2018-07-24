package com.example.ashleighwilson.booksearch.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract
{
    private BookContract() {}

    /* The Content authority is a name for the entire content provider, similar
     * to the relationship btw a domain name and its website. A convenient
      * string to use for the content authority is the package name for the app,
      * which is guaranteed to be unique on the device
      * Use the CONTENT_AUTHORITY to create the base of all URIs which apps will
      * use to contact the contact provider*/

    public static final String CONTENT_AUTHORITY = "com.example.ashleighwilson.booksearch";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "booksearch";

    public static final class BookEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                 CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                 CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        public final static String TABLE_NAME = "books";

        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        //Type: INTEGER
        public final static String COLUMN_BOOK_FAVS = "favorite";

        //possible values for if book is favorited
        public static final int FAVORITE_NO = 0;
        public static final int FAVORITE_YES = 1;


    }
}
