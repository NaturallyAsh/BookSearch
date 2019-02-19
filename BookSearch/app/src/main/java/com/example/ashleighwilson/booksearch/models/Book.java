package com.example.ashleighwilson.booksearch.models;

import android.graphics.Bitmap;

public class Book
{
    private String mTitle;
    private String mAuthors;
    private String mDescription;
    private String mInfoLink;
    private Bitmap mBookCover;
    //private String mBookCover;
    private byte[] mImageByteArray;
    private int mID;

    public Book (int id, Bitmap bookCover, String title, String authors, String description, String infoLink)
    {
        mID = id;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
        mInfoLink = infoLink;
    }

    public Book (Bitmap bookCover, String title, String authors, String description)
    {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
    }

    public Book(Bitmap bookCover, String title, String authors, String description, String
                infoLink)
    {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
        mInfoLink = infoLink;
    }

    public Book(Bitmap bookCover)
    {
        mBookCover = bookCover;
    }

    public Book() {

    }

    public String getmTitle()
    {
        return mTitle;
    }

    public void setmTitle(String title)
    {
        this.mTitle = title;
    }

    public String getmAuthors()
    {
        return mAuthors;
    }

    public void setmAuthors(String authors)
    {
        this.mAuthors = authors;
    }

    public String getmDescription()
    {
        return mDescription;
    }

    public void setmDescription (String description)
    {
        this.mDescription = description;
    }

    public Bitmap getmBookCover()
    {
        return mBookCover;
    }

    public void setmBookCover(Bitmap bookCover)
    {
        this.mBookCover = bookCover;
    }

    public String getmInfoLink()
    {
        return mInfoLink;
    }

    public int getID()
    {
        return mID;
    }

    public void setID(int ID)
    {
        this.mID = ID;
    }

    public byte[] getmImageByteArray() {
        return mImageByteArray;
    }

    public void setmImageByteArray(byte[] mImageByteArray) {
        this.mImageByteArray = mImageByteArray;
    }
}
