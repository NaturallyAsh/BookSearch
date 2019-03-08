package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;

import org.simpleframework.xml.Element;

public class ReviewsListResponse extends GoodreadsResponse{

    @Element(name="reviews", required=false)
    public Reviews reviews;

    @Override
    public String toString() {
        return "ReviewsListResponse{" +
                "review=" + reviews +
                '}';
    }

    public Reviews getReviews() {
        return reviews;
    }
}
