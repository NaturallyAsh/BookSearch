package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.UserBook;

import org.simpleframework.xml.Element;

public class BookShowResponse extends GoodreadsResponse{

    @Element(name = "book", required = false)
    UserBook book;

    public UserBook getBook() {
        return book;
    }

}
