package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

public class TestReviews {

    @Element(name = "reviews")
    public Reviews reviews;


    public Reviews getReviews() {
        return reviews;
    }

}
