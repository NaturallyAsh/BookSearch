package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.Reviews;
import com.example.ashleighwilson.booksearch.models.Shelf;

import org.simpleframework.xml.Element;

public class ReviewsAndShelfResponse extends GoodreadsResponse {

    @Element(name="reviews", required=false)
    public Reviews reviews;

    @Element(name="shelf", required=false)
    public Shelf shelf;

    public Reviews getReviews() {return this.reviews;}

    public Shelf getShelf() {return this.shelf;}

}
