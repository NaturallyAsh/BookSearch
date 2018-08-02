package com.example.ashleighwilson.booksearch;

public class Book
{
    private String mTitle;
    private String mAuthors;
    private String mDescription;
    private String mInfoLink;
    private String mBookCover;
    private int mID;

    public Book (int id, String title, String authors, String description, String bookCover)
    {
        mID = id;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
    }

    public Book (String title, String authors, String description, String bookCover)
    {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
    }

    public Book(String bookCover, String title, String authors, String description, String
                infoLink)
    {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
        mInfoLink = infoLink;
    }

    public Book()
    {
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

    public String getmBookCover()
    {
        return mBookCover;
    }

    public void setmBookCover(String bookCover)
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
}
