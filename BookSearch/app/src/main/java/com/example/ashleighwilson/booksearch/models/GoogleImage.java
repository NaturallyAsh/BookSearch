package com.example.ashleighwilson.booksearch.models;

import android.graphics.Bitmap;

public class GoogleImage {

    private Bitmap bookCover;

    public GoogleImage(Bitmap bookCover) {
        this.bookCover = bookCover;
    }

    public Bitmap getBookCover() {
        return bookCover;
    }

    public void setBookCover(Bitmap bookCover) {
        this.bookCover = bookCover;
    }
}
