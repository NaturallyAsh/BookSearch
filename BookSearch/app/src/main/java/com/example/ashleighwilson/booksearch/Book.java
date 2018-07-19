package com.example.ashleighwilson.booksearch;

public class Book
{
    private String mTitle;
    private String mAuthors;
    private String mDescription;
    private String mInfoLink;
    private String mBookCover;

    public Book(String bookCover, String title, String authors, String description, String
                infoLink)
    {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mBookCover = bookCover;
        mInfoLink = infoLink;
    }

    public String getmTitle()
    {
        return mTitle;
    }

    public String getmAuthors()
    {
        return mAuthors;
    }

    public String getmDescription()
    {
        return mDescription;
    }

    public String getmBookCover()
    {
        return mBookCover;
    }

    public String getmInfoLink()
    {
        return mInfoLink;
    }
}
